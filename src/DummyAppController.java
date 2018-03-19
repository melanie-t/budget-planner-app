//import java.util.HashMap;
//import java.util.Iterator;
//
//public class DummyAppController implements IAppController {
//
//		Database myDatabase;
//		SQLStringFactory sql;
//
//		public DummyAppController() {
//			myDatabase = new Database("MyDB");
//			this.sql = SQLStringFactory.getInstance();
//		}
//
//
//		public void start() {
//
//		}
//
//
//
//		public void run() {
//			System.err.println("Running Dummy app");
//
//
//			//Make sure at least 1 account in there
//			AccountRepository accountRepo = new AccountRepository(myDatabase);
//			accountRepo.reinitSQLStructure();
//			Account newAccount = new Account();
//			newAccount.setBankName("TD");
//			accountRepo.saveItem(newAccount);
//
//			Account newAccount2 = new Account();
//			newAccount2.setBankName("National");
//			newAccount2.setBalance(200);
//			accountRepo.saveItem(newAccount2);
//
//
//			//Use a new repo without items loaded to test if the fetching works
//			AccountRepository blankAccountRepo = new AccountRepository(myDatabase);
//			HashMap<Integer, Account> allAccounts = blankAccountRepo.getMapOfAllItems();
//
//			Iterator it = allAccounts.entrySet().iterator();
//		    while (it.hasNext()) {
//		        HashMap.Entry pair = (HashMap.Entry)it.next();
//
//		        Account account = (Account) pair.getValue();
//		        System.out.println(account.getId() + " " + account.getBankName());
//		        it.remove(); // avoids a ConcurrentModificationException
//		    }
//
//
//		}
//
//
//}
//
