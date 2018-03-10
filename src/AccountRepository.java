
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

//The User object will likely have one of these
public class AccountRepository { 
	
	//Database manipulation
	Database myDatabase;
	SQLStringFactory sql;
	
	
	Boolean boolAllLoaded;
	HashMap<Integer, AccountModel> itemMap; // loaded account models live here
	
	public AccountRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		this.sql = SQLStringFactory.getInstance();
		boolAllLoaded = false;
		
		itemMap = new HashMap<Integer, AccountModel>();
	}
		
	public void saveItem(AccountModel account) {
		if(account.isNew()) {
			//Insert into database
			Integer newId = myDatabase.updateSQL(sql.addEntry("account", "NULL", account.getBankName(), account.getNickName(), Integer.toString(account.getBalance()) ));
			account.setId(newId);
			addItemToMap(account);
			//NOTE: currently no way of getting id of newly inserted row. Should update the model with this.	
		} else {
			//Update ALL account values
			SQLValueMap values = new SQLValueMap();
			values.put("bankName", account.getBankName());
			values.put("nickname", account.getNickName());
			values.put("bankName", account.getBalance());
			
			SQLValueMap where = new SQLValueMap();
			where.put("accountId", Integer.toString(account.getId()));
			
			myDatabase.updateSQL( sql.updateEntryUsingMap("account", values, where) );
		}
	}
	
	public boolean hasItem(Integer itemID) {
		return itemMap.get(itemID) != null;
	}
	
	public AccountModel getItem(Integer itemID) {
		//Attempt to load from DB if not present
		if(hasItem(itemID))
			loadItem(itemID);
		
		//Return if found
		if(hasItem(itemID))
			return itemMap.get(itemID);
		return null;
	}
	
	public HashMap GetAllItems() {
		if(!boolAllLoaded)
			loadAll();
		return itemMap;
	}
	
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
	
	protected void loadAll() {
		System.out.println("loadAll");
		ResultSet result = myDatabase.fetchSQL("SELECT * FROM account");
		try {
			while(result.next())
				setItemFromResult(result);
			
			boolAllLoaded = true;
		} catch (SQLException e){ 
			System.err.println(e.getMessage());
		}
	}
	
	protected void setItemFromResult(ResultSet result) {
		System.out.println("setItemFromResult");
		try {
			AccountModel account =  new AccountModel();
			account.setId(result.getInt("accountId"));
			account.setBalance(result.getInt("balance"));
			account.setNickName(result.getString("nickname"));
			account.setBankName(result.getString("bankName"));
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
