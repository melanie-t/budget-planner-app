import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import GUI.AbstractAppController;
import GUI.AccountModel;
import to_be_removed.AccountsMainController;
import to_be_removed.AccountsMainView;

public class Iteration2AppController extends AbstractAppController {
	
		Database myDatabase;
		SQLStringFactory sql;
		
		public Iteration2AppController() {
			myDatabase = new Database("MyDB");
			this.sql = SQLStringFactory.getInstance();
		}
				
		AccountRepository theAccountRespository;
		TransactionRepository theTransactionRepository;
		public void start() {
			theAccountRespository = new AccountRepository(myDatabase);
			theTransactionRepository = new TransactionRepository(myDatabase);
			devStart();
			//productionStart();
		}
		
		// Development Mode
		protected void devStart() {
			theAccountRespository.reinitSQLStructure(); // will whipe and reinstall sql tables
			theTransactionRepository.reinitSQLStructure(); // will whipe and reinstall sql tables
			InsertFakeAccounts();
		}
		
		//Production Mode
		protected void productionStart() {
			Boolean isSQLStructureInitialized = true; //@TODO make check function for this
			if(isSQLStructureInitialized) {
				theAccountRespository.initSQLStructure(); // will whipe and reinstall sql tables
				theTransactionRepository.initSQLStructure(); // will whipe and reinstall sql tables
			}
		}
		
		
		
		
		protected void InsertFakeAccounts() {
			AccountModel newAccount = new AccountModel();
			newAccount.setBankName("TD");
			theAccountRespository.saveItem(newAccount);
			
			AccountModel newAccount2 = new AccountModel();
			newAccount2.setBankName("National");
			newAccount2.setBalance(200);
			theAccountRespository.saveItem(newAccount2);
			
		}
		
		
		public void run() {	
			System.out.println("Running Iteration 2 app");
			
			UserModel currentUser = new UserModel();
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
			accountMainView.setListener("add", accountMainController.openAddAccountListener()); // this will also set the view on the controller
			accountMainView.setListener("delete", accountMainController.deleteAccountListener(theAccountRespository));
			//__________________________________________________
			
			accountMainView.update();
			accountMainView.display();
		}
		
	
}

