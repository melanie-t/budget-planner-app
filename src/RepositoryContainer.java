import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RepositoryContainer implements IModelView, IModelController {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    private ArrayList<IObserver> observers;

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
    public TransactionList getTransactions(Integer fromAccount) {
        return transactionRepository.getItems(fromAccount);
    }

    @Override
    public AccountList getAllAccounts() {
        return accountRepository.getItems();
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        transactionRepository.deleteItem(transactionId);
        notifyObservers();
    }

    @Override
    public void deleteAcccount(Integer accountId) {
        accountRepository.deleteItem(accountId);
        transactionRepository.deleteAllItemsFromAccount(accountId);
        notifyObservers();
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.saveItem(transaction);
        notifyObservers();
    }

    @Override
    public void saveAccount(Account account) {
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
                transaction.setAccountId(accountId);
                transaction.setType(tokenList[0]);
                transaction.setDate(tokenList[1]);
                transaction.setAmount(amount);

                System.out.println(transaction.toString());

                saveTransaction(transaction);
            }
            br.close();

        }catch(FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, fnfe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch(IOException ioe) {
            JOptionPane.showMessageDialog(null, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // This will delete the previous tables and create new ones
    public void resetSQLStructure()
    {
        accountRepository.reinitSQLStructure();
        transactionRepository.reinitSQLStructure();
    }

    // This will create tables
    public void initSQLStructure()
    {
        accountRepository.initSQLStructure();
        transactionRepository.initSQLStructure();
    }
}
