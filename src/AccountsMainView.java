import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AccountsMainView extends AbstractView{
	
	JPanel viewPanel;
	String label;
	JLabel accountLabel;
	MainView mainView;
	
	UserModel user;
	DefaultTableModel tableModel;
	
	public AccountsMainView() {
		super();
	}
	
	public AccountsMainView(MainView v) {
		super();
		//AccountModel account;
		
		mainView = v;
		label = "Accounts";
		viewPanel = new JPanel();
		accountLabel = new JLabel();
		viewPanel.add(accountLabel);
	}
	
	
	//=====================================
	
	// 				MODEL
	
	//=====================================
	//-------------------------------------
	// Label 
	//-------------------------------------
	public void setLabel(String label ) {this.label = label;}
	public String getLabel() {return label;}
	
	
	//-------------------------------------
	//	User 
	//-------------------------------------
	public UserModel getUser() {return user;}
	public void setUser(UserModel user) {this.user = user;}
	
	
	//=====================================
	
	// 				UI GETTERS
	
	//=====================================
	protected Integer getSelectedAccount() {
		return 1;
	}
	
	protected String getFrameTitle() {
		UserModel user = getUser();
	    String username = user.getName();
		return username+": "+label;
	}
	
	
	
	//=====================================
	
	// 				UPDATE
	
	//=====================================
	public void update() {
		this.tableModel.fireTableDataChanged();
	}

	public void setTableModel(DefaultTableModel model) {
		this.tableModel = model;
	}
	
	public void loadData() {
		accountLabel.setText(label);
		
		DefaultTableModel model = new DefaultTableModel();
		
		//get account list
		UserModel user = this.getUser();
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
		
	
	//=====================================
	
	// 				DISPLAY
	
	//=====================================
	public void display() {
		//Place holder controller		
		//@TODO replace with map of some sort
		
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
			viewPanel.add(accountButtons[i]);
		}
		
		// Loads data into the model
		loadData();
		
		JTable accountTable = new JTable(tableModel);
		
		accountTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		accountTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(accountTable);
	
	    //Add the scroll pane to this panel.
	    viewPanel.add(scrollPane);
	
	}
}


