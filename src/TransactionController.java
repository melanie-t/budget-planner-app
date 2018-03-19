import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TransactionController extends AbstractController<ITransactionView>{

	int accountIndex;
	
	public TransactionController(ITransactionView view, IModelController model)
	{
		super(view, model);
        view.getAddButton().addActionListener(e->addButton());
        view.getDeleteButton().addActionListener(e->deleteButton());
        view.getImportButton().addActionListener(e->importTransactionButton());
        view.getUpdateButton().addActionListener(e->updateButton());
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
            view.clearFields();
        }
        else
            System.out.println("Add error");
    }

	private void addButton() {
        // Transactions with an ID of 0 are considered as "new"
        handleAddOrUpdate(0);
	}

    private void updateButton() {
        handleAddOrUpdate(view.getTransactionId());
    }
	
	private void deleteButton() {
        model.deleteTransaction(view.getTransactionId());
        view.clearFields();
	}
	
	private void importTransactionButton() {
		// Import transaction with .csv file
		String filePath = (String)JOptionPane.showInputDialog(null, 
				"File path for .csv file","Import Transactions",JOptionPane.QUESTION_MESSAGE, null, null, "tst/spread_sheet_test_case.csv"); 
		model.importTransactions(filePath, view.getAccountId());
	}
}
