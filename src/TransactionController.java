import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TransactionController extends AbstractViewController{

	private UserModel user;
	int accountIndex;
	
	protected TransactionController() {
		super();
	}
	
	protected void initController() {
		if(!getIsInitialized()) {
			AccountView accView = (AccountView) getSecondaryView();
			TransactionView transView = (TransactionView) getView();
			transView.getAddButton().addActionListener(e->addButton());
			//transView.getUpdateButton().addActionListener(e->updateTransaction());
			transView.getDeleteButton().addActionListener(e->deleteButton());
			transView.getClearButton().addActionListener(e->clearButton());
			transView.getImportButton().addActionListener(e->importTransactionButton());
			accView.getTable().addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){ 
			    // i = the index of the selected row in Accounts
					accountIndex = accView.getTable().getSelectedRow();
					if (accountIndex >= 0) 
					{
						//Account currently selected
						if (user != null)
						{
							AccountModel selectedAccount = user.getAccountAtIndex(accountIndex);
							transView.getAccountIDTextfield().setText(String.valueOf(selectedAccount.getId()));
						}
						update();
						//System.out.println(user.getAccountAtIndex(accountIndex).toString());
					}
				}
			});
		}
		
	}
	
	//====================================
	
	//			DEPENDENCIES
	
	//====================================
	public void setUser(UserModel user) {this.user = user; }
	public UserModel getUser() {return user;}
	
	//====================================
	
	//			EVENT LISTENERS
	
	//====================================
	private void addButton() {
		TransactionView transView = (TransactionView) getView();
		String type = transView.getTypeTextfield().getText();
		String date = transView.getDateTextfield().getText();
		String amount = transView.getAmountTextfield().getText();
		String description = transView.getDescriptionTextArea().getText();
	
		if (!amount.matches("\\d+") && !amount.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Amount should contain numbers only", "Input Error", JOptionPane.ERROR_MESSAGE);
		}	
		
		if (type.isEmpty() || date.isEmpty() || amount.isEmpty())
		{
			String message = "Please complete the fields: ";
			if (type.isEmpty())
				message += "Type. ";
			
			if (date.isEmpty())
				message += "Date. ";
			
			if (amount.isEmpty())
				message += "Amount.";
			
			JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
		}	
		
		else if (!(type.isEmpty() && date.isEmpty() && amount.isEmpty()) && amount.matches("\\d+"))
		{
			//Create transaction
			TransactionModel newTransaction = new TransactionModel();
			newTransaction.setType(type);
			newTransaction.setDate(date);
			newTransaction.setAmount(Integer.parseInt(amount));
			newTransaction.setDescription(description);
		
			//Save item to repo
			user.getAccountAtIndex(accountIndex).saveTransaction(newTransaction);
			
			//Update
			update();
			clearButton();
		}
		
		else
			System.out.println("Add error");
	}
	
	private void deleteButton() {
		AccountView transView = (AccountView) getView();
		int i = transView.getTable().getSelectedRow();
		if (i >=0) 
		{
			// Remove from database
			user.getAccountAtIndex(i).deleteTransaction(getTransactionDataFromRow(i));
			
			// Remove from model
			clearButton();
			
			//Update GUI
			update();
		}
		else
			System.out.println("Delete error");
	}
	
	private void clearButton() {
		TransactionView transView = (TransactionView) getView();
		transView.getTransactionIDTextfield().setText("");
		transView.getTypeTextfield().setText("");
		transView.getDateTextfield().setText("");
		transView.getAmountTextfield().setText("");
		transView.getDescriptionTextArea().setText("");
		transView.getTable().clearSelection();
	}
	
	private void importTransactionButton() {
		// Import transaction with .csv file
		String filePath = (String)JOptionPane.showInputDialog(null, 
				"File path for .csv file","Import Transactions",JOptionPane.QUESTION_MESSAGE, null, null, "tst/spread_sheet_test_case.csv"); 
		
		//Import transaction function

	}
	
	
	//===============================================
	
	// 		TRANSACTION TABLE DATA MANIPULATION
	
	//===============================================
	//Add data inputs to account model
	protected TransactionModel getTransactionDataFromRow(Integer i) {
		TransactionModel tmpTransaction = new TransactionModel();
		
		TransactionView transView = (TransactionView) getView();
		tmpTransaction.setType(transView.getTableModel().getValueAt(i, 1).toString());
		tmpTransaction.setDate(transView.getTableModel().getValueAt(i, 2).toString());
		tmpTransaction.setAmount(Integer.parseInt(transView.getTableModel().getValueAt(i, 3).toString()));
			
		return tmpTransaction;
	}
	
	protected TransactionModel getTransactionDataFromInput() {
		TransactionView transView = (TransactionView) getView();
		TransactionModel tmpTransaction = new TransactionModel();
		tmpTransaction.setType(transView.getTypeTextfield().getText());
		tmpTransaction.setDate(transView.getDateTextfield().getText());
		tmpTransaction.setAmount(Integer.parseInt(transView.getAmountTextfield().getText()));
		
		return tmpTransaction;
	}
	
	protected void updateDataRowFromModel(Integer i, TransactionModel transaction) {
		AccountView accView = (AccountView) getView();
		accView.getTableModel().setValueAt(transaction.getId(), i, 0);
		accView.getTableModel().setValueAt(transaction.getType(), i, 1);
		accView.getTableModel().setValueAt(transaction.getDate(), i, 2);
		accView.getTableModel().setValueAt(transaction.getAmount(), i, 3);
	}
	
	//____________________________________
	
	
	
	//===========================================
	
	// 		ACCOUNT TABLE DATA MANIPULATION
	
	//===========================================
	//Gets currently selected account data
	protected AccountModel getAccountDataFromRow(Integer i) {
		AccountModel tmpAccount = new AccountModel();
		
		AccountView accView = (AccountView) getSecondaryView();
		tmpAccount.setId(Integer.parseInt(accView.getTableModel().getValueAt(i, 0).toString()));
		tmpAccount.setBankName(accView.getTableModel().getValueAt(i, 1).toString());
		tmpAccount.setNickname(accView.getTableModel().getValueAt(i, 2).toString());
		tmpAccount.setBalance(Integer.parseInt(accView.getTableModel().getValueAt(i, 3).toString()));
			
		return tmpAccount;
	}

	//_____________________________________
	
	
	
	//Update attached models
	public void update() {
		
		boolean success = false;
		TransactionView transView = (TransactionView) getView();
		
		//reset table - since the ID is currently not stored in the table cannot make per row based changes...
		
		DefaultTableModel tableData = transView.getTableModel();
		
		if(tableData != null) {
			//reset table 
			tableData.setRowCount(0);
		
			//update list of account transactions
			if(user != null) {
				TransactionList accountTransactions = new TransactionList();
				accountTransactions = user.getAccountAtIndex(accountIndex).getListOfAllTransactions();
				
				if (accountTransactions != null) {
					//add rows to table
					for(TransactionModel transaction : accountTransactions) {
						transView.getTableModel().addRow(new Object[] {transaction.getId(), transaction.getType(), transaction.getDate(), transaction.getAmount()});
					}
				}
				success = true;
			}
		}
		
		if(!success) {
			System.out.println("No user was set for the AccountController");
		}
	}
}
