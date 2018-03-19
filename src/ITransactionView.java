import javax.swing.*;

public interface ITransactionView
{
    // Callbacks for event handlers
    JButton getAddButton();
    JButton getDeleteButton();
    JButton getImportButton();

    // User input values
    String  getTypeInput();
    String  getDateInput();
    Integer getAmountInput();
    String  getDescriptionInput();
    Integer getTransactionId();
    Integer getAccountId();

    void    clearFields();
    JPanel  getPanel();
}
