public class Iteration2AppController implements IAppController {

	Database myDatabase;
	SQLStringFactory sql;
	RepositoryContainer model;
	AccountView accountView;
	TransactionView transactionView;
	AccountController accountController;
	TransactionController transactionController;
	MainView window;

	public Iteration2AppController() {
		myDatabase = new Database("MyDB");
		sql = SQLStringFactory.getInstance();

		// Create model
		AccountRepository accounts = new AccountRepository(myDatabase);
		TransactionRepository transactions = new TransactionRepository(myDatabase);
		model = new RepositoryContainer(transactions, accounts);

		// Create views
		accountView = new AccountView(model);
		transactionView = new TransactionView(model);

		// Create controllers
		accountController = new AccountController(accountView, model);
		transactionController = new TransactionController(transactionView, model);
	}


	public void start() {
		devStart();
		//productionStart();
	}

	// Development Mode
	protected void devStart() {
		model.resetSQLStructure();
		InsertFakeAccounts();
	}

	//Production Mode
	protected void productionStart() {
		Boolean isSQLStructureInitialized = true; //@TODO make check function for this
		if(!isSQLStructureInitialized) {
			model.initSQLStructure();
		}
	}

	protected void InsertFakeAccounts() {
		Account newAccount = new Account();
		newAccount.setBankName("TD");
		model.saveAccount(newAccount);

		Account newAccount2 = new Account();
		newAccount2.setBankName("National");
		newAccount2.setBalance(200);
		model.saveAccount(newAccount2);
	}


	@Override
	public void shutdown() {
//		myDatabase.shutdown();
	}

	public void run() {

		// user using this app - does nothing for now
		User currentUser = new User();

		// Create window
		MainView v = new MainView("My Money Manager", accountView, transactionView);

		/*
		mainController.setAccountRepository(theAccountRespository);
		mainController.setTransactionRepository(theTransactionRepository);
		*/




		/*

		System.out.println("Running Iteration 2 app");

		User currentUser = new User();
		currentUser.setAccountRepository(theAccountRespository);


		AccountsMainController accountMainController = new AccountsMainController();
		accountMainController.setUser(currentUser);

		//Create view
		AccountsMainView accountMainView = new AccountsMainView();

		//Attach models
		accountMainView.setUser(currentUser);

		//-------------------------------------------------------------
		// Add controls
		// would be nice if these didn't have to be in separate classes / files
		accountMainView.setListener("delete", accountMainController.deleteAccountListener(theAccountRespository));
		//__________________________________________________

		accountMainView.update();
		accountMainView.display();
		*/
	}


}

