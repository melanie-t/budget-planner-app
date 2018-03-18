import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {

	
	public UserModel(){
		accountsRepo = null;
	}
	
	public String getName() {
		return "Jane Doe";
	}
	
	
	//=============================================
	
	//				USER HAS ACCOUNTS
	
	//=============================================
	AccountRepository accountsRepo;
	public void setAccountRepository(AccountRepository accountsRepo) {this.accountsRepo = accountsRepo;}
	public AccountRepository getAccountRepository() {return this.accountsRepo;}
	public AccountMap getMapOfAllAccounts() {
		if(accountsRepo == null) {
			System.out.println("accountsRepo == null");
			return null;
		} else {
			return accountsRepo.getMapOfAllItems();
		}
	}
	public AccountList getListOfAllAccounts() {
		if(accountsRepo == null) {
			System.out.println("accountsRepo == null");
			return null;
		} else {
			return accountsRepo.getListOfAllItems();
		}
	}
	public void saveAccount(AccountModel account) {
		if(accountsRepo == null) {
			System.out.println("accountsRepo == null");
		} else {
			accountsRepo.saveItem(account);
		}
	}
	public void deleteAccount(AccountModel account) {
		if(accountsRepo == null) {
			System.out.println("accountsRepo == null");
		} else {
			accountsRepo.deleteItem(account.getId());
		}
	}
	
	public AccountModel getAccountAtIndex(int i) {
		AccountModel currentAccount;
		
		if(accountsRepo == null) {
			System.out.println("accountsRepo == null");
			return null;
		} else {
			currentAccount = getListOfAllAccounts().get(i);
		}
		return currentAccount;
	}
	
	
}
