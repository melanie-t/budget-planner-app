
/*
 * Transaction Repository
 * Contains access to all of the transactions on the system
 * */
public class TransactionRepository {
	SQLStringFactory sql;
	Database myDatabase;
	
	public TransactionRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		this.sql = SQLStringFactory.getInstance();
	}
	
	public void loadAllItems() {
		//@TODO
	}
	
	
	public void saveItem(TransactionModel transaction) { // @TODO
		/*
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
		 */
	}
	
	public void reinitSQLStructure() {
		destroySQLStructure();	
		initSQLStructure();
	}
	
	public void initSQLStructure() {
		myDatabase.updateSQL(sql.createTable("transactions", "transactionId", "INTEGER", "accountId", "INTEGER", "account", "accountId"));	//handles foreign key
		myDatabase.updateSQL(sql.addColumn("transactions", "date", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("transactions", "amount", "INTEGER"));
		myDatabase.updateSQL(sql.addColumn("transactions", "description", "VARCHAR"));
	}
	
	public void destroySQLStructure() {
		myDatabase.updateSQL(sql.deleteTable("transactions"));	
	}
}

