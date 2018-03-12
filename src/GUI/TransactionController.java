package GUI;

public class TransactionController extends AbstractAppController{
	
	private TransactionModel model;
	private View view;
	
	protected TransactionController(TransactionModel model, View view) {
		this.model = model;
		this.view = view;
		initView();
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
