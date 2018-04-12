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
	BudgetController budgetController;

    User user;

	MainView window;

	public Iteration2AppController(User user) {
        this.user = user;

		myDatabase = new Database(user.getName());
		sql = SQLStringFactory.getInstance();

		// Create model
		AccountRepository accounts = new AccountRepository(myDatabase);
		TransactionRepository transactions = new TransactionRepository(myDatabase);
		BudgetRepository budgets = new BudgetRepository((myDatabase));
		model = new RepositoryContainer(transactions, accounts, budgets);
	}


	public void start() {
        if (user.getName() == "Jane_Doe")
            devStart();
        else
            productionStart();
	}

	// Development Mode
	protected void devStart() {
		model.resetSQLStructure();
		InsertTestingEntries();
		startMVC();
	}
	
	protected void InsertTestingEntries() {
		
		//-------------------------------
		// Accounts 
		//-------------------------------
		Account newAccount = new Account();
		newAccount.setBankName("Toronto Dominion");
		newAccount.setNickname("TD");
		model.saveItem(newAccount);

		Account newAccount2 = new Account();
		newAccount2.setBankName("Bank National");
		newAccount2.setNickname("National");
		newAccount2.setBalance(200);
		model.saveItem(newAccount2);
		//-------------------------------
		
		
		//-------------------------------
		// Budgets 
		//-------------------------------
		Budget newBudget1 = new Budget();
		newBudget1.setName("Resteraunts");
		newBudget1.setAmount(200);
		model.saveItem(newBudget1);
		//-------------------------------
		
		

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
        startMVC();
	}
	

	protected void startMVC() {
		// Create views
		accountView = new AccountView(model);
		transactionView = new TransactionView(model);
		budgetView = new BudgetView(model);

		// Create controllers
		accountController = new AccountController(accountView, model);
		transactionController = new TransactionController(transactionView, model);
		budgetController = new BudgetController(budgetView, model);

		model.forceUpdate();
	}

	
	private void shutdown() {
		myDatabase.shutdown();
	}

	public void run() {
		// Create window
		window = new MainView("My Money Manager - " + "User : " + user.getName(), accountView, transactionView, budgetView, this);
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

