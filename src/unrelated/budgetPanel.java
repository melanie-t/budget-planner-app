package unrelated;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class budgetPanel {

	public budgetPanel() { display(); } // This class should not be instantiated
	
	private JPanel budgetPanel = new JPanel();
	
	private JButton addBudget = new JButton("Add");
	private JButton deleteBudget = new JButton("Delete");
	private JButton saveBudget = new JButton("Save changes");
	private JButton submitBudget = new JButton("Submit");
	private JButton resetBudget = new JButton("Reset");
	
	private JTextField categoryInput = new JTextField(15);
	private JTextField amountInput = new JTextField(15);
	private JTextField balanceInput = new JTextField(15);
	private JTextField timeInput = new JTextField(15);

	public JPanel getPanel() { return budgetPanel; }
	public JButton getAddBudgetButton() {return addBudget;}
	public JButton getDeleteBudgetButton() {return deleteBudget;}
	public JButton getSaveBudgetButton() {return saveBudget;}
	public JButton getSubmitBudgetButton() {return submitBudget;}
	public JButton getResetBudgetButton() {return resetBudget;}
	
	public String getCategory() { return categoryInput.getText();}
	public String getAmount() { return amountInput.getText();}
	public String getBalance() { return balanceInput.getText();}
	public String getTime() { return timeInput.getText();}
	
	public void display() {
		JLabel budgetLabel = new JLabel("Budget");
		
		//Start test data
		Object[][] data = {{"Grocery", new Integer(800), new Integer(500), new Integer(20)},
				{"Restaurant", new Integer(300), new Integer(100), new Integer(20)},
				{"Fun", new Integer(150), new Integer(80), new Integer(20)}};
		//End test data
		
		String columnName[] = {
				"Category",
				"Amount",
				"Balance", 
				"Time frame (days)"};
		
		JTable budgetTable = new JTable(data, columnName);
		budgetTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		budgetTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(budgetTable);
		
		// Adding all components to the panel
		budgetPanel.add(budgetLabel);
		budgetPanel.add(addBudget);
		budgetPanel.add(deleteBudget);
		budgetPanel.add(saveBudget);
		budgetPanel.add(scrollPane);
		
		//initMethods.initJFrame("Budget", budgetPanel, 400, 200);
	}
	
	public void addBudget() {
		JPanel addBudgetPanel = new JPanel();
		
		// Creating labels
		JLabel addLabel = new JLabel("[         ADD A BUDGET         ]");
		JLabel categoryLabel = new JLabel("Category");
		JLabel amountLabel = new JLabel("Amount");
		JLabel balanceLabel = new JLabel("Balance");
		JLabel timeLabel = new JLabel("Time frame (days)");
		
		categoryLabel.setLabelFor(categoryInput);
		amountLabel.setLabelFor(amountInput);
		balanceLabel.setLabelFor(balanceInput);
		timeLabel.setLabelFor(timeInput);
		
		// Adding all components to the panel
		addBudgetPanel.add(addLabel);
		
		addBudgetPanel.add(categoryLabel);
		addBudgetPanel.add(categoryInput);
		
		addBudgetPanel.add(amountLabel);
		addBudgetPanel.add(amountInput);
		
		addBudgetPanel.add(balanceLabel);
		addBudgetPanel.add(balanceInput);
		
		addBudgetPanel.add(timeLabel);
		addBudgetPanel.add(timeInput);
		
		addBudgetPanel.add(submitBudget);
		addBudgetPanel.add(resetBudget);

		JFrame addBudgetFrame = new JFrame("Add Budget");
		addBudgetFrame.add(addBudgetPanel);
		addBudgetFrame.setSize(220, 290);
		addBudgetFrame.setVisible(true);
		addBudgetFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
	}
}
