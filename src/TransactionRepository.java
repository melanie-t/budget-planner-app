import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Repository class for Transaction objects
 */
public class TransactionRepository extends AbstractRepository<Transaction>{

	public String depositType = "Deposit";
	public String withdrawallType = "Withdrawal";
	
    /**
     * Constructor.
     * @param database database associated with this repository
     */
	public TransactionRepository(Database database) {
	    super(database, "transactions", "transactionId"); // transactionId should be in a constant
	}

    public void initSQLStructure() {
        database.updateSQL(sql.createTable(tableName, "transactionId", "INTEGER", "accountId", "INTEGER", "account", "accountId"));	//handles foreign key
        database.updateSQL(sql.addColumn(tableName, "budgetId", "INTEGER"));
        database.updateSQL(sql.addColumn(tableName, "date", "VARCHAR"));
        database.updateSQL(sql.addColumn(tableName, "type", "VARCHAR"));
        database.updateSQL(sql.addColumn(tableName, "amount", "INTEGER"));
        database.updateSQL(sql.addColumn(tableName, "description", "VARCHAR"));
    }

    @Override
    public Transaction getItem(Integer id) {
        return new Transaction(itemMap.get(id));
    }

    protected void setItemFromResult(ResultSet result) {
        try {
            Transaction transaction = new Transaction();
            transaction.setId(result.getInt("transactionId"));
            transaction.setAssociatedAccountId(result.getInt("accountId"));
            transaction.setAssociatedBudgetId(result.getInt("budgetId"));
            transaction.setAmount(result.getInt("amount"));
            transaction.setDate(result.getString("date"));
            transaction.setType(result.getString("type"));
            transaction.setDescription(result.getString("description"));

            System.out.println(result.getInt(primaryKey));
            addItemToMap(transaction);
        } catch(SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    
    
    /**
     * Account Balance
     */
    public Integer fetchSumOfTransactionTypeForAccount(Integer intAccount, String strType) {
        ResultSet result = database.fetchSQL("SELECT SUM(amount) intAmount FROM "+tableName+" WHERE type='"+sql.EscapeSQLValue(strType)+"' AND accountId = '"+intAccount+"' GROUP BY accountId");
        try {
            while(result.next())
            	return result.getInt("intAmount");

        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return 0;
    }
    
    public Integer fetchBlanaceForAccount(Integer intAccount) {
    	Integer in = fetchSumOfTransactionTypeForAccount(intAccount, depositType);
    	Integer out = fetchSumOfTransactionTypeForAccount(intAccount, withdrawallType);
    	
    	System.out.println("In:" +in);
    	System.out.println("Out: " +out);
    	return in - out;
    }
    
    
    /**
     * User Budget
     */
    public Integer fetchSumOfTransactionTypeForBudget(Integer intBudget, String strType) {
        ResultSet result = database.fetchSQL("SELECT SUM(amount) intAmount FROM "+tableName+" WHERE type='"+sql.EscapeSQLValue(strType)+"' AND budgetId = '"+intBudget+"' GROUP BY budgetId");
        try {
            while(result.next())
            	return result.getInt("intAmount");

        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return 0;
    }
    
    public Integer fetchBlanaceForBudget(Integer intBudget) {
    	Integer out = fetchSumOfTransactionTypeForBudget(intBudget, withdrawallType);
    	System.out.println("Out: " +out);
    	return out;
    }
    
    

	public void saveItem(Transaction transaction) {
		System.out.println(transaction.toString());
		SQLValueMap values = new SQLValueMap();
        values.put("accountId", transaction.getAssociatedAccountId());
        values.put("budgetId", transaction.getAssociatedBudgetId());
		values.put("type", transaction.getType());
		values.put("date", transaction.getDate());
		values.put("amount", transaction.getAmount());
		values.put("description", transaction.getDescription());
			
		if(transaction.getId() == 0) {
            // Insert new item
            Integer generatedId = database.updateSQL( sql.addEntryUsingMap(tableName, values) );
            transaction.setId(generatedId);
            addItemToMap(transaction);
        } else {
			// Update item
			SQLValueMap where = new SQLValueMap();
			where.put(primaryKey, Integer.toString(transaction.getId()));
            database.updateSQL( sql.updateEntryUsingMap(tableName, values, where) );
            
            Transaction transInMap = itemMap.get(transaction.getId());
            if(transInMap != null)
            	transInMap.updateWith(transaction);
            else 
            	 addItemToMap(transaction);
		}
	}
	
	public void deleteItem(Integer itemID) {
		if(itemMap.containsKey(itemID))
        {
            itemMap.remove(itemID);
            database.updateSQL("DELETE FROM "+tableName+" WHERE "+primaryKey+"='"+itemID+"';");
            System.out.println("Delete Transaction "+itemID);
        }
	}

    /**
     * Deletes all transactions associated with the specified account id
     * @param accountId accound id
     */
    public void deleteAllItemsFromAccount(Integer accountId) {
    	ArrayList<Transaction> transactionsToDelete = new ArrayList<Transaction>();
        for (Transaction transaction : itemMap.values())
        {
            if (transaction.getAssociatedAccountId().equals(accountId))
                transactionsToDelete.add(transaction);
        }
        for (Transaction transaction : transactionsToDelete) {
        	deleteItem(transaction.getId());
        }
    }

    /**
     * Returns a list of all transactions associated with the specified account id
     * @param accountId account id
     * @return list of Transactions
     */
    public ArrayList<Transaction> getItemsFromAccount(Integer accountId) {
	    ArrayList<Transaction> itemsToFilter = getItems();
        Iterator<Transaction> it = itemsToFilter.iterator();
        while (it.hasNext()) {
            if (it.next().getAssociatedAccountId() != accountId)
                it.remove();
        }
        return itemsToFilter;
    }

    public ArrayList<Transaction> getItemsFromBudget(Integer budgetid)
    {
        ArrayList<Transaction> itemsToFilter = getItems();
        Iterator<Transaction> it = itemsToFilter.iterator();
        while (it.hasNext()) {
            if (it.next().getAssociatedBudgetId() != budgetid)
                it.remove();
        }
        return itemsToFilter;
    }

    public void clearBudgetAssociations(Integer budgetId, Integer defaultBudget)
    {
        for (Transaction transaction : getItems())
        {
            if (transaction.getAssociatedBudgetId() == budgetId)
            {
                transaction.setAssociatedBudgetId(defaultBudget);
                saveItem(transaction);
            }
        }
    }
}

