/**
 * The IModelController interface exposes methods required from the controller objects.
 */
public interface IModelController {

    /**
     * Save the specified Transaction to the repository and the database.
     * @param transaction Transaction object to save
     */
    void saveItem(Transaction transaction);

    /**
     * Save the specified Account to the repository and the database.
     * @param account Account object to save
     */
    void saveItem(Account account);

    void saveItem(Budget budget);

    /**
     * Delete the Transaction with the specified id from the repository and the database.
     * @param transactionId transaction id
     */
    void deleteTransaction(Integer transactionId);

    /**
     * Delete the Account with the specified id from the repository and the database.
     * This will also delete all Transaction objects associated with the account.
     * @param accountId account id
     */
    void deleteAccount(Integer accountId);

    void deleteBudget(Integer budgetId);

    /**
     * Import a list of transactions from a .csv file on disk and associate them with the
     * specified account.
     * @param path filepath
     * @param accountId associated account id
     */
    void importTransactions(String path, Integer accountId);
}
