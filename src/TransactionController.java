

import to_be_removed.AbstractAppController;

public class TransactionController extends AbstractAppController{
	
	private TransactionModel model;
	private MainView view;
	
	protected TransactionController() {
	}
	
	private void initView() {
		// Loads JTable
	}
	
	protected void initController() {
		//view.getTranAddButton().addActionListener(e->addButton());
		//view.getTranUpdateButton().addActionListener(e->updateButton());
		//view.getTranDeleteButton().addActionListener(e->deleteButton());
	}

	private void addButton() {
		System.out.println("Implement add button action");
	}
	
	private void updateButton() {
		System.out.println("Implement update button action");
	}
	
	private void deleteButton() {
		System.out.println("Implement delete button action");
	}
}
