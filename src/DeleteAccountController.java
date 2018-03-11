
import java.awt.event.ActionEvent;

import GUI.addAccountWindow;

public class DeleteAccountController extends UserBasedController{
	
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
