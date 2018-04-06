import java.awt.event.ActionListener;

public interface IBudgetView extends IObserver
{
    void registerAddActionCallback(ActionListener listener, String actionCommand);
    void registerUpdateActionCallback(ActionListener listener, String actionCommand);
    void registerDeleteActionCallback(ActionListener listener, String actionCommand);

    String getNameInput();
    Integer getAmountInput();

    Integer getSelectedBudgetId();

    void setSelection(Integer id);


}
