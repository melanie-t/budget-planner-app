import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/*
 * Transaction Repository
 * Contains access to all of the transactions on the system
 * */
public class TransactionRepository {
	//Database manipulation
	Database myDatabase;
	SQLStringFactory sql;
	
	String tableName;
	String primaryKey;
	
	TransactionMap itemMap; // loaded account models live here
											   // If visibility becomes private make a getter method for itemMap size()
											   // w/o that breaks tests for TransactionRepository and AccountTransactionRepository
	
	
	public TransactionRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		tableName = "transactions";
		primaryKey = "transactionId";
		this.sql = SQLStringFactory.getInstance();
		
	}
	
	
	
	
	
	
	public void loadAllItems() {
		//Load tuples from database.
		//Put all tuples in itemMap.
		itemMap.clear();
		SQLValueMap where = new SQLValueMap();
		ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("transactions", where));
		
		try {
			System.out.println("Load all transactions");
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
	
	
	
	//========================================

	//				SAVING
	
	//========================================
	public void saveItem(TransactionModel transaction) {
		System.out.println(transaction.toString());
		SQLValueMap values = new SQLValueMap();
		values.put("accountId", transaction.getAccountId());
		values.put("type", transaction.getType());
		values.put("date", transaction.getDate());
		values.put("amount", transaction.getAmount());
		values.put("description", transaction.getDescription());
			
		if(transaction.isNew()) {
			//Insert into database
			Integer newId = myDatabase.updateSQL(sql.addEntryUsingMap(tableName, values));
			transaction.setId(newId);
			addItemToMap(transaction);
		} else {
			//Update item in database
			SQLValueMap where = new SQLValueMap();
			where.put(primaryKey, Integer.toString(transaction.getId()));
			myDatabase.updateSQL( sql.updateEntryUsingMap(tableName, values, where) );
		}
	}
	
	public void deleteItem(Integer itemID) {
		if(itemMap.containsKey(itemID)) 
			itemMap.remove(itemMap.get(itemID));
		myDatabase.updateSQL("DELETE FROM "+tableName+" WHERE "+primaryKey+"='"+itemID+"';");
		System.out.println("Delete Transaction "+itemID);
	}
	

	
	
	
	public void reinitSQLStructure() {
		destroySQLStructure();	
		initSQLStructure();
	}
	
	public void initSQLStructure() {
		myDatabase.updateSQL(sql.createTable("transactions", "transactionId", "INTEGER", "accountId", "INTEGER", "account", "accountId"));	//handles foreign key
		myDatabase.updateSQL(sql.addColumn("transactions", "date", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("transactions", "type", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("transactions", "amount", "INTEGER"));
		myDatabase.updateSQL(sql.addColumn("transactions", "description", "VARCHAR"));
	}
	
	public void destroySQLStructure() {
		myDatabase.updateSQL(sql.deleteTable("transactions"));	
	}
	
	/*
	 * Helpers for loadAll() and loadItem()
	 */
	protected void setFromResult(ResultSet result) {
		try {
			TransactionModel transacMod = new TransactionModel();
			transacMod.setId(result.getInt("transactionId"));
			transacMod.setAccountId(result.getInt("accountId"));
			transacMod.setAmount(result.getInt("amount"));
			transacMod.setDate(result.getString("date"));
			transacMod.setType(result.getString("type"));
			transacMod.setDescription(result.getString("description"));
			
			System.out.println(result.getInt("amount"));
			addItemToMap(transacMod);
		}catch(SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
	}
	
	private void addItemToMap(TransactionModel transac) {
		itemMap.put(transac.getId(), transac);
	}
	
	
	
	
	//========================================
	
	//				GETTING
	
	//========================================
	
	protected boolean hasItemCached(Integer itemID) { // check the map not the database
		Boolean hasItemCachedCached = itemMap.get(itemID) != null;
		return hasItemCachedCached;
	}
	
	public TransactionModel getItem(Integer itemID) {
		//Attempt to load from DB if not present
		if(!hasItemCached(itemID))
			loadItem(itemID);
		
		//Return if found
		if(hasItemCached(itemID))
			return itemMap.get(itemID);
		return null;
	}
	
	//will return map off all items in database
	public TransactionMap getMapOfAllItems() {
		loadAllItems();
		return (TransactionMap)itemMap;
	}
	
	public TransactionList getListOfAllItems() {
		
		//Load Transaction if not listed
		loadAllItems();
		
		//Initialze Transaction List
		TransactionList aTransactionList = new TransactionList();
		
		//Loop over hash map
		Iterator it = getMapOfAllItems().entrySet().iterator();
	    while (it.hasNext()) {
	    	//Get map pairs
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        
	        //Add TransactionModel to list
	        aTransactionList.add((TransactionModel) pair.getValue());
	    }
	    
		return aTransactionList;
	}
	
	
}

