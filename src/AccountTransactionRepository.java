/*
 * AccountTransaction Repository
 * Contains access to all of the transactions for an account
 * */
public class AccountTransactionRepository extends TransactionRepository{
	
	AccountModel account;
	
	public AccountTransactionRepository(Database myDatabase){
		super(myDatabase);
		account = null;
	}
	
	public void 		setAccount(AccountModel account) 	{this.account = account;}
	public AccountModel getAccount() 						{return account;}
	public boolean 		hasAccount() 						{return account == null;}
	public boolean		hasAccountId()						{return hasAccount() && account.getId() != 0;}
	
	
	public void loadAllItems() {
		//@TODO
	}
	
	
	public void saveItem(TransactionModel transaction) {
		if(hasAccountId()) {
			transaction.setAccountId(account.getId());
			super.saveItem(transaction);
		}
	}
}
