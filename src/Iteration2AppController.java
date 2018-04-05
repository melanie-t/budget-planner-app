import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Iteration2AppController implements IAppController, WindowListener{

	Database myDatabase;
	SQLStringFactory sql;
	RepositoryContainer model;
	AccountView accountView;
	TransactionView transactionView;
	BudgetView budgetView;
	AccountController accountController;
	TransactionController transactionController;

    User user;

	MainView window;

	public Iteration2AppController(User user) {
        this.user = user;
		myDatabase = new Database(user.getName());
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
		
        if (user.getName() == "Jane_Doe")
        {
            devStart();
        }
        else
        {
            productionStart();
        }
	}

	// Development Mode
	protected void devStart() {
		model.resetSQLStructure();
		InsertFakeAccounts();
	}

	//Production Mode
	protected void productionStart() {
        if (user.isNew())
        {
            model.initSQLStructure();
        }
        else
        {
            model.loadAllItems();
        }
	}

	protected void InsertFakeAccounts() {
		Account newAccount = new Account();
		newAccount.setBankName("TD");
		model.saveItem(newAccount);

		Account newAccount2 = new Account();
		newAccount2.setBankName("National");
		newAccount2.setBalance(200);
		model.saveItem(newAccount2);
	}


	private void shutdown() {
		myDatabase.shutdown();
	}

	public void run() {

		// Create window
		window = new MainView("My Money Manager - " + "User : " + user.getName(), accountView, transactionView, this);


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

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {
		shutdown();
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}
}

