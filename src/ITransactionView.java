import javax.swing.*;
import java.awt.event.ActionListener;

public interface ITransactionView
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
    Integer getTransactionId();
    Integer getAccountId();

    void setSelection(Integer id);

    JPanel  getPanel();
}
