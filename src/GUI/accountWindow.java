package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class accountWindow {
	
	private accountWindow() {}
	
	protected static void init() {
		
		JPanel accountPanel = new JPanel();
		
		JLabel accountLabel = new JLabel("Accounts");
		accountPanel.add(accountLabel);
	
		JButton accountButtons[] = {
				new JButton("Add"), 
				new JButton("Delete"), 
				new JButton("Edit")};
		
		// Adding button action listener and button to panel
		for (int i = 0; i < accountButtons.length; i++) {
			//accountButtons[i].addActionListener(this);
			accountPanel.add(accountButtons[i]);
		}
		
		String columnName[] = {
				"Bank", 
				"Nickname", 
				"Balance"};
	
		//Start test data
		Object[][] data = {{"Bank of Montreal", "BMO", new Integer(1000)},
				{"Bank of Montreal", "BMO", new Integer(2000)},
				{"Bank of Montreal", "BMO", new Integer(3000)},
				{"Bank of Montreal", "BMO", new Integer(4000)},
				{"Bank of Montreal", "BMO", new Integer(5000)},
				{"Bank of Montreal", "BMO", new Integer(6000)}};
		//End test data
		
		JTable accountTable = new JTable(data, columnName);
		accountTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		accountTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(accountTable);
	
	    //Add the scroll pane to this panel.
	    accountPanel.add(scrollPane);

	    // Initializes add button when clicked, opens addAccountWindow
		accountButtons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					addAccountWindow.init();
				}
				
				catch(Exception e) {
					System.out.println("Error in opening add window");
				}
			}
		});
		
		initMethods.initJFrame("Accounts", accountPanel, 400, 200);
	
	}
}