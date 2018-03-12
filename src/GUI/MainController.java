package GUI;

public class MainController {
	
	public MainController() {}
	
	public MainController(View v)
	{
		AccountModel accModel = new AccountModel();
		TransactionModel transModel = new TransactionModel();
		
		AccountController ac = new AccountController(accModel,v);
		TransactionController tc = new TransactionController(transModel,v);
		
		ac.initController();
		tc.initController();
	}

}
