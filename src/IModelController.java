public interface IModelController {

    void saveItem(Transaction transaction);
    void saveItem(Account account);
    void deleteTransaction(Integer transactionId);
    void deleteAccount(Integer accountId);
    void importTransactions(String path, Integer accountId);
}
