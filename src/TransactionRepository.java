
public class TransactionRepository {
	SQLStringFactory sql;
	Database myDatabase;
	

	
	
	public void saveItem() { // @TODO
		
	}
	
	
	

	public TransactionRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		this.sql = SQLStringFactory.getInstance();
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

