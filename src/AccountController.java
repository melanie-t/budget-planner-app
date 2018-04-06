import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

/**
 * Controller class for views that implement the IAccountView interface and models that implement
 * the IModelController interface.
 */
public class AccountController extends AbstractController<IAccountView> {

    /**
     * Constructor.
     * @param view associated view
     * @param model associated model
     */
	public AccountController(IAccountView view, IModelController model)
    {
		super(view, model);
        view.registerAddActionCallback(this, "Add");
        view.registerUpdateActionCallback(this, "Update");
        view.registerDeleteActionCallback(this, "Delete");
	}

    /**
     * Event listener implementation for events created by the view
     * @param e
     */
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

    /**
     * Creates a new account object and sends it to the model for saving.
     * If the specified accountId is 0, then this is an "Add" operation. Otherwise we update
     * the existing account with the specified id.
     * @param accountId account id
     */
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
            model.saveItem(account);
            view.setSelection(account.getId());
        }
        else
            System.out.println("Add error");
    }

    /**
     * Event handler for "Add" actions from the user
     */
	private void handleAdd() {
        handleAddOrUpdate(0);
	}

    /**
     * Event handler for "Update" actions from the user
     */
	private void handleUpdate() {
        handleAddOrUpdate(view.getSelectedAccountId());
	}

    /**
     * Event handler for "Delete" actions from the user
     */
	private void handleDelete() {
        model.deleteAccount(view.getSelectedAccountId());
        view.setSelection(0);
	}
}
