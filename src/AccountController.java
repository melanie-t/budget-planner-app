import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AccountController extends AbstractViewController {

	private AccountList list;
	private UserModel user;
	
	protected AccountController() {
		super();
	}
	
	private void initView() {
		
	}
	
	protected void initController() {
		if(!getIsInitialized()) {
			AccountView accView = (AccountView) getView();
			accView.getAddButton().addActionListener(e->addButton());
			accView.getUpdateButton().addActionListener(e->updateButton());
			accView.getDeleteButton().addActionListener(e->deleteButton());
			accView.getClearButton().addActionListener(e->clearButton());
			accView.getTable().addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){ 
			    // i = the index of the selected row
					int i = accView.getTable().getSelectedRow();
					if (i>=0) 
					{
						AccountModel tmpAccount = getAccountDataFromRow(i);
						accView.getBankTextfield().setText(tmpAccount.getBankName());
						accView.getNicknameTextfield().setText(tmpAccount.getNickname());
						accView.getBalanceTextfield().setText(Integer.toString(tmpAccount.getBalance()));
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
		AccountView accView = (AccountView) getView();
		String bank = accView.getBankTextfield().getText();
		String nickname = accView.getNicknameTextfield().getText();
		String balance = accView.getBalanceTextfield().getText();
		
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
			//Create account
			AccountModel newAccount = new AccountModel();
			newAccount.setBankName(bank);
			newAccount.setNickname(nickname);
			newAccount.setBalance(Integer.parseInt(balance));
			
			//Save item to repo
			AccountRepository accountRepo = user.getAccountRepository();
			accountRepo.saveItem(newAccount);

			//Update GUI
			update();
		}
		
		else
			System.out.println("Add error");
	}
	
	private void updateButton() {
		AccountView accView = (AccountView) getView();
		int i = accView.getTable().getSelectedRow();
		if (i >= 0)
		{
			
			AccountModel getInitialAccountData = getAccountDataFromRow(i);
			Integer AccountId = getInitialAccountData.getId();
			
			//Get data from input - excludes ID
			AccountModel tmpAccountModel = getAccountDataFromAddAccountInput();
			tmpAccountModel.setId(AccountId); // set the ID
			updateDataRowFromModel(i, tmpAccountModel);
			
			//save to database
			user.getAccountRepository().saveItem(tmpAccountModel);
			
			resetAddAccountInput();
		}
		
		else
			System.out.println("Update error");
	}
	
	private void deleteButton() {
		AccountView accView = (AccountView) getView();
		int i = accView.getTable().getSelectedRow();
		if (i>=0) 
		{
			// Remove from database
			
			// Remove from model
			accView.getTableModel().removeRow(i);
			accView.getBankTextfield().setText("");
			accView.getNicknameTextfield().setText("");
			accView.getBalanceTextfield().setText("");
		}
		else
			System.out.println("Delete error");
	}
	
	private void clearButton() {
		AccountView accView = (AccountView) getView();
		accView.getBankTextfield().setText("");
		accView.getNicknameTextfield().setText("");
		accView.getBalanceTextfield().setText("");
	}

	
	
	//====================================
	
	//			MODEL MANIPULATION
	
	//====================================
	
	//------------------------------------
	
		// TABLE DATA MANIPULATION
		
		//------------------------------------
	
	//Add data inputs to account model
	protected AccountModel getAccountDataFromAddAccountInput() {
		AccountView accView = (AccountView) getView();
		
		AccountModel tmpAccount = new AccountModel();
		tmpAccount.setBankName(accView.getBankTextfield().getText());
		tmpAccount.setNickname(accView.getNicknameTextfield().getText());
		tmpAccount.setBalance(Integer.parseInt(accView.getBalanceTextfield().getText()));
		
		return tmpAccount;
	}
	
	

	private void resetAddAccountInput() {
		AccountView accView = (AccountView) getView();
		AccountModel blankAccount = new AccountModel();
		accView.getBankTextfield().setText(blankAccount.getBankName());
		accView.getNicknameTextfield().setText(blankAccount.getNickname());
		accView.getBalanceTextfield().setText(Integer.toString(blankAccount.getBalance()));
	}
	
	
	//------------------------------------
	
	// TABLE DATA MANIPULATION
	
	//------------------------------------
	protected AccountModel getAccountDataFromRow(Integer i) {
		AccountModel tmpAccount = new AccountModel();
		
		AccountView accView = (AccountView) getView();
		tmpAccount.setId(Integer.parseInt(accView.getTableModel().getValueAt(i, 0).toString()));
		tmpAccount.setBankName(accView.getTableModel().getValueAt(i, 1).toString());
		tmpAccount.setNickname(accView.getTableModel().getValueAt(i, 2).toString());
		tmpAccount.setBalance(Integer.parseInt(accView.getTableModel().getValueAt(i, 3).toString()));
			
		return tmpAccount;
	}
	
	protected void updateDataRowFromModel(Integer i, AccountModel account) {
		AccountView accView = (AccountView) getView();
		accView.getTableModel().setValueAt(account.getId(), i, 0);
		accView.getTableModel().setValueAt(account.getBankName(), i, 1);
		accView.getTableModel().setValueAt(account.getNickname(), i, 2);
		accView.getTableModel().setValueAt(account.getBalance(), i, 3);
	}
	
	
	
	
	//Update attached models
	public void update() {
		
		boolean success = false;
		AccountView accView = (AccountView) getView();
		//reset table - since the ID is currently not stored in the table cannot make per row based changes...
		DefaultTableModel tableData = accView.getTableModel();
		
		
		if(tableData != null) {
			//reset table 
			tableData.setRowCount(0);
			
			//update list of user accounts
			if(user != null) {
				AccountRepository accountRepo = user.getAccountRepository();
				if(user != null) {
					AccountList accounts = accountRepo.getListOfAllItems();
					//add rows to table
					for(AccountModel account : accounts) {
						accView.getTableModel().addRow(new Object[] {account.getId(), account.getBankName(), account.getNickname(), account.getBalance()});
					}
					success = true;
				} 
			}
		}
		
		if(!success) {
			System.out.println("No user was set for the AccountController");
		}
	}
	

}
