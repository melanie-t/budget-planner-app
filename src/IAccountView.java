import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * The IAccountView interface exposes all the functionality required from the account controller.
 */
public interface IAccountView extends IObserver
{
    /**
     * Register an ActionListener to handle an "Add account" request.
     * @param listener event handler
     * @param actionCommand event handler's expected actionCommand string
     */
    void registerAddActionCallback(ActionListener listener, String actionCommand);

    /**
     * Register an ActionListener to handle an "Update account" request.
     * @param listener event handler
     * @param actionCommand event handler's expected actionCommand string
     */
    void registerUpdateActionCallback(ActionListener listener, String actionCommand);

    /**
     * Register an ActionListener to handle an "Delete account" request.
     * @param listener event handler
     * @param actionCommand event handler's expected actionCommand string
     */
    void registerDeleteActionCallback(ActionListener listener, String actionCommand);

    /**
     * Returns the user's input for "Bank"
     * @return the bank name
     */
    String getBankInput();

    /**
     * Returns the user's input for "Nickname"
     * @return the nickname
     */
    String getNicknameInput();

    /**
     * Returns the user's input for "Balance"
     * @return the balance
     */
    Integer getBalanceInput();

    /**
     * Returns the user's current account selection
     * @return the account id
     */
    Integer getSelectedAccountId();

    /**
     * Overrides the UI and sets the user's current account selection to
     * the one with the specified id
     * @param id id of the account to force focus on
     */
    void setSelection(Integer id);
}
