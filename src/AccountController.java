import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AccountController extends AbstractController<IAccountView> {

	public AccountController(IAccountView view, IModelController model)
    {
		super(view, model);
        view.getAddButton().addActionListener(e->addButton());
        view.getUpdateButton().addActionListener(e->updateButton());
        view.getDeleteButton().addActionListener(e->deleteButton());
	}

    private void handleAddOrUpdate(Integer accountId)
    {
        boolean success = true;

        String bank = view.getBankInput();
        String nickname = view.getNicknameInput();
        Integer balance = view.getBalanceInput();

        if (bank.isEmpty() || nickname.isEmpty() || balance == null)
        {
            String message = "Please complete the fields: ";
            if (bank.isEmpty())
                message += "Bank. ";

            if (nickname.isEmpty())
                message += "Nickname. ";

            if (balance == null)
                message += "Balance must be a number.";

            JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);

            success = false;
        }

        if (success)
        {
            Account account = new Account(
                    accountId,
                    bank,
                    nickname,
                    balance
            );
            model.saveAccount(account);
            view.setSelection(account.getId());
        }
        else
            System.out.println("Add error");
    }

	private void addButton() {
        // Accounts with an ID of 0 are considered as "new"
        handleAddOrUpdate(0);
	}
	
	private void updateButton() {
        handleAddOrUpdate(view.getAccountId());
	}
	
	private void deleteButton() {
        model.deleteAcccount(view.getAccountId());
        view.setSelection(0);
	}
}
