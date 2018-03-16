import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class TransactionController {

	private TransactionModel transModel;
	private TransactionView transView;
	
	protected TransactionController(TransactionModel m, TransactionView v) {
		transModel = m;
		transView = v;
		initView();
	}
	
	// Loads JTable with database
	private void initView() {
		// Get account list
		//list = user.getListOfAllAccounts();
		
		// Add rows
		//for (TransactionModel account : list)
		//	view.getAccModel().addRow(new Object[]{account.getBankName(), account.getNickname(), account.getBalance()});
	}
	
	protected void initController() {
		transView.getAddButton().addActionListener(e->addTransaction());
		//transView.getUpdateButton().addActionListener(e->updateButton());
		transView.getDeleteButton().addActionListener(e->deleteTransaction());
		transView.getClearButton().addActionListener(e->clearTransaction());
		transView.getImportButton().addActionListener(e->importTransaction());
		transView.getTable().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){ 
		    // i = the index of the selected row
				int i = transView.getTable().getSelectedRow();
				if (i>=0) 
				{
					transView.getTypeTextfield().setText(transView.getModel().getValueAt(i, 0).toString());
					transView.getDateTextfield().setText(transView.getModel().getValueAt(i, 1).toString());
					transView.getAmountTextfield().setText(transView.getModel().getValueAt(i, 2).toString());
				}
			}
		});
	}
	
	private void addTransaction() {
		String type = transView.getTypeTextfield().getText();
		String date = transView.getDateTextfield().getText();
		String amount = transView.getAmountTextfield().getText();
		
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
			// Add to database
			transModel.setType(type);
			transModel.setDate(date);
			transModel.setAmount(Float.parseFloat(amount));
			//repo.saveItem(model);
			//System.out.println("Saved entry: " + type + ", " + date + ", " + amount);
			
			// Add to model
			transView.getModel().addRow(new Object[] {type, date, amount});
			transView.getTypeTextfield().setText("");
			transView.getDateTextfield().setText("");
			transView.getAmountTextfield().setText("");
		}
		
		else
			System.out.println("Add error");
	}
	
	/*
	private void updateButton() {
		int i = transView.getTable().getSelectedRow();
		if (i >= 0)
		{
			// Update database
			
			
			// Update model
			transView.getModel().setValueAt(transView.getTypeTextfield().getText(), i, 0);
			transView.getModel().setValueAt(transView.getDateTextfield().getText(), i, 1);
			transView.getModel().setValueAt(transView.getAmountTextfield().getText(), i, 2);
			
			transView.getTypeTextfield().setText("");
			transView.getDateTextfield().setText("");
			transView.getAmountTextfield().setText("");
		}
		
		else
			System.out.println("Update error");
	}
	*/
	
	private void deleteTransaction() {
		int i = transView.getTable().getSelectedRow();
		if (i>=0) 
		{
			// Remove from database
			
			// Remove from model
			transView.getModel().removeRow(i);
			transView.getTypeTextfield().setText("");
			transView.getDateTextfield().setText("");
			transView.getAmountTextfield().setText("");
		}
		else
			System.out.println("Delete error");
	}
	
	private void clearTransaction() {
		transView.getTypeTextfield().setText("");
		transView.getDateTextfield().setText("");
		transView.getAmountTextfield().setText("");
	}
	
	private void importTransaction() {
		// Import transactions
	}
}
