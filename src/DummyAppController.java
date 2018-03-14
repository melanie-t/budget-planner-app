import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.table.DefaultTableModel;

public class DummyAppController extends AbstractAppController {
	
		Database myDatabase;
		SQLStringFactory sql;

		public DummyAppController() {
			myDatabase = new Database("MyDB");
			this.sql = SQLStringFactory.getInstance();
		}
		
				
		public void start() {
			
		}
		
		
		
		public void run() {
			System.err.println("Running Dummy app");
			
			
			//Make sure at least 1 account in there
			AccountRepository accountRepo = new AccountRepository(myDatabase);
			accountRepo.reinitSQLStructure();
			AccountModel newAccount = new AccountModel();
			newAccount.setBankName("TD");
			accountRepo.saveItem(newAccount);
			
			AccountModel newAccount2 = new AccountModel();
			newAccount2.setBankName("National");
			newAccount2.setBalance(200);
			accountRepo.saveItem(newAccount2);
			
			
			//Use a new repo without items loaded to test if the fetching works
			AccountRepository blankAccountRepo = new AccountRepository(myDatabase);
			HashMap<Integer, AccountModel> allAccounts = blankAccountRepo.getMapOfAllItems();
			
			Iterator it = allAccounts.entrySet().iterator();
		    while (it.hasNext()) {
		        HashMap.Entry pair = (HashMap.Entry)it.next();
		        
		        AccountModel account = (AccountModel) pair.getValue(); 
		        System.out.println(account.getId() + " " + account.getBankName());
		        it.remove(); // avoids a ConcurrentModificationException
		    }
			
			
		}
		
	
}

