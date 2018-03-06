
import java.util.Scanner;
public class Iteration1AppController extends AppController {
	
		Database myDatabase;
		SQLStringFactory sql;
		
		
		AccountRespository theAccountRespository;
		TransactionRepository theTransactionRepository;
		
		View accountsView;
		View transactionsView;
		
		public Iteration1AppController() {}
		
		
		
		public void start() {
			myDatabase = new Database("MyDB");
			this.sql = SQLStringFactory.getInstance();
			
			theAccountRespository = new AccountRespository(myDatabase);
			theAccountRespository.reinitSQLStructure(); // will whipe and reinstall sql tables
			
			
			theTransactionRepository = new TransactionRepository(myDatabase);
			theTransactionRepository.reinitSQLStructure(); // will whipe and reinstall sql tables
			
			InitViews();
		}
		
		protected void InitViews() {
			ViewFactory viewFactory = new ViewFactory(myDatabase);		
			accountsView = viewFactory.createView(sql.showAll("account"));
			transactionsView = viewFactory.createView(sql.showAll("transactions"));
			/* Plans: - Jordan
			 * View should be passed data and a callback function to update the data
			 * Will use Repository to access the models instead of the database
			 * Views should be able to hold multiple variables external to itself  
			*/
		}
		
		
		
		protected void displayTextView(Scanner reader, Integer choice) {
			while(choice != 4) {
				System.out.println("Please select an option:\n");
				System.out.println("1. Add an account");
				System.out.println("2. Add a transaction");
				System.out.println("3. View transactions");
				System.out.println("4. Exit");			
				
				while(!reader.hasNextInt()) {
					reader.next();
					System.out.println("Please enter a valid option.");
				}
				choice = reader.nextInt();	
				reader.nextLine();//clear the trailing \n
				switch(choice) {
				case 1:
					
					// Show interface ----------------------
					System.out.println("Add an account");
					System.out.println("Bank Name:");
					String bankName = reader.nextLine();
					System.out.println("Nickname:");
					String nickname = reader.nextLine();
					System.out.println("Balance:");
					String balance = reader.nextLine();										
					//________________________________________
					
					
					// Add an account --------------------------
					AccountModel objAccount = new AccountModel();
					objAccount.setBankName(bankName);
					objAccount.setNickName(nickname);
					objAccount.setBalance((int)Float.parseFloat(balance)*100); // are we still assuming cents? to be clarified in team meeting
					theAccountRespository.saveItem(objAccount);
					//________________________________________
					
					
					
					System.out.println("Account added successfully - Accounts:");
					System.out.println(accountsView);
					break;
				case 2:
					//add a transaction
					System.out.println("Which account would you like to add a transaction to? Please enter the accountId.");
					//show account ids and names
					accountsView.update();
					System.out.println(accountsView);
					System.out.println("\n\nAccount ID:");
					//get user input (expected: integer)
					String accountId;
					while(!reader.hasNextInt()) {
						reader.next();
						System.out.println("Please enter a valid option.");
					}
					accountId = String.valueOf(reader.nextInt());
					reader.nextLine();//clear the trailing \n
					//get date, amount and desc. of transaction
					System.out.println("Please enter the date of your transaction(DD-MM-YYYY):");
					String transDate = reader.nextLine();
					System.out.println("Please enter the amount:");
					String transAmount = reader.nextLine();
					System.out.println("Please enter a description:");
					String transDesc = reader.nextLine();
					//________________________________________
					
					
					//Update Model ---------------------------
					myDatabase.updateSQL(sql.addEntry("transactions", "NULL", accountId, transDate, transAmount, transDesc));
					//________________________________________
					
					
					
					System.out.println("Transaction added successfully - Transactions:");
					System.out.println(transactionsView);
					break;				
				case 3:
					//Viewing transactions
					System.out.println("Transactions:");
					//showAll transactions
					transactionsView.update();
					System.out.println(transactionsView);
					break;
				case 4:
					//exit
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("Please enter a valid option.");
				}
			    		
			}		
		}
		
		
		public void shutdown() {
			accountsView.shutdown();
			myDatabase.shutdown();
		}
		
		public void run() {
			int choice = 0;
			Scanner reader = new Scanner(System.in);
			displayTextView(reader, choice);
			reader.close();
		}
		
	
}
