import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The RepositoryContainer provides a facade for the model and exposes the methods required by the view
 * and the controller.
 */
public class RepositoryContainer implements IModelView, IModelController {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    private ArrayList<IObserver> observers;

    /**
     * Constructor.
     * @param transactionRepository the transaction repository
     * @param accountRepository the account repository
     */
    public RepositoryContainer(TransactionRepository transactionRepository, AccountRepository accountRepository)
    {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.observers = new ArrayList<>();
    }

    @Override
    public void attachObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void detachObserver(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o -> o.update());
    }

    @Override
    public ArrayList<Transaction> getTransactions(Integer fromAccount) {
        return transactionRepository.getItems(fromAccount);
    }

    @Override
    public ArrayList<Account> getAllAccounts() {
        return accountRepository.getItems();
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        Transaction transactionToDelete = transactionRepository.getItem(transactionId);
        transactionRepository.deleteItem(transactionId);
        Account associatedAccount = accountRepository.getItem(transactionToDelete.getAssociatedAccountId());
        associatedAccount.setBalance(associatedAccount.getBalance() - transactionToDelete.getAmount());
        notifyObservers();
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountRepository.deleteItem(accountId);
        transactionRepository.deleteAllItemsFromAccount(accountId);
        notifyObservers();
    }

    @Override
    public void saveItem(Transaction transaction) {
        transactionRepository.saveItem(transaction);
        Account associatedAccount = accountRepository.getItem(transaction.getAssociatedAccountId());
        if(associatedAccount != null) {
        	associatedAccount.setBalance(associatedAccount.getBalance() + transaction.getAmount());
        }
        notifyObservers();
    }

    @Override
    public void saveItem(Account account) {
        accountRepository.saveItem(account);
        notifyObservers();
    }

    @Override
    public void importTransactions(String path, Integer accountId) {
        String [] tokenList = null;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = null;
            while((line = br.readLine()) != null) {
				/*
				 * tokenList maps the tokens as
				 * tokenList[0]: type
				 * tokenList[1]: date
				 * tokenList[2]: amount
				 */
                tokenList = line.split(",");

                Integer amount = Integer.parseInt(tokenList[2]);

                Transaction transaction = new Transaction();
                transaction.setAssociatedAccountId(accountId);
                transaction.setType(tokenList[0]);
                transaction.setDate(tokenList[1]);
                transaction.setAmount(amount);


                saveItem(transaction);
            }
            br.close();

        }catch(FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, fnfe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch(IOException ioe) {
            JOptionPane.showMessageDialog(null, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delete tables in the database and create new ones.
     */
    public void resetSQLStructure()
    {
        accountRepository.reinitSQLStructure();
        transactionRepository.reinitSQLStructure();
    }

    /**
     * Deserialize the content of the database and populate the repositories
     */
    public void loadAllItems()
    {
        accountRepository.loadAllItems();
        transactionRepository.loadAllItems();
        notifyObservers();
    }

    /**
     * Create tables in the database and initialize their structure.
     */
    public void initSQLStructure()
    {
        accountRepository.initSQLStructure();
        transactionRepository.initSQLStructure();
    }
}
