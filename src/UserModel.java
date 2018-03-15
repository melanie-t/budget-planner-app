import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {

	AccountRepository accountsRepo;
	
	
	public UserModel(){
		accountsRepo = null;
	}
	
	public String getName() {
		return "Jane Doe";
	}
	
	
	//=============================================
	
	//				USER HAS ACCOUNTS
	
	//=============================================
	public void setAccountRepository(AccountRepository accountsRepo) {this.accountsRepo = accountsRepo;}
	public AccountRepository getAccountRepository() {return this.accountsRepo;}
	public AccountMap getMapOfAllAccounts() {
		if(accountsRepo == null)
			System.out.println("accountsRepo == null");
		return accountsRepo.getMapOfAllItems();
	}
	public AccountList getListOfAllAccounts() {
		if(accountsRepo == null)
			System.out.println("accountsRepo == null");
		return accountsRepo.getListOfAllItems();
	}
	
	
}
