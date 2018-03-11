
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GUI.addAccountWindow;
import GUI.initMethods;


public class AccountsMainView extends AbstractView{
	
	JPanel viewPanel;
	String label;
	JLabel accountLabel;
	
	UserModel user;
	DefaultTableModel tableModel;
	
	public AccountsMainView() {
		super();
		//AccountModel account;
		
		label = "Accounts";
		viewPanel = new JPanel();
		accountLabel = new JLabel();
		viewPanel.add(accountLabel);
		
		
		TodoController todoController = new TodoController();
		todoController.setView(this);
		this.setControl("@TODO", todoController);
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
		//Used by DeleteAccountController
		return 1; // @TODO check interface
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
			model.addRow(new Object[]{account.getBankName(), account.getNickName(), account.getBalance()});
		
		this.setTableModel(model);
	}

	public void setTableModel(DefaultTableModel model) {
		this.tableModel = model;
	}
		
	
	//=====================================
	
	// 				DISPLAY
	
	//=====================================
	public void display() {
		//Place holder controller
		
		//@TODO replace with map of some sort
		
		//Add
		JButton addButton = new JButton("Add");
		ActionListener addAccountController = getControl("add");
		if(addAccountController != null)
	    	addButton.addActionListener(addAccountController);
		else 
			addButton.addActionListener(getControl("@TODO"));
			
		//Delete
		JButton deleteButton = new JButton("Delete");
		deleteButton.putClientProperty("view", this);
		ActionListener deleteAccountController = getControl("delete");
	    if(deleteAccountController != null)
	    	deleteButton.addActionListener(deleteAccountController);
	    else 
	    	deleteButton.addActionListener(getControl("@TODO"));
	    
	    //Edit
	    JButton editButton = new JButton("Edit");
		ActionListener editAccountController = getControl("edit");
	    if(editAccountController != null)
	    	editButton.addActionListener(editAccountController);
	    else 
	    	editButton.addActionListener(getControl("@TODO"));
		    
		    
		
		JButton accountButtons[] = {
				addButton, 
				deleteButton, 
				editButton};
		
		// Adding button action listener and button to panel
		for (int i = 0; i < accountButtons.length; i++) {
			//accountButtons[i].addActionListener(this);
			viewPanel.add(accountButtons[i]);
		}
			
		
		
		
		
		JTable accountTable = new JTable(tableModel);
		
		accountTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		accountTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(accountTable);
	
	    //Add the scroll pane to this panel.
	    viewPanel.add(scrollPane);

		initMethods.initJFrame(this.getFrameTitle(), viewPanel, 400, 200);
	
	}
}


