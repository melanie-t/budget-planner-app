import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import GUI.accountWindow;
import GUI.addAccountWindow;

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
			//-------------------------------------------------------------
			// Reset the Database
			
			theAccountRespository = new AccountRepository(myDatabase);
			theAccountRespository.reinitSQLStructure(); // will whipe and reinstall sql tables
			
			theTransactionRepository = new TransactionRepository(myDatabase);
			theTransactionRepository.reinitSQLStructure(); // will whipe and reinstall sql tables
			//_____________________________________________________________
		}
		
		
		public void run() {
			System.out.println("Running Iteration 2 app");
			
			UserModel currentUser = new UserModel();
			currentUser.SetAccountRepository(theAccountRespository);
			
			//Create view
			AccountsMainView accountMainView = new AccountsMainView();
			
			//Attach models
			accountMainView.setUser(currentUser);
			
			//-------------------------------------------------------------
			// Add controls 
			// would be nice if these didn't have to be in separate classes / files
			AddAccountWindowController addAccountControl = new AddAccountWindowController();
			accountMainView.setControl("add", addAccountControl);
		
			DeleteAccountController deleteAccountControl =  new DeleteAccountController();
			deleteAccountControl.setUser(currentUser);
			accountMainView.setControl("delete", deleteAccountControl);
			//__________________________________________________
			
			accountMainView.update();
			accountMainView.display();
		}
		
	
}

