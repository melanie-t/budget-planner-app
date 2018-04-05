import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        if(associatedAccount != null) {
        	associatedAccount.setBalance(associatedAccount.getBalance() - transactionToDelete.getAmount());
        	accountRepository.saveItem(associatedAccount);
        }
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
        Account associatedAccount = accountRepository.getItem(transaction.getAssociatedAccountId());
        // This should never happen, unless we need it for unit testing ?
        if(associatedAccount != null) {
            if (transaction.getId() != 0) {
                // Remove previous transaction ammount from account
                Transaction oldVersion = transactionRepository.getItem(transaction.getId());
                associatedAccount.setBalance(associatedAccount.getBalance() - oldVersion.getAmount());
            }
            // Add new/updated amount of transaction and save the account
        	associatedAccount.setBalance(associatedAccount.getBalance() + transaction.getAmount());
            accountRepository.saveItem(associatedAccount);
        }
        transactionRepository.saveItem(transaction);
        notifyObservers();
    }

    @Override
    public void saveItem(Account account) {

        int initialAmount;
        Transaction initialTransaction = null;

        //id 0 is reserved for new accounts
        if(account.getId() == 0 && account.getBalance() != 0) {
            initialAmount = account.getBalance();
            account.setBalance(0);
            initialTransaction = new Transaction(
                    0,
                    0,
                    "Deposit",
                     new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                    initialAmount,
                    "Initial Balance"
            );
        }

        accountRepository.saveItem(account);

        if(initialTransaction != null) {
            initialTransaction.setAssociatedAccountId(account.getId()); //update the transaction with the new account id
            saveItem(initialTransaction);
        }

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
