import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import GUI.accountWindow;
import GUI.addAccountWindow;

public class DummyAppController extends AppController {
	
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

