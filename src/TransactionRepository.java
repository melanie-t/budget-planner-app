import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;

/*
 * Transaction Repository
 * Contains access to all of the transactions on the system
 * */
public class TransactionRepository {
	SQLStringFactory sql;
	Database myDatabase;
	
	HashMap<Integer, TransactionModel> itemMap; // loaded account models live here
	
	
	public TransactionRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		this.sql = SQLStringFactory.getInstance();
	}
	
	public void loadAllItems() {
		//@TODO
	}
	
	public void loadItem(Integer intItem) {
		
	}
	
	
	public void saveItem(TransactionModel transaction) {
		String accId = String.valueOf(transaction.getAccountId());
		String type = transaction.getType();
		String date = transaction.getDate();
		String amount = String.valueOf(transaction.getAmount());
		
		
		myDatabase.updateSQL(sql.addEntry("transactions", "NULL", accId, date, amount, type));
	}
	
	public void reinitSQLStructure() {
		destroySQLStructure();	
		initSQLStructure();
	}
	
	public void initSQLStructure() {
		myDatabase.updateSQL(sql.createTable("transactions", "transactionId", "INTEGER", "accountId", "INTEGER", "account", "accountId"));	//handles foreign key
		myDatabase.updateSQL(sql.addColumn("transactions", "date", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("transactions", "amount", "FLOAT")); //Integer originally
		myDatabase.updateSQL(sql.addColumn("transactions", "description", "VARCHAR"));
	}
	
	public void destroySQLStructure() {
		myDatabase.updateSQL(sql.deleteTable("transactions"));	
	}
}

