import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * The ITransactionView interface exposes all the functionality required from the transaction controller.
 */
public interface ITransactionView extends IObserver
{
    /**
     * Register an ActionListener to handle an "Add transaction" request.
     * @param listener event handler
     * @param actionCommand event handler's expected actionCommand string
     */
    void registerAddActionCallback(ActionListener listener, String actionCommand);

    /**
     * Register an ActionListener to handle an "Update transaction" request.
     * @param listener event handler
     * @param actionCommand event handler's expected actionCommand string
     */
    void registerUpdateActionCallback(ActionListener listener, String actionCommand);

    /**
     * Register an ActionListener to handle an "Delete transaction" request.
     * @param listener event handler
     * @param actionCommand event handler's expected actionCommand string
     */
    void registerDeleteActionCallback(ActionListener listener, String actionCommand);

    /**
     * Register an ActionListener to handle an "Import transactions" request.
     * @param listener event handler
     * @param actionCommand event handler's expected actionCommand string
     */
    void registerImportActionCallback(ActionListener listener, String actionCommand);

    /**
     * Returns the user's input for "Type"
     * @return the type of transaction
     */
    String  getTypeInput();

    /**
     * Returns the user's input for "Date"
     * @return the date of the transaction
     */
    String  getDateInput();

    /**
     * Returns the user's input for "Amount"
     * @return the amount for transaction
     */
    Integer getAmountInput();

    /**
     * Returns the user's input for "Description"
     * @return the description of the transaction
     */
    String  getDescriptionInput();

    Integer getBudgetIdInput();

    /**
     * Returns the user's current transaction selection
     * @return the transaction id
     */
    Integer getSelectedTransactionId();

    /**
     * Returns the user's current account selection
     * @return the account id
     */
    Integer getSelectedAccountId();

    /**
     * Overrides the UI and sets the user's current transaction selection to
     * the one with the specified id
     * @param id id of the transaction to force focus on
     */
    void setSelection(Integer id);
}
