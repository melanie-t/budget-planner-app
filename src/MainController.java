public class MainController {
	
	public MainController() {}
	
	public MainController(MainView v)
	{
		AccountView accView = new AccountView();
		AccountModel accModel = new AccountModel();
		AccountController accController = new AccountController(accModel, accView);
		accController.initController();
		

		TransactionView transView = new TransactionView();
		TransactionModel transModel = new TransactionModel();
		TransactionController transController = new TransactionController(transModel, transView);
		transController.initController();
		
		v.setLayout(accView, transView);
	}

}
