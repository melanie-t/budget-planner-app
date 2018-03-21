import javax.swing.*;
import java.awt.event.ActionListener;

public interface IAccountView extends IObserver
{
    void registerAddActionCallback(ActionListener listener, String actionCommand);
    void registerUpdateActionCallback(ActionListener listener, String actionCommand);
    void registerDeleteActionCallback(ActionListener listener, String actionCommand);

    String getBankInput();
    String getNicknameInput();
    Integer getBalanceInput();
    Integer getSelectedAccountId();

    void setSelection(Integer id);
}
