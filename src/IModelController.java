public interface IModelController {

    void saveTransaction(Transaction transaction);
    void saveAccount(Account account);
    void deleteTransaction(Integer transactionId);
    void deleteAcccount(Integer accountId);
    void importTransactions(String path, Integer accountId);
}
