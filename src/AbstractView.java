import javax.swing.*;
import java.util.ArrayList;

public abstract class AbstractView implements IObserver
{
    private static int currentAccountView;
    private static ArrayList<AbstractView> instances = new ArrayList<>();

    public AbstractView()
    {
        AbstractView.currentAccountView = 0;
        AbstractView.instances.add(this);
    }

    protected void setCurrentAccountView(int accountId)
    {
        currentAccountView = accountId;
        for (AbstractView view : AbstractView.instances)
        {
            view.handleAccountIdChange();
        }
    }

    protected int getCurrentAccountView() {return currentAccountView;}

    protected abstract void handleAccountIdChange();
}
