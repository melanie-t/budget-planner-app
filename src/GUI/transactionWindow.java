package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class transactionWindow {

	private transactionWindow() {
	}
	
	protected static void init () {
		
		JPanel transactionPanel = new JPanel();
		
		JLabel transactionLabel = new JLabel("Transactions");
		transactionPanel.add(transactionLabel);
		
		JButton transactionButtons[] = {
				new JButton("Add"),
				new JButton("Delete"),
				new JButton("Edit")};
		
		// Adding button action listener and button to panel
		for (int i = 0; i < transactionButtons.length; i++) {
			//transactionButtons[i].addActionListener(this);
		    transactionPanel.add(transactionButtons[i]);
		}

		String columnName[] = {
				"Date", 
				"Amount", 
				"Category"};
	
		//Start test data
		Object[][] data = {{"2018-01-01", new Integer(0), "Grocery"},
				{"2018-01-01", new Integer(1), "Grocery"},
				{"2018-01-01", new Integer(2), "Grocery"},
				{"2018-01-01", new Integer(3), "Grocery"},
				{"2018-01-01", new Integer(4), "Grocery"},
				{"2018-01-01", new Integer(5), "Grocery"}};
		
		//End test data
		
		JTable transactionTable = new JTable(data, columnName);
		transactionTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		transactionTable.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(transactionTable);

        //Add the scroll pane to this panel.
        transactionPanel.add(scrollPane);
      
        // Initializes add button when clicked, opens addAccountWindow
   		transactionButtons[0].addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent a) {
   				try {
   					addTransactionWindow.initialize();
   				}
   				
   				catch(Exception e) {
   					System.out.println("Error in opening add window");
   				}
   			}
   		});
		initMethods.initJFrame("Transactions", transactionPanel, 400, 200);
	}
}
