import javax.swing.*;

public interface ITransactionView
{
    // Callbacks for event handlers
    JButton getAddButton();
    JButton getDeleteButton();
    JButton getImportButton();
    JButton getUpdateButton();

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
