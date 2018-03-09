package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class budgetWindow {

	private budgetWindow(){}
	
	protected static void init() {
		JPanel budgetPanel = new JPanel();
		
		JLabel budgetLabel = new JLabel("Budget");
		budgetPanel.add(budgetLabel);
	
		JButton budgetButtons[] = {
				new JButton("Add"), 
				new JButton("Delete"), 
				new JButton("Edit")};
		
		// Adding button action listener and button to panel
		for (int i = 0; i < budgetButtons.length; i++) {
			//accountButtons[i].addActionListener(this);
			budgetPanel.add(budgetButtons[i]);
		}
		
		String columnName[] = {
				"Category",
				"Amount",
				"Balance", 
				"Time frame (days)"};
	
		//Start test data
		Object[][] data = {{"Grocery", new Integer(800), new Integer(500), new Integer(20)},
				{"Restaurant", new Integer(300), new Integer(100), new Integer(20)},
				{"Fun", new Integer(150), new Integer(80), new Integer(20)}
		};
		//End test data
		
		JTable budgetTable = new JTable(data, columnName);
		budgetTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		budgetTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(budgetTable);
	
	    //Add the scroll pane to this panel.
	    budgetPanel.add(scrollPane);
	    
	    // Initializes add button when clicked, opens addAccountWindow
		budgetButtons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					addBudgetWindow.init();
				}
				
				catch(Exception e) {
					System.out.println("Error in opening add window");
				}
			}
		});
		
		initMethods.initJFrame("Budget", budgetPanel, 400, 200);
	
	}
}
