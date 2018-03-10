import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import GUI.accountWindow;
import GUI.addAccountWindow;

public class Iteration2AppController extends AppController {
	
		Database myDatabase;
		SQLStringFactory sql;
		
		
		public Iteration2AppController() {
			myDatabase = new Database("MyDB");
			this.sql = SQLStringFactory.getInstance();
			
		}
		
				
		public void start() {
			AccountRespository theAccountRespository;
			theAccountRespository = new AccountRespository(myDatabase);
			//theAccountRespository.reinitSQLStructure(); // will whipe and reinstall sql tables
			
			TransactionRepository theTransactionRepository;
			theTransactionRepository = new TransactionRepository(myDatabase);
			//theTransactionRepository.reinitSQLStructure(); // will whipe and reinstall sql tables
		}
		
		public void shutdown() {
		
		}
		
		public void run() {
			System.out.println("Running Iteration 2 app");
			
					
			UserModel currentUser = new UserModel();
			
			//Create view
			AccountsMainView accountMainView = new AccountsMainView();
			//Attach model
			accountMainView.setModel(currentUser);
			
			
			
			//Add account controls -----------------------------
			AddAccountController addAccountControl = new AddAccountController();
			addAccountControl.setView(accountMainView);
			accountMainView.setController("add", addAccountControl);
		
			DeleteAccountController deleteAccountControl =  new DeleteAccountController();
			deleteAccountControl.setUser(currentUser);
			deleteAccountControl.setView(accountMainView);
			accountMainView.setController("delete", deleteAccountControl);
			//__________________________________________________
			
			
			
			accountMainView.update();
			accountMainView.display();
		}
		
	
}

