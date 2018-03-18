package unrelated;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import AbstractViewController;
import UserModel;

public class BudgetController extends AbstractViewController{

	private BudgetList list;
	private UserModel user;
	
	protected BudgetController() {
		super();
	}
	
	private void initView() {
		
	}
	
	protected void initController() {
		if(!getIsInitialized()) {
			BudgetView budView = (BudgetView) getView();
			budView.getAddButton().addActionListener(e->addButton());
			budView.getUpdateButton().addActionListener(e->updateButton());
			budView.getDeleteButton().addActionListener(e->deleteButton());
			budView.getClearButton().addActionListener(e->clearButton());
			budView.getTable().addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){ 
			    // i = the index of the selected row
					int i = budView.getTable().getSelectedRow();
					if (i>=0) 
					{
						BudgetModel tmpBudget = getBudgetDataFromRow(i);
						budView.getBankTextfield().setText(tmpBudget.getBankName());
						budView.getNicknameTextfield().setText(tmpBudget.getNickname());
						budView.getNicknameTextfield().setText(tmpBudget.getNickname());
						budView.getBalanceTextfield().setText(Integer.toString(tmpBudget.getBalance()));
					}
				}
			});
			update();
		}
		
	
	
	
}
	

	//====================================
	
	//			DEPENDENCIES
	
	//====================================
	public void setUser(UserModel user) {
		this.user = user; 
		//update the tables when this changes
		update();
	}
	public UserModel getUser() {return user;}
	
	
	//====================================
	
	//			EVENT LISTENERS
	
	//====================================
	private void addButton() {
		BudgetView budView = (BudgetView) getView();
		String bank = budView.getBankTextfield().getText();
		String nickname = budView.getNicknameTextfield().getText();
		String balance = budView.getBalanceTextfield().getText();
		
		if (!balance.matches("\\d+") && !balance.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Balance should contain numbers only", "Input Error", JOptionPane.ERROR_MESSAGE);
		}	
		
		if (bank.isEmpty() || nickname.isEmpty() || balance.isEmpty())
		{
			String message = "Please complete the fields: ";
			if (bank.isEmpty())
				message += "Bank. ";
			
			if (nickname.isEmpty())
				message += "Nickname. ";
			
			if (balance.isEmpty())
				message += "Balance.";
			
			JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
		}	
		
		else if (!(bank.isEmpty() && nickname.isEmpty() && balance.isEmpty()) && balance.matches("\\d+"))
		{
			//Create budget
			BudgetModel newBudget = new BudgetModel();
			newBudget.setBankName(bank);
			newBudget.setNickname(nickname);
			newBudget.setBalance(Integer.parseInt(balance));
			
			//Save item to repo
			BudgetRepository budgetRepo = user.getBudgetRepository();
			budgetRepo.saveItem(newBudget);

			//Update GUI
			update();
		}
		
		else
			System.out.println("Add error");
	}
	
	private void updateButton() {
		BudgetView budView = (BudgetView) getView();
		int i = budView.getTable().getSelectedRow();
		if (i >= 0) 
		{
			BudgetModel getInitialBudgetData = getBudgetDataFromRow(i);
			Integer BudgetId = getInitialBudgetData.getId();
			
			//Get data from input - excludes ID
			BudgetModel tmpBudgetModel = getBudgetDataFromAddBudgetInput();
			tmpBudgetModel.setId(BudgetId); // set the ID
			System.out.println(BudgetId);
			//updateDataRowFromModel(i, tmpBudgetModel);
			
			//save to database
			user.getBudgetRepository().saveItem(tmpBudgetModel);
			
			
			update();
			// WARNING THIS WILL CAUSE THE DATATO BE REPLACED WITH BLANK DATA IF DOUBLE CLICKED!!!!
			resetAddBudgetInput();
		}
		
		else
			System.out.println("Update error");
	}
	
	private void deleteButton() {
		BudgetView budView = (BudgetView) getView();
		int i = budView.getTable().getSelectedRow();
		if (i >=0) 
		{
			// Remove from database
			BudgetModel budgetToDelete = getBudgetDataFromRow(i);
			user.getBudgetRepository().deleteItem(budgetToDelete.getId());
			
			// Remove from model
			resetAddBudgetInput();
			
			//Update GUI
			update();
		}
		else
			System.out.println("Delete error");
	}
	
	private void clearButton() {
		BudgetView budView = (BudgetView) getView();
		budView.getBankTextfield().setText("");
		budView.getNicknameTextfield().setText("");
		budView.getBalanceTextfield().setText("");
	}

	
	//====================================
	
	// 		TABLE DATA MANIPULATION
	
	//====================================
	//Add data inputs to budget model
	protected BudgetModel getBudgetDataFromAddBudgetInput() {
		BudgetView budView = (BudgetView) getView();
		
		BudgetModel tmpBudget = new BudgetModel();
		tmpBudget.setBankName(budView.getBankTextfield().getText());
		tmpBudget.setNickname(budView.getNicknameTextfield().getText());
		tmpBudget.setBalance(Integer.parseInt(budView.getBalanceTextfield().getText()));
		
		return tmpBudget;
	}
	
	private void resetAddBudgetInput() {
		BudgetView budView = (BudgetView) getView();
		budView.getBankTextfield().setText("");
		budView.getNicknameTextfield().setText("");
		budView.getBalanceTextfield().setText("");
	}
	//____________________________________
	
	
	
	//====================================
	
	// 		TABLE DATA MANIPULATION
	
	//====================================
	protected BudgetModel getBudgetDataFromRow(Integer i) {
		BudgetModel tmpBudget = new BudgetModel();
		
		BudgetView budView = (BudgetView) getView();
		tmpBudget.setId(Integer.parseInt(budView.getTableModel().getValueAt(i, 0).toString()));
		tmpBudget.setBankName(budView.getTableModel().getValueAt(i, 1).toString());
		tmpBudget.setNickname(budView.getTableModel().getValueAt(i, 2).toString());
		tmpBudget.setBalance(Integer.parseInt(budView.getTableModel().getValueAt(i, 3).toString()));
			
		return tmpBudget;
	}
	
	protected void updateDataRowFromModel(Integer i, BudgetModel budget) {
		BudgetView budView = (BudgetView) getView();
		budView.getTableModel().setValueAt(budget.getId(), i, 0);
		budView.getTableModel().setValueAt(budget.getBankName(), i, 1);
		budView.getTableModel().setValueAt(budget.getNickname(), i, 2);
		budView.getTableModel().setValueAt(budget.getBalance(), i, 3);
	}
	//_____________________________________
	
	
	
	//Update attached models
	public void update() {
		
		boolean success = false;
		BudgetView budView = (BudgetView) getView();
		//reset table - since the ID is currently not stored in the table cannot make per row based changes...
		DefaultTableModel tableData = budView.getTableModel();
		
		
		if(tableData != null) {
			//reset table 
			tableData.setRowCount(0);
			
			//update list of user budgets
			if(user != null) {
				BudgetRepository budgetRepo = user.getBudgetRepository();
				if(user != null) {
					BudgetList budgets = budgetRepo.getListOfAllItems();
					//add rows to table
					for(BudgetModel budget : budgets) {
						budView.getTableModel().addRow(new Object[] {budget.getId(), budget.getBankName(), budget.getNickname(), budget.getBalance()});
					}
					success = true;
				} 
			}
		}
		
		if(!success) {
			System.out.println("No user was set for the BudgetController");
		}
}
}
