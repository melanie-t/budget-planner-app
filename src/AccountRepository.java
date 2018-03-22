import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {
	
	private Database myDatabase;
	private SQLStringFactory sql;
	
	private String tableName;
	private String primaryKey;
	
	private AccountMap itemMap; // loaded account models live here

	public AccountRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		tableName = "account";
		primaryKey = "accountId";
		this.sql = SQLStringFactory.getInstance();
        itemMap = new AccountMap();
	}

    public Account getAccount(Integer id)
    {
        return itemMap.get(id);
    }

    public void initSQLStructure() {
        myDatabase.updateSQL(sql.createTable(tableName, primaryKey, "INTEGER"));	//pass "NULL" to auto-increment
        myDatabase.updateSQL(sql.addColumn(tableName, "bankName", "VARCHAR"));
        myDatabase.updateSQL(sql.addColumn(tableName, "nickname", "VARCHAR"));
        myDatabase.updateSQL(sql.addColumn(tableName, "balance", "INTEGER"));
    }

    public void loadAllItems() {
        SQLValueMap where = new SQLValueMap(); // left blank so where is omitted
        ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap(tableName, where));

        try {
            System.out.println("Load all accounts");
            while(result.next())
                setItemFromResult(result);

        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    private void setItemFromResult(ResultSet result) {
        try {
            Account account =  new Account();
            account.setId(result.getInt("accountId"));
            account.setBalance(result.getInt("balance"));
            account.setNickname(result.getString("nickname"));
            account.setBankName(result.getString("bankName"));

            System.out.println(result.getInt(primaryKey));
            addItemToMap(account);
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    private void addItemToMap(Account account) {itemMap.put(account.getId(), account);}

	public void saveItem(Account account) {
		SQLValueMap values = new SQLValueMap();
		values.put("bankName", account.getBankName());
		values.put("nickName", account.getNickname());
		values.put("balance", account.getBalance());
			
		if(account.getId() == 0) {
            // Insert new item
            Integer generatedId = myDatabase.updateSQL( sql.addEntryUsingMap(tableName, values) );
            account.setId(generatedId);
            addItemToMap(account);
		} else {
			// Update item in database
			SQLValueMap where = new SQLValueMap();
			where.put("accountId", Integer.toString(account.getId()));
			myDatabase.updateSQL( sql.updateEntryUsingMap("account", values, where) );
            itemMap.get(account.getId()).updateWith(account);
		}
	}
	
	public void deleteItem(Integer itemID) {
		if(itemMap.containsKey(itemID))
        {
            itemMap.remove(itemID);
            myDatabase.updateSQL(sql.deleteEntry(tableName, primaryKey, itemID));
            System.out.println("Delete Account "+itemID);
        }
    }

    public AccountList getItems() {
        AccountList anAccountList = new AccountList();
        itemMap.forEach((k,v)->anAccountList.add(v));
        return anAccountList;
    }

	public void reinitSQLStructure() { // reinstall
		destroySQLStructure();	
		initSQLStructure();
	}

	private void destroySQLStructure() { // uninstall
		myDatabase.updateSQL(sql.deleteTable("account"));	
	}
}
