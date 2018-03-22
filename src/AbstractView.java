import java.util.ArrayList;

/**
 * Base class for the views. It's main functionality is to share the user's current selection on a given
 * view to the other views. This allows the view to update the transaction table whenever the user selects
 * a new account without requiring any external function calls.\n
 * Each time a new view is created, it adds itself to a private static array. Whenever modifications are
 * made to the current selection, each view in the array then updates itself accordingly.
 */
public abstract class AbstractView
{
    private static Integer currentAccountSelection = 0;
    private static Integer currentTransactionSelection = 0;
    private static ArrayList<AbstractView> instances = new ArrayList<>();

    /**
     * Constructor.
     */
    public AbstractView()
    {
        AbstractView.instances.add(this);
    }

    /**
     * Set the current account selection to the specified id and notify all other views of the change.
     * @param accountId selected account id
     */
    protected void setCurrentAccountSelection(int accountId)
    {
        currentAccountSelection = accountId;
        for (AbstractView view : AbstractView.instances)
        {
            view.handleAccountSelectionChange();
        }
    }

    /**
     * Set the current transaction selection to the specified id and notify all other views of the change.
     * @param transactionId
     */
    protected void setCurrentTransactionSelection(int transactionId)
    {
        currentTransactionSelection = transactionId;
        for (AbstractView view : AbstractView.instances)
        {
            view.handleTransactionSelectionChange();
        }
    }

    /**
     * Get the id of the currently selected account.
     * @return account id
     */
    protected int getCurrentAccountSelection() {return currentAccountSelection;}

    /**
     * Get the id of the currently selected transaction.
     * @return transaction id
     */
    protected int getCurrentTransactionSelection() {return currentTransactionSelection;}

    /**
     * Handler method called whenever changed are made to an active selection.
     */
    protected abstract void handleAccountSelectionChange();

    /**
     * Handler method called whenever changed are made to an active selection.
     */
    protected abstract void handleTransactionSelectionChange();
}
