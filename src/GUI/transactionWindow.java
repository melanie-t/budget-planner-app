package GUI;

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

public class transactionWindow {

	public transactionWindow() {}
	
	protected JPanel transactionPanel = new JPanel();
	private JButton addTransaction = new JButton("Add");
	private JButton deleteTransaction = new JButton("Delete");
	private JButton saveTransaction = new JButton("Save changes");
	private JButton submitTransaction = new JButton("Submit");
	private JButton resetTransaction = new JButton("Reset");
	
	private JTextField dateInput = new JTextField(15);
	private JTextField amountInput = new JTextField(15);
	private JTextField categoryInput = new JTextField(15);
	
	public JButton getAddTransactionButton() {return addTransaction;}
	public JButton getDeleteTransactionButton() {return deleteTransaction;}
	public JButton getSaveTransactionButton() {return saveTransaction;}
	public JButton getSubmitTransactionButton() {return submitTransaction;}
	public JButton getResetTransactionButton() {return resetTransaction;}
	
	public String getDate() { return dateInput.getText(); }
	public String getAmount() { return amountInput.getText(); }
	public String getCategory() { return categoryInput.getText(); }
	
	public void display () {
		
		JLabel transactionLabel = new JLabel("Transactions");
		
		
		//Start test data
		Object[][] data = {{"2018-01-01", new Integer(0), "Grocery"},
				{"2018-01-01", new Integer(1), "Grocery"},
				{"2018-01-01", new Integer(2), "Grocery"},
				{"2018-01-01", new Integer(3), "Grocery"},
				{"2018-01-01", new Integer(4), "Grocery"},
				{"2018-01-01", new Integer(5), "Grocery"}};
		
		//End test data
		
		String columnName[] = {
				"Date", 
				"Amount", 
				"Category"};
	
		JTable transactionTable = new JTable(data, columnName);
		transactionTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		transactionTable.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(transactionTable);
   	
   		transactionPanel.add(transactionLabel);
   		transactionPanel.add(addTransaction);
   		transactionPanel.add(deleteTransaction);
   		transactionPanel.add(saveTransaction);
        transactionPanel.add(scrollPane);
   		
		//initMethods.initJFrame("Transactions", transactionPanel, 400, 200);
	}
	
	private void addTransaction() {
		JPanel addTransactionPanel = new JPanel();
		JButton resetTransaction = new JButton("Reset");
		
		JLabel addLabel = new JLabel("[         ADD A TRANSACTION         ]");
		JLabel dateLabel = new JLabel("Date");
		JLabel amountLabel = new JLabel("Amount");
		JLabel categoryLabel = new JLabel("Category");
		
		// Setting labels for textfields
		dateLabel.setLabelFor(dateInput);
		amountLabel.setLabelFor(amountInput);
		categoryLabel.setLabelFor(categoryInput);

		// Add all components to panel
		addTransactionPanel.add(addLabel);
		
		addTransactionPanel.add(dateLabel);
		addTransactionPanel.add(dateInput);
		
		addTransactionPanel.add(amountLabel);
		addTransactionPanel.add(amountInput);
		
		addTransactionPanel.add(categoryLabel);
		addTransactionPanel.add(categoryInput);
		
		addTransactionPanel.add(submitTransaction);
		addTransactionPanel.add(resetTransaction);
		
		JFrame addTransactionFrame = new JFrame("Add Transaction");
		addTransactionFrame.add(addTransactionPanel);
		addTransactionFrame.setSize(220, 240);
		addTransactionFrame.setVisible(true);
		addTransactionFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
	}
}
