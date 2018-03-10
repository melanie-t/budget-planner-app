
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
	
	//AccountModel account;
	DefaultTableModel tableModel;
	
	JPanel accountPanel;
	String label;
	JLabel accountLabel;
	
	
	UserModel model;
	AccountController controller;
	
	public AccountsMainView() {
		super();
		//AccountModel account;
		
		label = "Accounts";
		accountPanel = new JPanel();
		accountLabel = new JLabel(label);
		accountPanel.add(accountLabel);
		
		
		TodoController todoController = new TodoController();
		todoController.setView(this);
		this.setControl("@TODO", todoController);
	}
	
	
	//DEPRECATED - exploratory method
	public void setTableModel(DefaultTableModel model) {
		this.tableModel = model;
	}
	
	//=============================
	
	// 			MODEL
	
	//=============================
	public void setLabel(String label ) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setModel(UserModel model) {
		this.model = model;
	}
	
	public UserModel getUser() {
		return model;
	}
	
	public Integer getSelectedAccount() {
		return 1; // @TODO check interface
	}
	
	
	
	//=============================
	
	// 			DISPLAY
	
	//=============================
	public void display() {
		//Place holder controller
		
		//@TODO replace with map of some sort
		JButton addButton = new JButton("Add");
		ActionListener addAccountController = getControl("add");
		if(addAccountController != null)
	    	addButton.addActionListener(addAccountController);
		else 
			addButton.addActionListener(getControl("@TODO"));
			
			
		JButton deleteButton = new JButton("Delete");
		ActionListener deleteAccountController = getControl("delete");
	    if(deleteAccountController != null)
	    	deleteButton.addActionListener(deleteAccountController);
	    else 
	    	deleteButton.addActionListener(getControl("@TODO"));
	    
	    JButton editButton = new JButton("Edit");
		ActionListener editAccountController = getControl("edit");
	    if(editAccountController != null)
	    	editButton.addActionListener(editAccountController);
	    else 
	    	editButton.addActionListener(getControl("@TODO"));
		    
		    
		
		JButton accountButtons[] = {
				addButton, 
				deleteButton, 
				new JButton("Edit")};
		
		// Adding button action listener and button to panel
		for (int i = 0; i < accountButtons.length; i++) {
			//accountButtons[i].addActionListener(this);
			accountPanel.add(accountButtons[i]);
		}
			
		
		// Model Data -----------------------------------------------
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Bank");
		model.addColumn("Nickname");
		model.addColumn("Balance");
		
		model.addRow(new Object[]{"Bank of Sweeden", "Sweed", new Integer(1000)});
		model.addRow(new Object[]{"Bank of Montreal", "BMO", new Integer(2000)});
		model.addRow(new Object[]{"Bank of Montreal", "BMO", new Integer(3000)});
		model.addRow(new Object[]{"Bank of Montreal", "BMO", new Integer(4000)});
		model.addRow(new Object[]{"Bank of Montreal", "BMO", new Integer(5000)});
		this.setTableModel(model);
		//___________________________________________________________
		
		
		JTable accountTable = new JTable(tableModel);
		
		accountTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		accountTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(accountTable);
	
	    //Add the scroll pane to this panel.
	    accountPanel.add(scrollPane);

	    UserModel user = getUser();
	    String username = user.getName();
		initMethods.initJFrame(username+": "+label, accountPanel, 400, 200);
	
	}
}


