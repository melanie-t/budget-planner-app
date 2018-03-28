import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Repository class for Transaction objects
 */
public class TransactionRepository extends AbstractRepository<Transaction>{

    /**
     * Constructor.
     * @param database database associated with this repository
     */
	public TransactionRepository(Database database) {
	    super(database, "transactions", "transactionId");
	}

    public void initSQLStructure() {
        database.updateSQL(sql.createTable(tableName, "transactionId", "INTEGER", "accountId", "INTEGER", "account", "accountId"));	//handles foreign key
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

	public void saveItem(Transaction transaction) {
		System.out.println(transaction.toString());
		SQLValueMap values = new SQLValueMap();
		values.put("accountId", transaction.getAssociatedAccountId());
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
        for (Transaction transaction : itemMap.values())
        {
            if (transaction.getAssociatedAccountId().equals(accountId))
                deleteItem(transaction.getId());
        }
    }

    /**
     * Returns a list of all transactions associated with the specified account id
     * @param accountId account id
     * @return list of Transactions
     */
    public ArrayList<Transaction> getItems(Integer accountId) {
	    ArrayList<Transaction> itemsToFilter = getItems();
        Iterator<Transaction> it = itemsToFilter.iterator();
        while (it.hasNext()) {
            if (it.next().getAssociatedAccountId() != accountId)
                it.remove();
        }
        return itemsToFilter;
    }
}

