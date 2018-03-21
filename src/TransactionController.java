import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

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
        Integer accountId = view.getSelectedAccountId();

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
        handleAddOrUpdate(view.getSelectedTransactionId());
    }
	
	private void handleDelete() {
        model.deleteTransaction(view.getSelectedTransactionId());
        view.setSelection(0);
	}
	
	private void handleImport() {
        JFileChooser chooser = new JFileChooser();
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
}
