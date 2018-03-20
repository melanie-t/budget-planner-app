import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class TransactionController extends AbstractController<ITransactionView>{

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

    private void handleAddOrUpdate(Integer transactionId)
    {
        boolean success = true;

        String type = view.getTypeInput();
        String date = view.getDateInput();
        Integer amount = view.getAmountInput();
        String description = view.getDescriptionInput();
        Integer accountId = view.getAccountId();

        if (type.isEmpty() || date.isEmpty() || amount == null)
        {
            String message = "Please complete the fields: ";
            if (type.isEmpty())
                message += "Type. ";

            if (date.isEmpty())
                message += "Date. ";    // TODO - validate date format and/or use a date type

            if (amount == null)
                message += "Amount must be a number.";

            JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);

            success = false;
        }

        if (success)
        {
            Transaction transaction = new Transaction(
                    transactionId,
                    accountId,
                    type,
                    date,
                    amount,
                    description
            );
            model.saveTransaction(transaction);
            view.setSelection(transaction.getId());
        }
        else
            System.out.println("Add error");
    }

	private void handleAdd() {
        // Transactions with an ID of 0 are considered as "new"
        handleAddOrUpdate(0);
	}

    private void handleUpdate() {
        handleAddOrUpdate(view.getTransactionId());
    }
	
	private void handleDelete() {
        model.deleteTransaction(view.getTransactionId());
        view.setSelection(0);
	}
	
	private void handleImport() {
		// Import transaction with .csv file
		String filePath = (String)JOptionPane.showInputDialog(null, 
				"File path for .csv file","Import Transactions",JOptionPane.QUESTION_MESSAGE, null, null, "tst/spread_sheet_test_case.csv"); 
		model.importTransactions(filePath, view.getAccountId());
        view.setSelection(0);
	}
}
