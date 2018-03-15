import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class AccountController {

	private AccountList list;
	private AccountRepository repo;
	private AccountModel model;
	private AccountView accView;
	private UserModel user;
	
	protected AccountController(AccountModel m, AccountView v) {
		model = m;
		accView = v;
		initView();
	}
	
	// Loads JTable with database
	private void initView() {
		// Get account list
		//list = user.getListOfAllAccounts();
		
		// Add rows
		//for (AccountModel account : list)
		//	view.getAccModel().addRow(new Object[]{account.getBankName(), account.getNickname(), account.getBalance()});
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
					accView.getBankTextfield().setText(accView.getModel().getValueAt(i, 0).toString());
					accView.getNicknameTextfield().setText(accView.getModel().getValueAt(i, 1).toString());
					accView.getBalanceTextfield().setText(accView.getModel().getValueAt(i, 2).toString());
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
			// Add to database
			model.setBankName(bank);
			model.setNickname(nickname);
			model.setBalance(Integer.parseInt(balance));
			//repo.saveItem(model);
			//System.out.println("Saved entry: " + bank + ", " + nickname + ", " + balance);
			
			// Add to model
			accView.getModel().addRow(new Object[] {bank, nickname, balance});
			accView.getBankTextfield().setText("");
			accView.getNicknameTextfield().setText("");
			accView.getBalanceTextfield().setText("");
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
			accView.getModel().setValueAt(accView.getBankTextfield().getText(), i, 0);
			accView.getModel().setValueAt(accView.getNicknameTextfield().getText(), i, 1);
			accView.getModel().setValueAt(accView.getBalanceTextfield().getText(), i, 2);
			
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
			accView.getModel().removeRow(i);
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
}
