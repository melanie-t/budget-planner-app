import java.util.HashMap;

// The User object will likely have one of these
// perhaps this is more of a repository instead of controller?
public class AccountController { 
	

	//Database manipulation
	Database myDatabase;
	SQLStringFactory sql;
	

	public AccountController(Database myDatabase) {
		this.myDatabase = myDatabase;
		this.sql = SQLStringFactory.getInstance();
	}
	
	
	//----------------------------------------------------
	// DEVELOPMENT ONLY - Sql Structure 
	public void reinitSQLStructure() {
		destroySQLStructure();	
		initSQLStructure();
	}
	
	public void initSQLStructure() {
		myDatabase.updateSQL(sql.createTable("account", "accountId", "INTEGER"));	//pass "NULL" to auto-increment
		myDatabase.updateSQL(sql.addColumn("account", "bankName", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("account", "nickname", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("account", "balance", "INTEGER"));
	}
	
	public void destroySQLStructure() {
		myDatabase.updateSQL(sql.deleteTable("account"));	
	}
	//_____________________________________________________
	
	

	//----------------------------------------------------
	// Save Account
	public boolean saveAccount(AccountModel objAccount) {
		
		if(objAccount.isNew()) {
			//Insert into database
			myDatabase.updateSQL(sql.addEntry("account", "NULL", objAccount.GetBankName(), objAccount.GetNickName(), Integer.toString(objAccount.GetBalance()) ));
			//NOTE: currently no way of getting id of newly inserted row. Should update the model with this.
			
		} else {
			//Update ALL account values
			SQLValueMap values = new SQLValueMap();
			values.put("bankName", objAccount.GetBankName());
			values.put("nickname", objAccount.GetNickName());
			values.put("bankName", objAccount.GetBalance());
			
			SQLValueMap where = new SQLValueMap();
			where.put("accountId", Integer.toString(objAccount.getId()));
			
			
			myDatabase.updateSQL( sql.updateEntryUsingMap("account", values, where) );
		}
		
		return false;
	}
	//_____________________________________________________
	
	
}
