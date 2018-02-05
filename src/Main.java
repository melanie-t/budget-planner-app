import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException
    {
		
		Database myDatabase = new Database("MyDB");
		SQLStringFactory sql = SQLStringFactory.getInstance();
		ViewFactory viewFactory = new ViewFactory(myDatabase);		
		
		myDatabase.updateSQL(sql.deleteTable("account"));		
		myDatabase.updateSQL(sql.createTable("account", "accountId", "INTEGER"));	//pass "NULL" to auto-increment
		myDatabase.updateSQL(sql.addColumn("account", "bankName", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("account", "nickname", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("account", "balance", "INTEGER"));
		
		myDatabase.updateSQL(sql.deleteTable("transactions"));		
		myDatabase.updateSQL(sql.createTable("transactions", "transactionId", "INTEGER", "accountId", "INTEGER", "account", "accountId"));	//handles foreign key
		myDatabase.updateSQL(sql.addColumn("transactions", "date", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("transactions", "amount", "INTEGER"));
		myDatabase.updateSQL(sql.addColumn("transactions", "description", "VARCHAR"));
		
		View accountsView = viewFactory.createView(sql.showAll("account"));
		View transactionsView = viewFactory.createView(sql.showAll("transactions"));
		
		
		int choice = 0;
		Scanner reader = new Scanner(System.in);
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
				//add an account
				System.out.println("Add an account");
				System.out.println("Bank Name:");
				String bankName = reader.nextLine();
				System.out.println("Nickname:");
				String nickname = reader.nextLine();
				System.out.println("Balance:");
				String balance = reader.nextLine();										
				myDatabase.updateSQL(sql.addEntry("account", "NULL", bankName, nickname, balance));
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
				myDatabase.updateSQL(sql.addEntry("transactions", "NULL", accountId, transDate, transAmount, transDesc));
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
		reader.close();
		accountsView.shutdown();
		myDatabase.shutdown();
		
    }
}
