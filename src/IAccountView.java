import javax.swing.*;
import java.awt.event.ActionListener;

public interface IAccountView
{
    // Callbacks for event handlers
    void registerAddActionCallback(ActionListener listener, String actionCommand);
    void registerUpdateActionCallback(ActionListener listener, String actionCommand);
    void registerDeleteActionCallback(ActionListener listener, String actionCommand);

    // User input values
    String getBankInput();
    String getNicknameInput();
    Integer getBalanceInput();
    Integer getAccountId();

    void setSelection(Integer id);
}
