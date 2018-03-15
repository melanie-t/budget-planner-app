import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class AccountController extends AbstractViewController {

	private AccountList list;
	
	private AccountModel model;
	private AccountView accView;
	private UserModel user;
	
	protected AccountController(AccountModel m, AccountView v) {
		model = m;
		accView = v;
		
	}
	
	private void initView() {
		
	}
	
	protected void initController() {
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
					accView.getBankTextfield().setText(accView.getTableModel().getValueAt(i, 0).toString());
					accView.getNicknameTextfield().setText(accView.getTableModel().getValueAt(i, 1).toString());
					accView.getBalanceTextfield().setText(accView.getTableModel().getValueAt(i, 2).toString());
				}
			}
		});
	}
	
	private void addButton() {
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

			//Update GUI - not complete
			update();
		}
		
		else
			System.out.println("Add error");
	}
	
	private void updateButton() {
		int i = accView.getTable().getSelectedRow();
		if (i >= 0)
		{
			// Update database
			
			
			// Update model
			accView.getTableModel().setValueAt(accView.getBankTextfield().getText(), i, 0);
			accView.getTableModel().setValueAt(accView.getNicknameTextfield().getText(), i, 1);
			accView.getTableModel().setValueAt(accView.getBalanceTextfield().getText(), i, 2);
			
			accView.getBankTextfield().setText("");
			accView.getNicknameTextfield().setText("");
			accView.getBalanceTextfield().setText("");
		}
		
		else
			System.out.println("Update error");
	}
	
	private void deleteButton() {
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
		accView.getBankTextfield().setText("");
		accView.getNicknameTextfield().setText("");
		accView.getBalanceTextfield().setText("");
	}

	
	public void setUser(UserModel user) {
		this.user = user; 
		//update the tables when this changes
		update();
	}
	public UserModel getUser() {return user;}
	
	//Update attached models
	public void update() {
		
		
		//reset table - since the ID is currently not stored in the table cannot make per row based changes...
		accView.getTableModel().setRowCount(0);
				
		AccountRepository accountRepo = user.getAccountRepository();
		if(user != null) {
			AccountList accounts = accountRepo.getListOfAllItems();
			//add rows to table
			for(AccountModel account : accounts) {
				accView.getTableModel().addRow(new Object[] {account.getBankName(), account.getNickname(), account.getBalance()});
			}
		} else {
			System.out.println("No user was set for the AccountController");
		}
	}
	
	private void ResetAddAccountInput() {
		accView.getBankTextfield().setText("");
		accView.getNicknameTextfield().setText("");
		accView.getBalanceTextfield().setText("");
	}

}
