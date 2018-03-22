import java.util.ArrayList;

/**
 * The IModelView interface exposes the methods required from the view objects.
 */
public interface IModelView extends IObservable{

    /**
     * Get all transactions from the model associated with the specified account id.
     * @param fromAccount account id
     * @return a list of Transactions
     */
    ArrayList<Transaction> getTransactions(Integer fromAccount);

    /**
     * Get a list of all accounts.
     * @return a list of accounts
     */
    ArrayList<Account> getAllAccounts();
}
