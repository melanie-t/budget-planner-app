import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/*
 * Transaction Repository
 * Contains access to all of the transactions on the system
 * */
public class TransactionRepository {
	SQLStringFactory sql;
	Database myDatabase;
	
	HashMap<Integer, TransactionModel> itemMap; // loaded account models live here
											   // If visibility becomes private make a getter method for itemMap size()
											   // w/o that breaks tests for TransactionRepository and AccountTransactionRepository
	
	
	public TransactionRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		this.sql = SQLStringFactory.getInstance();
	}
	
	public void loadAllItems() {
		//Load tuples from database.
		//Put all tuples in itemMap.
		itemMap = new HashMap();
		SQLValueMap where = new SQLValueMap();
		ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("transactions", where));
		
		try {
			
			while(result.next())
				setFromResult(result);
			
		} catch (SQLException sqle){ 
			System.err.println(sqle.getMessage());
		}
	}
	
	public void loadItem(Integer intItem) {
		SQLValueMap where = new SQLValueMap();
		where.put("transactionId", intItem);
		
		ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("transactions", where));

		try {
			while(result.next())
				setFromResult(result);
		}catch(SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
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
		myDatabase.updateSQL(sql.addColumn("transactions", "amount", "INTEGER"));
		myDatabase.updateSQL(sql.addColumn("transactions", "description", "VARCHAR"));
	}
	
	public void destroySQLStructure() {
		myDatabase.updateSQL(sql.deleteTable("transactions"));	
	}
	
	/*
	 * Helpers for loadAll() and loadItem()
	 */
	private void setFromResult(ResultSet result) {
		
		try {
			TransactionModel transacMod = new TransactionModel();
			transacMod.setAccountId(result.getInt("accountId"));
			transacMod.setAmount(result.getFloat("amount"));
			transacMod.setDate(result.getString("date"));
			transacMod.setType(result.getString("description"));
			
			if(itemMap != null)
				transacMod.SetId(itemMap.size());
			else
				itemMap = new HashMap();

			addToMap(transacMod);
			
		}catch(SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
	}
	
	private void addToMap(TransactionModel transac) {
		itemMap.put(transac.getId(), transac);
	}
}

