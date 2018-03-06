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
		//@TODO restricted to account
	}
	
	
	public void saveItem(TransactionModel transaction) {
		if(hasAccount() && !getAccount().isNew()) {
			transaction.setAccountId(account.getId());
			super.saveItem(transaction);
		}
	}
}
