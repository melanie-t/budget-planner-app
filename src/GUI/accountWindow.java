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

public class accountWindow {
	protected JPanel accountPanel = new JPanel();
	
	private JButton addAccount = new JButton("Add");
	private JButton deleteAccount = new JButton("Delete");
	private JButton saveAccount = new JButton("Save changes");
	private JButton submitAccount = new JButton("Submit");
	private JButton resetAccount = new JButton("Reset");

	private JTextField bankInput = new JTextField(15);
	private JTextField nicknameInput = new JTextField(15);
	private JTextField balanceInput = new JTextField(15);

	public JButton getAddAccountButton() {return addAccount;}
	public JButton getDeleteAccountButton() {return deleteAccount;}
	public JButton getSaveAccountButton() {return saveAccount;}
	public JButton getSubmitAccountButton() {return submitAccount;}
	public JButton getResetAccountButton() {return resetAccount;}
	
	public String getBank() { return bankInput.getText(); }
	public String getNickname() { return nicknameInput.getText(); }
	public String getBalance() { return balanceInput.getText(); }

	public accountWindow() {
		//AccountModel account;	
	}
	
	public void display() {
		//Start test data
		JLabel accountLabel = new JLabel("Accounts");
		
		Object[][] data = {{"Bank of Montreal", "BMO", new Integer(1000)},
				{"Bank of Montreal", "BMO", new Integer(2000)},
				{"Bank of Montreal", "BMO", new Integer(3000)},
				{"Bank of Montreal", "BMO", new Integer(4000)},
				{"Bank of Montreal", "BMO", new Integer(5000)},
				{"Bank of Montreal", "BMO", new Integer(6000)}};
		//End test data
		String columnName[] = {"Bank", "Nickname", "Balance"};
		JTable accountTable = new JTable(data, columnName);
		accountTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		accountTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(accountTable);
		
		// Adding components to panel
		accountPanel.add(accountLabel);
		accountPanel.add(addAccount);
		accountPanel.add(deleteAccount);
		accountPanel.add(saveAccount);
	    accountPanel.add(scrollPane);
		
		//initMethods.initJFrame("Accounts", accountPanel, 400, 200);		
	}
	
	public void addAccount () {		
		JPanel addAccountPanel = new JPanel();
		
		// Creating labels to be attached to textfields
		JLabel addLabel = new JLabel("[            ADD AN ACCOUNT            ]");
		JLabel bankLabel = new JLabel("Bank");
		JLabel nicknameLabel = new JLabel("Nickname");
		JLabel balanceLabel = new JLabel("Balance");
		
		// Setting labels for textfields
		bankLabel.setLabelFor(bankInput);
		nicknameLabel.setLabelFor(nicknameInput);
		balanceLabel.setLabelFor(balanceInput);
		
		addAccountPanel.add(addLabel);
		
		addAccountPanel.add(bankLabel);
		addAccountPanel.add(bankInput);
		
		addAccountPanel.add(nicknameLabel);
		addAccountPanel.add(nicknameInput);
		
		addAccountPanel.add(balanceLabel);
		addAccountPanel.add(balanceInput);
		
		addAccountPanel.add(submitAccount);
		addAccountPanel.add(resetAccount);
		
		JFrame addAccountFrame = new JFrame("Add Account");
		addAccountFrame.add(addAccountPanel);
		addAccountFrame.setSize(220, 240);
		addAccountFrame.setVisible(true);
		addAccountFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
	}
}