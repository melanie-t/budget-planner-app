public class MainController extends AbstractViewController{
	
	public MainController() {
		user = null;
	}
	
	//===================================
	// USER
	//===================================
	UserModel user;
	public void setUser(UserModel user) {this.user = user;}
	public UserModel getUser() {return user;}

	public void initController() {
		if(user != null) {	
			AccountView accView = new AccountView();
			AccountController accController = new AccountController();
			accController.setView(accView);
			accController.setUser(user);
			accController.initController();
			
	
			TransactionView transView = new TransactionView();
			TransactionController transController = new TransactionController();
			transController.setSecondaryView(accView);
			transController.setView(transView);
			transController.setUser(user);
			transController.initController();
			
			MainView v = (MainView) getView();
			v.setLayout(accView, transView);
		}
	}
	
	
	
	
	
}
