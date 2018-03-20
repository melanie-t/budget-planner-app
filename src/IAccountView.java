import javax.swing.*;

public interface IAccountView
{
    // Callbacks for event handlers
    JButton getAddButton();
    JButton getUpdateButton();
    JButton getDeleteButton();

    // User input values
    String getBankInput();
    String getNicknameInput();
    Integer getBalanceInput();
    Integer getAccountId();

    void setSelection(Integer id);

    JPanel  getPanel();
}
