import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * AccountTransaction Repository
 * Contains access to all of the transactions for an account
 * */
public class AccountTransactionRepository extends TransactionRepository{
	
	AccountModel account;
	
	public AccountTransactionRepository(Database myDatabase, AccountModel account){
		super(myDatabase);
		setAccount(account);
	}
	
	
	public AccountTransactionRepository(Database myDatabase){
		super(myDatabase);
		account = null;
	}
	
	public void 		setAccount(AccountModel account) 	{this.account = account;}
	public AccountModel getAccount() 						{return account;}
	public boolean 		hasAccount() 						{return account != null;}
	
	
	public void loadAllItems() {
		if(hasAccount() && !getAccount().isNew()) {
			//Load tuples from database.
			//Put all tuples in itemMap.
			itemMap.clear();
			SQLValueMap where = new SQLValueMap();
			where.put("accountId", getAccount().getId()); // IMPORTNAT LOAD ONLY FOR THIS ACCOUNT
			
			ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("transactions", where));
			
			try {
				while(result.next())
					setFromResult(result);
				
			} catch (SQLException sqle){ 
				System.err.println(sqle.getMessage());
			}
		}
	}
	public void loadItem(Integer intItem) {
		if(hasAccount() && !getAccount().isNew()) {
			SQLValueMap where = new SQLValueMap();
			where.put("transactionId", intItem);
			where.put("accountId", getAccount().getId()); // IMPORTNAT LOAD ONLY FOR THIS ACCOUNT
			
			ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("transactions", where));
	
			try {
				while(result.next())
					setFromResult(result);
			} catch(SQLException sqle) {
				System.err.println(sqle.getMessage());
			}
		}
	}
	
	
	public void saveItem(TransactionModel transaction) {
		if(hasAccount() && !getAccount().isNew()) {
			transaction.setAccountId(account.getId());
			super.saveItem(transaction);
		}
	}
}
