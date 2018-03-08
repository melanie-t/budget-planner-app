import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

	public GUI() {
		accountWindow();
		transactionWindow();
	}
	
	/*
	 * Initializes the JFrame and makes it visible.
	 */
	private void initJFrame(String frameName, JPanel panel, int width, int height) {
		JFrame frame = new JFrame(frameName);
		frame.add(panel);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
	}
	
	/*
	 * Account window
	 */
	
	private void initResetButton (JButton resetButton, JTextField [] fieldReset)
	{
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					
					// Clear all textfields
					for (int i = 0; i < fieldReset.length; i++)
						fieldReset[i].setText("");
				}
				
				catch (Exception e) {
					System.out.println("Error in resetting");
				}
			};
		});
	}
	
	private void initSubmitButton (JButton submitButton, JTextField [] fieldSubmitted)
	{
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					// Controller for processing textfields
				}
				
				catch(Exception e) {
					System.out.println("Error in submitting");
				}
			}
		});
	}
	
	private void initDeleteButton (JButton deleteButton, JTextField [] entryDeleted)
	{	
		// Delete entry
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					
				}
				
				catch(Exception e) {
					System.out.println("Error in deleting");
				}
			}
		});
	}
	
	private void initEditButton (JButton editButton, JTextField [] editEntry)
	{	
		// Edit entry
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					
				}
				
				catch(Exception e) {
					System.out.println("Error in editting");
				}
			}
		});
	}
	
	private void accountWindow () {
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
					addAccountWindow();
				}
				
				catch(Exception e) {
					System.out.println("Error in opening add window");
				}
			}
		});
		
		initJFrame("Accounts", accountPanel, 400, 200);
	}
	
	/*
	 * Add account
	 */
	private void addAccountWindow () {
		JPanel addAccountPanel = new JPanel();
		
		JLabel inputLabel[] = {
				new JLabel("Bank:"), 
				new JLabel("Nickname:"), 
				new JLabel("Balance:")};
		
		JTextField accountTextfield[] = {new JTextField(20), new JTextField(20), new JTextField(20)};
		
		// Setting labels for textfields and adding to panel
		for (int i = 0; i < inputLabel.length; i++)
		{
			inputLabel[i].setLabelFor(accountTextfield[i]);
			addAccountPanel.add(inputLabel[i]);
			addAccountPanel.add(accountTextfield[i]);
		}
		
		JButton submit = new JButton("Submit");
		JButton reset = new JButton("Reset");
		
		addAccountPanel.add(submit);
		addAccountPanel.add(reset);
		
		initSubmitButton(submit, accountTextfield);
		initResetButton(reset, accountTextfield);
		
		initJFrame("Add Account", addAccountPanel, 330, 200);
	}
	
	/*
	 * Transaction window
	 */

	private void transactionWindow () {
		
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
   					addTransactionWindow();
   				}
   				
   				catch(Exception e) {
   					System.out.println("Error in opening add window");
   				}
   			}
   		});
		initJFrame("Transactions", transactionPanel, 400, 200);
	}
	
	
	/*
	 * Add transaction
	 */
	private void addTransactionWindow () {
		JPanel transactionPanel = new JPanel();
		
		JLabel transactionLabel[] = {
				new JLabel("Date:"),
				new JLabel("Amount:"),
				new JLabel("Category:")};
	
		JTextField transactionTextfield[] = {
				new JTextField(20),
				new JTextField(20),
				new JTextField(20)};

		// Setting labels for textfields and adding to panel
		for (int i = 0; i<transactionLabel.length; i++)
		{
			transactionLabel[i].setLabelFor(transactionTextfield[i]);
			transactionPanel.add(transactionLabel[i]);
			transactionPanel.add(transactionTextfield[i]);
		}
		
		JButton submit = new JButton("Submit");
		JButton reset = new JButton("Reset");

		transactionPanel.add(submit);
		transactionPanel.add(reset);
		
		initSubmitButton (submit, transactionTextfield);
		initResetButton (reset, transactionTextfield);
		initJFrame("Add Transaction", transactionPanel, 330, 200);
	}
	
	/*
	 * Initializes reset button which clears all textfields
	 */
	
	private void budgetWindow () {
		JPanel budgetPanel = new JPanel();
		
	}
	
	private void addBudgetWindow () {
		
	}
	
	private void importTransaction () {
		
	}
	
	private void viewBudget () {
		
	}
}

