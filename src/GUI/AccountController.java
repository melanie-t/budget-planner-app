package GUI;

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
