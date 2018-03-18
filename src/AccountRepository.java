
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//The User object will likely have one of these
public class AccountRepository { 
	
	//Database manipulation
	Database myDatabase;
	SQLStringFactory sql;
	
	String tableName;
	String primaryKey;
	
	AccountMap itemMap; // loaded account models live here
	
	
	public AccountRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		tableName = "account";
		primaryKey = "accountId";
		this.sql = SQLStringFactory.getInstance();
		
		itemMap = new AccountMap();
	}
		
	

	
	
	//========================================

	//				SAVING
	
	//========================================
	public void saveItem(AccountModel account) {
	
		SQLValueMap values = new SQLValueMap();
		values.put("bankName", account.getBankName());
		values.put("nickName", account.getNickname());
		values.put("balance", account.getBalance());
			
		if(account.isNew()) {
			//Insert into database
			Integer newId = myDatabase.updateSQL(sql.addEntryUsingMap("account", values));
			account.setId(newId);
			addItemToMap(account);
		} else {
			//Update item in database
			SQLValueMap where = new SQLValueMap();
			where.put("accountId", Integer.toString(account.getId()));
			myDatabase.updateSQL( sql.updateEntryUsingMap("account", values, where) );
		}
	}
	
	public void deleteItem(Integer itemID) {
		if(itemMap.containsKey(itemID)) 
			itemMap.remove(itemMap.get(itemID));
		myDatabase.updateSQL("DELETE FROM "+tableName+" WHERE "+primaryKey+"='"+itemID+"';");
	}
	
	//========================================
	
	//				GETTING
	
	//========================================
	public AccountModel getItem(Integer itemID) {
		//Attempt to load from DB if not present
		if(!hasItemCached(itemID))
			loadItem(itemID);
		
		//Return if found
		if(hasItemCached(itemID))
			return itemMap.get(itemID);
		return null;
	}
	
	//will return map off all items in database
	public AccountMap getMapOfAllItems() {
		loadAll();
		return (AccountMap)itemMap;
	}
	
	public AccountList getListOfAllItems() {
		
		//Load Accounts if not listed
		loadAll();
		
		//Initialze Account
		AccountList anAccountList = new AccountList();
		
		//Loop over hash map
		Iterator it = getMapOfAllItems().entrySet().iterator();
	    while (it.hasNext()) {
	    	//Get map pairs
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        
	        //Add AccountModel to list
	        anAccountList.add((AccountModel) pair.getValue());
	    }
	    
		return anAccountList;
	}
	
	
	
	//========================================
	
	//				LOADING
	
	//========================================
	protected void loadItem(Integer itemID) {
		SQLValueMap where = new SQLValueMap();
		where.put("accountId", itemID);
		
		ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("account", where));
		try {
			while(result.next())
				setItemFromResult(result);
			
			
		} catch (SQLException e){ 
			System.err.println(e.getMessage());
		}
	}
	
	//Loads all accounts in database
	protected void loadAll() {
		
		//get rid of old items - at least until caching can be properly done
		itemMap.clear();
		
		SQLValueMap where = new SQLValueMap(); // left blank so where is omitted
		
		ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("account", where));
		try {
			while(result.next())
				setItemFromResult(result);
			
		} catch (SQLException e){ 
			System.err.println(e.getMessage());
		}
	}
	
	protected void setItemFromResult(ResultSet result) {
		try {
			AccountModel account =  new AccountModel();
			account.setId(result.getInt("accountId"));
			account.setBalance(result.getInt("balance"));
			account.setNickname(result.getString("nickname"));
			account.setBankName(result.getString("bankName"));
			
			AccountTransactionRepository accTransRepo = new AccountTransactionRepository(myDatabase, account);
			account.setAccountTransactionRepository(accTransRepo);
			
			addItemToMap(account);
		} catch (SQLException e){ 
			System.err.println(e.getMessage());
		}
	}
	
	protected void addItemToMap(AccountModel account) {
		if(!account.isNew()) {
			itemMap.put(account.getId(), account);
		}
	}
	
	protected boolean hasItemCached(Integer itemID) { // check the map not the database
		Boolean hasItemCachedCached = itemMap.get(itemID) != null;
		return hasItemCachedCached;
	}
	
	
	
	//#####################################################

	// DEVELOPMENT ONLY - Sql Structure 
	
	//#####################################################
	public void reinitSQLStructure() { // reinstall
		destroySQLStructure();	
		initSQLStructure();
	}

	public void initSQLStructure() { // install
		myDatabase.updateSQL(sql.createTable("account", "accountId", "INTEGER"));	//pass "NULL" to auto-increment
		myDatabase.updateSQL(sql.addColumn("account", "bankName", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("account", "nickname", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("account", "balance", "INTEGER"));
	}
	
	public void destroySQLStructure() { // uninstall
		myDatabase.updateSQL(sql.deleteTable("account"));	
	}
	//_____________________________________________________
		
}
