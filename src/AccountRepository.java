

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Repository class for Account objects.
 */
public class AccountRepository extends AbstractRepository<Account> {

    /**
     * Constructor.
     * @param database database associated with this repository
     */
	public AccountRepository(Database database) {
	    super(database, "account", "accountId");
	}

    public void initSQLStructure() {
        database.updateSQL(sql.createTable(tableName, primaryKey, "INTEGER"));	//pass "NULL" to auto-increment
        database.updateSQL(sql.addColumn(tableName, "bankName", "VARCHAR"));
        database.updateSQL(sql.addColumn(tableName, "nickname", "VARCHAR"));
        database.updateSQL(sql.addColumn(tableName, "balance", "INTEGER"));
    }

    @Override
    public Account getItem(Integer id) {
        return new Account(itemMap.get(id));
    }

    protected void setItemFromResult(ResultSet result) {
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

	public void saveItem(Account account) {
		SQLValueMap values = new SQLValueMap();
		values.put("bankName", account.getBankName());
		values.put("nickName", account.getNickname());
		values.put("balance", account.getBalance());
			
		if(account.getId() == 0) {
            // Insert new item
            Integer generatedId = database.updateSQL( sql.addEntryUsingMap(tableName, values) );
            account.setId(generatedId);
            addItemToMap(account);
		} else {
			// Update item in database
			SQLValueMap where = new SQLValueMap();
			where.put("accountId", Integer.toString(account.getId()));
            database.updateSQL( sql.updateEntryUsingMap("account", values, where) );
            itemMap.get(account.getId()).updateWith(account);
		}
	}
}
