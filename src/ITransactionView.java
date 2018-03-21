import javax.swing.*;
import java.awt.event.ActionListener;

public interface ITransactionView extends IObserver
{
    // Callbacks for event handlers
    void registerAddActionCallback(ActionListener listener, String actionCommand);
    void registerUpdateActionCallback(ActionListener listener, String actionCommand);
    void registerDeleteActionCallback(ActionListener listener, String actionCommand);
    void registerImportActionCallback(ActionListener listener, String actionCommand);

    // User input values
    String  getTypeInput();
    String  getDateInput();
    Integer getAmountInput();
    String  getDescriptionInput();
    Integer getSelectedTransactionId();
    Integer getSelectedAccountId();

    void setSelection(Integer id);
}
