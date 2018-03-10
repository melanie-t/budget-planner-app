import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import GUI.accountWindow;
import GUI.addAccountWindow;

public class DummyAppController extends AbstractAppController {
	
		Database myDatabase;
		SQLStringFactory sql;
		ViewController theViewController;
		

		public DummyAppController() {
			myDatabase = new Database("MyDB");
			this.sql = SQLStringFactory.getInstance();
			theViewController = new ViewController();
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
			
			
			
			ResultSet result = myDatabase.fetchSQL("SELECT accountId FROM account");
			
			String columnNames[] = {"accountId"};
			String columnTypes[] = {"INTEGER"};
			
			String output = "";
			try
			{
				String type = "";
				
				while(result.next())
				{
					output += "\n-------------------\n";
					for (int i = 0; i < columnNames.length; i++)
					{
						output += columnNames[i] + " -> ";
						
						type = columnTypes[i].toUpperCase();
						switch (type)
						{
						case "VARCHAR":
							output += result.getString(columnNames[i]) + "\n";
							break;
						case "INTEGER":
							output += result.getInt(columnNames[i]) + "\n";
							break;
						case "FLOAT":
							output += result.getFloat(columnNames[i]) + "\n";
							break;
						default:
							break;
						}
					}
				}	
			}
			catch (SQLException e) 
			{ 
				System.err.println(e.getMessage());
			}
			
			System.err.println(output);
			
			
			/*
				SQLValueMap where = new SQLValueMap();
				where.put("accountId", 1);
				System.out.println(sql.selectEntryUsingMap("account", where));
			*/
			
			
			/*
			 * This is an attempt to build the MVC with the new windows
			 * */
			
			
		}
		
	
}

