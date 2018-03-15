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
			AccountModel accModel = new AccountModel();
			AccountController accController = new AccountController(accModel, accView);
			accController.setUser(user);
			accController.initController();
			
	
			TransactionView transView = new TransactionView();
			TransactionModel transModel = new TransactionModel();
			TransactionController transController = new TransactionController(transModel, transView);
			transController.initController();
			
			MainView v = (MainView) getView();
			v.setLayout(accView, transView);
		}
	}
	
	
	
	
	
}
