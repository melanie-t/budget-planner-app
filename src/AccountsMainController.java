import java.awt.event.ActionEvent;

import GUI.addAccountWindow;

public class AccountsMainController extends AbstractViewController{
	UserModel user;
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	public UserModel getUser() {
		return user;
	}
	
	
	//-----------------------------------
	// OpenAddAccountListener
	//-----------------------------------
	public OpenAddAccountListener openAddAccountListener() {
		return new OpenAddAccountListener();
	}
	public class OpenAddAccountListener extends AccountController{
		public void actionPerformed(ActionEvent arg0) {
			//still need a way of updating the model here
			try {
				System.out.println("addAccountWindow opened");
				addAccountWindow.init(); // <-- @TODO Replace this
			}
			catch(Exception e) {
				System.out.println("Error in opening add window");
			}
		}
	}
	
	
	//-----------------------------------
	// DeleteAccountListener
	//-----------------------------------
	public DeleteAccountListener deleteAccountListener(AccountRepository accountRepo) {
		return new DeleteAccountListener(accountRepo);
	}
	public class DeleteAccountListener extends UserBasedController{
		DeleteAccountListener(AccountRepository accountRepo){
			setAccountRepository(accountRepo);
		}
		
		AccountRepository accountRepo;
		
		public void setAccountRepository(AccountRepository accountRepo) {
			this.accountRepo = accountRepo;
		}
		
			
		public void actionPerformed(ActionEvent arg0) {
			//still need a way of updating the model here
			try {
				
				//getSelectedAccount
				AccountsMainView view = (AccountsMainView) getView();
				
				Integer accountId = view.getSelectedAccount();
				System.out.println("Delete account ID " + accountId + ".... @TODO find which account is actually selected in UI");
				
			}
			catch(Exception e) {
				System.out.println("Error when deleting account");
			}
		}
	}

	
	
	
	
	
}
