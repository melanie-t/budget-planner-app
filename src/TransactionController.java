import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransactionController {

	private TransactionModel model;
	private TransactionView view;
	
	protected TransactionController(TransactionModel m, TransactionView v) {
		model = m;
		view = v;
		initView();
	}
	
	private void initView() {
		// Loads JTable
	}
	
	protected void initController() {
		view.getTransAddButton().addActionListener(e->addButton());
		view.getTransUpdateButton().addActionListener(e->updateButton());
		view.getTransDeleteButton().addActionListener(e->deleteButton());
		view.getTransTable().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){ 
		    // i = the index of the selected row
				int i = view.getTransTable().getSelectedRow();
				view.getTransTypeTextfield().setText(view.getTransModel().getValueAt(i, 0).toString());
				view.getTransDateTextfield().setText(view.getTransModel().getValueAt(i, 1).toString());
				view.getTransAmountTextfield().setText(view.getTransModel().getValueAt(i, 2).toString());
			}
		});
	}
	
	private void addButton() {
		Object[] row = new Object[3];
		
		row[0] = view.getTransTypeTextfield().getText();
		row[1] = view.getTransDateTextfield().getText();
		row[2] = view.getTransAmountTextfield().getText();
			
		if (!(row[0].toString().isEmpty() && row[1].toString().isEmpty() && row[2].toString().isEmpty()))
		{
			view.getTransModel().addRow(row);
			view.getTransTypeTextfield().setText("");
			view.getTransDateTextfield().setText("");
			view.getTransAmountTextfield().setText("");
		}
		
		else
			System.out.println("Add error");
	}
	
	private void updateButton() {
		int i = view.getTransTable().getSelectedRow();
		if (i >= 0)
		{
			view.getTransModel().setValueAt(view.getTransTypeTextfield().getText(), i, 0);
			view.getTransModel().setValueAt(view.getTransDateTextfield().getText(), i, 1);
			view.getTransModel().setValueAt(view.getTransAmountTextfield().getText(), i, 2);
			
			view.getTransTypeTextfield().setText("");
			view.getTransDateTextfield().setText("");
			view.getTransAmountTextfield().setText("");
		}
		
		else
			System.out.println("Update error");
	}
	
	private void deleteButton() {
		int i = view.getTransTable().getSelectedRow();
		if (i>=0) 
		{
			view.getTransModel().removeRow(i);
			view.getTransTypeTextfield().setText("");
			view.getTransDateTextfield().setText("");
			view.getTransAmountTextfield().setText("");
		}
		else
			System.out.println("Delete error");
	}
}
