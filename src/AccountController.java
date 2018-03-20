import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class AccountController extends AbstractController<IAccountView> {

	public AccountController(IAccountView view, IModelController model)
    {
		super(view, model);
        view.registerAddActionCallback(this, "Add");
        view.registerUpdateActionCallback(this, "Update");
        view.registerDeleteActionCallback(this, "Delete");
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand())
        {
            case "Add":
                handleAdd();
                break;
            case "Update":
                handleUpdate();
                break;
            case "Delete":
                handleDelete();
                break;
            default:
                break;
        }
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

	private void handleAdd() {
        // Accounts with an ID of 0 are considered as "new"
        handleAddOrUpdate(0);
	}
	
	private void handleUpdate() {
        handleAddOrUpdate(view.getAccountId());
	}
	
	private void handleDelete() {
        model.deleteAcccount(view.getAccountId());
        view.setSelection(0);
	}
}
