import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;

/**
 * Controller class for views that implement ITransactionView interface and models that implement
 * the IModelController interface.
 */
public class TransactionController extends AbstractController<ITransactionView>{

    /**
     * Constructor.
     * @param view associated view
     * @param model associated model
     */
	public TransactionController(ITransactionView view, IModelController model)
	{
		super(view, model);
        view.registerAddActionCallback(this, "Add");
        view.registerDeleteActionCallback(this, "Delete");
        view.registerUpdateActionCallback(this, "Update");
        view.registerImportActionCallback(this, "Import");
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
            case "Import":
                handleImport();
                break;
            default:
                break;
        }
    }
    /**
     * Creates a new transaction object and sends it to the model for saving.
     * If the specified transactionId is 0, then this is an "Add" operation. Otherwise we update
     * the existing transaction with the specified id.
     * @param transactionId account id
     */
    private void handleAddOrUpdate(Integer transactionId)
    {
        boolean success = true;

        String type = view.getTypeInput();
        String date = view.getDateInput();
        Integer amount = view.getAmountInput();
        String description = view.getDescriptionInput();
        Integer accountId = view.getSelectedAccountId();
        Integer budgetId = view.getBudgetIdInput();

        if (type.isEmpty() || date.isEmpty() || amount == null)
        {
            String message = "Please complete the fields: ";
            if (type.isEmpty())
                message += "Type. ";

            if (date.isEmpty())
                message += "Date. ";

            if (amount == null)
                message += "Amount must be a number.";

            showMessageDialog("Input Error", message);

            success = false;
        }
        else if (!Arrays.asList(Transaction.getTransactionTypes()).contains(type) ||    //validate transaction type
                !Util.validDateString(date))
        {
            String message = "Invalid Inputs: ";
            if (!Arrays.asList(Transaction.getTransactionTypes()).contains(type))
                message += "Type. ";

            if (!Util.validDateString(date))
                message += "Date. ";

            showMessageDialog("Input Error", message);

            success = false;
        }

        if (success)
        {
            Transaction transaction = new Transaction(
                    transactionId,
                    accountId,
                    budgetId,
                    type,
                    date,
                    amount,
                    description
            );
            model.saveItem(transaction);
            view.setSelection(transaction.getId());
        }
        else
            System.out.println("Add error");
    }

    /**
     * Event handler for "Add" actions from the user
     */
	private void handleAdd() {
        // Transactions with an ID of 0 are considered as "new"
        handleAddOrUpdate(0);
	}

    /**
     * Event handler for "Update" actions from the user
     */
    private void handleUpdate() {
        handleAddOrUpdate(view.getSelectedTransactionId());
    }

    /**
     * Event handler for "Delete" actions from the user
     */
	private void handleDelete() {
        model.deleteTransaction(view.getSelectedTransactionId());
        view.setSelection(0);
	}

    /**
     * Event handler for "Import" actions from the user
     */
	private void handleImport() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV file", "csv"
        );
        chooser.setFileFilter(filter);
        File file = null;
        int returnValue = chooser.showOpenDialog( null ) ;
        if( returnValue == JFileChooser.APPROVE_OPTION ) {
            file = chooser.getSelectedFile() ;
        }
        String filePath = "";
        if(file != null)
        {
            filePath = file.getPath();
        }
		model.importTransactions(filePath, view.getSelectedAccountId());
        view.setSelection(0);
	}

	protected void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

}
