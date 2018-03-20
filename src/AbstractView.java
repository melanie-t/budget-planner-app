import java.util.ArrayList;

public abstract class AbstractView implements IObserver
{
    private static Integer currentAccountSelection = 0;
    private static Integer currentTransactionSelection = 0;
    private static ArrayList<AbstractView> instances = new ArrayList<>();

    public AbstractView()
    {
        AbstractView.instances.add(this);
    }

    protected void setCurrentAccountSelection(int accountId)
    {
        // User just clicked the same field twice, do nothing
        if (currentAccountSelection == accountId)
            return;

        currentAccountSelection = accountId;
        for (AbstractView view : AbstractView.instances)
        {
            view.handleAccountSelectionChange();
        }
    }

    protected void setCurrentTransactionSelection(int transactionId)
    {
        // User just clicked the same field twice, do nothing
        if (currentTransactionSelection == transactionId)
            return;

        currentTransactionSelection = transactionId;
        for (AbstractView view : AbstractView.instances)
        {
            view.handleTransactionSelectionChange();
        }
    }


    protected int getCurrentAccountSelection() {return currentAccountSelection;}
    protected int getCurrentTransactionSelection() {return currentTransactionSelection;}

    protected abstract void handleAccountSelectionChange();
    protected abstract void handleTransactionSelectionChange();
}
