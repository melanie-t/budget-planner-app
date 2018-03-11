import java.awt.event.ActionEvent;

public class UserBasedController extends AbstractViewController{
	UserModel user;
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	public UserModel getUser() {
		return user;
	}
	
	
	public class DeleteAccountController extends UserBasedController{
		
		public DeleteAccountController(){}
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
