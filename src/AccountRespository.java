import java.util.HashMap;

//The User object will likely have one of these
public class AccountRespository { 
	
	//Database manipulation
	Database myDatabase;
	SQLStringFactory sql;
	
	HashMap<Integer, AccountModel> itemMap; // loaded account models live here
	

	public AccountRespository(Database myDatabase) {
		this.myDatabase = myDatabase;
		this.sql = SQLStringFactory.getInstance();
		
		itemMap = new HashMap<Integer, AccountModel>();
	}
	
		
	public void saveItem(AccountModel objAccount) {
		if(objAccount.isNew()) {
			//Insert into database
			myDatabase.updateSQL(sql.addEntry("account", "NULL", objAccount.getBankName(), objAccount.getNickName(), Integer.toString(objAccount.getBalance()) ));
			//NOTE: currently no way of getting id of newly inserted row. Should update the model with this.
			
		} else {
			//Update ALL account values
			SQLValueMap values = new SQLValueMap();
			values.put("bankName", objAccount.getBankName());
			values.put("nickname", objAccount.getNickName());
			values.put("bankName", objAccount.getBalance());
			
			SQLValueMap where = new SQLValueMap();
			where.put("accountId", Integer.toString(objAccount.getId()));
			
			
			myDatabase.updateSQL( sql.updateEntryUsingMap("account", values, where) );
		}
	}
	
	protected void loadItem(Integer itemID) {
		//@TODO make selectEntryUsingMap and fetchSQL
		
		
	}
	
	/*
	 * // check to see if more that just the primary key is loaded form DB
	protected boolean isItemLoaded(Integer itemID) { 
		if(hasItem(itemID)) {		
			AccountModel objItem = itemMap.get(itemID);
			return true; // currently not checking any type of boolLoaded value
		}
		return false;
	}
	*/
	
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
