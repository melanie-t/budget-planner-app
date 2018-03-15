import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AccountsMainView extends AbstractView{
	
	JPanel panel;
	DefaultTableModel model;
	JLabel accountLabel;
	JTextField bankTextfield;
	JTextField nicknameTextfield;
	JTextField balanceTextfield;
	JTable table;
	
	UserModel user;
	AccountList accountList;
	
	public AccountsMainView() {
		super();
		//AccountModel account;
		
		panel = new JPanel();
		model = new DefaultTableModel();
		bankTextfield = new JTextField(15);
		nicknameTextfield = new JTextField(15);
		balanceTextfield = new JTextField(15);
	}
	
	
	//=====================================
	
	// 				MODEL
	
	//=====================================
	//-------------------------------------
	// Label 
	//-------------------------------------
	public void setAccountLabel(JLabel label ) {this.accountLabel = label;}
	public JLabel getAccountLabel() {return accountLabel;}
	
	//=====================================
	
	// 				UI GETTERS
	
	//=====================================
	protected Integer getSelectedAccount() {
		return 1;
	}
	
	protected String getFrameTitle() {
		UserModel user = getUser();
	    String username = user.getName();
		return username+ " - " + accountLabel;
	}
	
	//-------------------------------------
	//	User 
	//-------------------------------------
	public UserModel getUser() {return user;}
	public void setUser(UserModel user) {this.user = user;}
	
	//-------------------------------------
	//	Bank 
	//-------------------------------------
	public JTextField getBankTextfield() {return this.bankTextfield;}
	public void setBankTextfield(JTextField bank) {this.bankTextfield = bank;}
	
	//-------------------------------------
	//	Nickname 
	//-------------------------------------
	public JTextField getNicknameTextfield() {return this.nicknameTextfield;}
	public void setNicknameTextfield(JTextField nicknameTextfield) {this.nicknameTextfield = nicknameTextfield;}
	
	//-------------------------------------
	//	Balance 
	//-------------------------------------
	public JTextField getBalanceTextfield() {return this.balanceTextfield;}
	public void setBalanceTextfield(JTextField balanceTextfield) {this.balanceTextfield = balanceTextfield;}
	
	
	//=====================================
	
	// 				UPDATE
	
	//=====================================
	public void update() {
		this.model.fireTableDataChanged();
	}

	public void setTableModel(DefaultTableModel model) {
		this.model = model;
	}
	
	public void loadData() {
		//get account list
		user = this.getUser();
		AccountList accounts = user.getListOfAllAccounts();
		//set columns
		model.addColumn("Bank");
		model.addColumn("Nickname");
		model.addColumn("Balance");
		
		//add rows
		for(AccountModel account : accounts) 
			model.addRow(new Object[]{account.getBankName(), account.getNickname(), account.getBalance()});
		
		this.setTableModel(model);
	}
	
	public void addAccount() {
		// Updates database
		
		// Adds to model
		model.addRow(new Object[] {bankTextfield.getText(), nicknameTextfield.getText(), balanceTextfield.getText()});
		update();
	}
		
	
	//=====================================
	
	// 				DISPLAY
	
	//=====================================
	public void display() {
		//Place holder controller		
		//@TODO replace with map of some sort
		
		accountLabel = new JLabel("Accounts");
		
		JLabel bankLabel = new JLabel("Bank");
		JLabel nicknameLabel = new JLabel("Nickname");
		JLabel balanceLabel = new JLabel("Balance");
		
		//Add
		JButton addButton = new JButton("Add");
		ActionListener addAccountController = getListener("add");
		if(addAccountController != null)
	    	addButton.addActionListener(addAccountController);
		else 
			addButton.addActionListener(todoListener());
	    
	    //Update
	    JButton updateButton = new JButton("Update");
		ActionListener updateAccountController = getListener("update");
	    if(updateAccountController != null)
	    	updateButton.addActionListener(updateAccountController);
	    else 
	    	updateButton.addActionListener(todoListener());
	    
		//Delete
		JButton deleteButton = new JButton("Delete");
		ActionListener deleteAccountController = getListener("delete");
	    if(deleteAccountController != null)
	    	deleteButton.addActionListener(deleteAccountController);
	    else 
	    	deleteButton.addActionListener(todoListener());

		JButton accountButtons[] = {
				addButton, 
				updateButton, 
				deleteButton};
		
		// Adding button to panel
		for (int i = 0; i < accountButtons.length; i++) {
			panel.add(accountButtons[i]);
		}
		
		// Loads data into the model
		loadData();
		
		JTable accountTable = new JTable(model);
		
		accountTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		accountTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(accountTable);

	
	}
}


