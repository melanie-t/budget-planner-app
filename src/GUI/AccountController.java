package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccountController extends AbstractViewController {

	private AccountModel model;
	private View view;
	
	protected AccountController(AccountModel m, View v) {
		model = m;
		view = v;
		initView();
	}
	
	private void initView() {
		// Loads JTable
	}
	
	protected void initController() {
		view.getAccAddButton().addActionListener(e->addButton());
		view.getAccUpdateButton().addActionListener(e->updateButton());
		view.getAccDeleteButton().addActionListener(e->deleteButton());
		view.getAccTable().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){ 
		    // i = the index of the selected row
				int i = view.getAccTable().getSelectedRow();
				view.getAccBankTextfield().setText(view.getAccModel().getValueAt(i, 0).toString());
				view.getAccNicknameTextfield().setText(view.getAccModel().getValueAt(i, 1).toString());
				view.getAccBalanceTextfield().setText(view.getAccModel().getValueAt(i, 2).toString());
			}
		});
	}
	
	private void addButton() {
		Object[] row = new Object[3];
		
		row[0] = view.getAccBankTextfield().getText();
		row[1] = view.getAccNicknameTextfield().getText();
		row[2] = view.getAccBalanceTextfield().getText();
			
		if (!(row[0].toString().isEmpty() && row[1].toString().isEmpty() && row[2].toString().isEmpty()))
		{
			view.getAccModel().addRow(row);
			view.getAccBankTextfield().setText("");
			view.getAccNicknameTextfield().setText("");
			view.getAccBalanceTextfield().setText("");
		}
		
		else
			System.out.println("Add error");
	}
	
	private void updateButton() {
		int i = view.getAccTable().getSelectedRow();
		if (i >= 0)
		{
			view.getAccModel().setValueAt(view.getAccBankTextfield().getText(), i, 0);
			view.getAccModel().setValueAt(view.getAccNicknameTextfield().getText(), i, 1);
			view.getAccModel().setValueAt(view.getAccBalanceTextfield().getText(), i, 2);
		}
		
		else
			System.out.println("Update error");
	}
	
	private void deleteButton() {
		int i = view.getAccTable().getSelectedRow();
		if (i>=0) 
		{
			view.getAccModel().removeRow(i);
			view.getAccBankTextfield().setText("");
			view.getAccNicknameTextfield().setText("");
			view.getAccBalanceTextfield().setText("");
		}
		else
			System.out.println("Delete error");
	}
}
