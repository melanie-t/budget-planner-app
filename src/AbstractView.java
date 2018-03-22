import java.util.ArrayList;

public abstract class AbstractView
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
        currentAccountSelection = accountId;
        for (AbstractView view : AbstractView.instances)
        {
            view.handleAccountSelectionChange();
        }
    }

    protected void setCurrentTransactionSelection(int transactionId)
    {
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
