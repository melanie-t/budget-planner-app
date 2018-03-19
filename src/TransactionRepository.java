import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRepository {

	private Database myDatabase;
	private SQLStringFactory sql;
	
	private String tableName;
	private String primaryKey;
	
	private TransactionMap itemMap;

	public TransactionRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		tableName = "transactions";
		primaryKey = "transactionId";
		this.sql = SQLStringFactory.getInstance();
        itemMap = new TransactionMap();
	}

    public void initSQLStructure() {
        myDatabase.updateSQL(sql.createTable("transactions", "transactionId", "INTEGER", "accountId", "INTEGER", "account", "accountId"));	//handles foreign key
        myDatabase.updateSQL(sql.addColumn("transactions", "date", "VARCHAR"));
        myDatabase.updateSQL(sql.addColumn("transactions", "type", "VARCHAR"));
        myDatabase.updateSQL(sql.addColumn("transactions", "amount", "INTEGER"));
        myDatabase.updateSQL(sql.addColumn("transactions", "description", "VARCHAR"));
    }

	public void loadAllItems() {
		SQLValueMap where = new SQLValueMap(); // left blank so where is omitted
		ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("transactions", where));
		
		try {
			System.out.println("Load all transactions");
			while(result.next())
				setItemFromResult(result);

		} catch (SQLException sqle){ 
			System.err.println(sqle.getMessage());
		}
	}

    private void setItemFromResult(ResultSet result) {
        try {
            Transaction transaction = new Transaction();
            transaction.setId(result.getInt("transactionId"));
            transaction.setAccountId(result.getInt("accountId"));
            transaction.setAmount(result.getInt("amount"));
            transaction.setDate(result.getString("date"));
            transaction.setType(result.getString("type"));
            transaction.setDescription(result.getString("description"));

            System.out.println(result.getInt("amount"));
            addItemToMap(transaction);
        }catch(SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    private void addItemToMap(Transaction transac)	{itemMap.put(transac.getId(), transac);}
	
	public void saveItem(Transaction transaction) {
		System.out.println(transaction.toString());
		SQLValueMap values = new SQLValueMap();
		values.put("accountId", transaction.getAccountId());
		values.put("type", transaction.getType());
		values.put("date", transaction.getDate());
		values.put("amount", transaction.getAmount());
		values.put("description", transaction.getDescription());
			
		if(transaction.getId() == 0) {
            // Insert new item
            Integer generatedId = myDatabase.updateSQL( sql.addEntryUsingMap(tableName, values) );
            transaction.setId(generatedId);
            addItemToMap(transaction);
        } else {
			// Update item
			SQLValueMap where = new SQLValueMap();
			where.put(primaryKey, Integer.toString(transaction.getId()));
			myDatabase.updateSQL( sql.updateEntryUsingMap(tableName, values, where) );
            itemMap.get(transaction.getId()).updateWith(transaction);
		}
	}
	
	public void deleteItem(Integer itemID) {
		if(itemMap.containsKey(itemID))
        {
            itemMap.remove(itemID);
            myDatabase.updateSQL("DELETE FROM "+tableName+" WHERE "+primaryKey+"='"+itemID+"';");
            System.out.println("Delete Transaction "+itemID);
        }
	}

    public void deleteAllItemsFromAccount(Integer accountId) {
        for (Transaction transaction : itemMap.values())
        {
            if (transaction.getAccountId().equals(accountId))
                deleteItem(transaction.getId());
        }
    }

    public TransactionList getItems(Integer accountId) {
        TransactionList aTransactionList = new TransactionList();
        itemMap.forEach((k,v)->{
            if (accountId == -1 || v.getAccountId().equals(accountId)) aTransactionList.add(v);
        });
        return aTransactionList;
    }

    public Integer getLargestIndex()
    {
        Integer largest = 0;
        for (Integer key : itemMap.keySet())
        {
            if (key > largest)
                largest = key;
        }
        return largest;
    }

    public void reinitSQLStructure() {
        destroySQLStructure();
        initSQLStructure();
    }

    public void destroySQLStructure() {
        myDatabase.updateSQL(sql.deleteTable("transactions"));
    }
}

