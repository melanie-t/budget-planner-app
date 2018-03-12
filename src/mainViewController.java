import java.awt.event.ActionEvent;

import GUI.AbstractEventListener;
import GUI.AbstractViewController;
import GUI.AccountModel;
import to_be_removed.AccountsMainController;
import to_be_removed.AccountsMainView;

public class mainViewController extends AbstractViewController{
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
		OpenAddAccountListener l = new OpenAddAccountListener();
		l.setController(this);
		return l;
	}
	public class OpenAddAccountListener extends AbstractEventListener{
		public void actionPerformed(ActionEvent arg0) {
			//still need a way of updating the model here
			try {
				System.out.println("addAccountWindow opened");
				 // <-- @TODO Replace this
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
		DeleteAccountListener l = new DeleteAccountListener();
		l.setController(this);
		return l;
	}
	public class DeleteAccountListener extends AbstractEventListener{
		
		public void actionPerformed(ActionEvent arg0) {
			try {
				
				//Access the controller
				AccountsMainController controller = (AccountsMainController) getController();
				UserModel user = controller.getUser();
			
				AccountRepository accountRepo = user.getAccountRepository();
				
				//Access the view
				//getSelectedAccount
				AccountsMainView view = (AccountsMainView) getView();
				//get selected items
				Integer accountId = view.getSelectedAccount();
				
				//delete item
				accountRepo.deleteItem(accountId);
				
				//---------------------------------------------
				// DEV PURPOSES 
				//Create a new repo to check if delete worked
				AccountRepository testRepo = new AccountRepository(new Database("MyDB"));
				AccountList accountList = testRepo.getListOfAllItems();
				for(AccountModel a : accountList) {
					System.out.println(a.toString());
				}
				//____________________________________________
				
				
				view.update();
				
				System.out.println("Delete account ID " + accountId + ".... @TODO find which account is actually selected in UI");
				
			}
			catch(Exception e) {
				System.out.println("Error when deleting account: "+e.getMessage());
			}
		}
	}

	
}
