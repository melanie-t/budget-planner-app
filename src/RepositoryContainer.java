import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * The RepositoryContainer provides a facade for the model and exposes the methods required by the view
 * and the controller.
 */
public class RepositoryContainer implements IModelView, IModelController {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private BudgetRepository budgetRepository;

    private ArrayList<IObserver> observers;

    /**
     * Constructor.
     * @param transactionRepository the transaction repository
     * @param accountRepository the account repository
     */
    public RepositoryContainer(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            BudgetRepository budgetRepository)
    {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.budgetRepository = budgetRepository;
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
    public ArrayList<Transaction> getTransactionsFromAccount(Integer fromAccount) {
        return transactionRepository.getItemsFromAccount(fromAccount);
    }

    @Override
    public ArrayList<Transaction> getTransactionsFromBudget(Integer fromBudget) {
        return transactionRepository.getItemsFromBudget(fromBudget);
    }



    @Override
    public HashMap<String, Integer> getBudgetIndexes() {
        HashMap<String, Integer> output = new HashMap<>();
        for (Budget budget : budgetRepository.getItems())
        {
            output.put(budget.getName(), budget.getId());
        }
        return output;
    }

    @Override
    public ArrayList<Budget> getAllBudgets() {
        ArrayList<Budget> allBudgets = budgetRepository.getItems();
        if (allBudgets.get(0).getId() == 1)
            allBudgets.remove(0);
        else // This should not happen, just to be safe
        {
            int nullIndex = -1;
            for (int i = 0; i < allBudgets.size(); i++)
            {
                if (allBudgets.get(1).getId() == 1)
                {
                    nullIndex = i;
                    break;
                }
            }
            if (nullIndex != -1)
                allBudgets.remove(nullIndex);
        }

        return allBudgets;
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
    public void deleteBudget(Integer budgetId) {
        budgetRepository.deleteItem(budgetId);
        transactionRepository.clearBudgetAssociations(budgetId);
        notifyObservers();
    }

    @Override
    public void saveItem(Transaction transaction) {
        Budget associatedBudget = budgetRepository.getItem(transaction.getAssociatedBudgetId());
        Account associatedAccount = accountRepository.getItem(transaction.getAssociatedAccountId());

        if (transaction.getId() != 0)
        {
            Transaction previousTransaction = transactionRepository.getItem(transaction.getId());
            int previousAmount = previousTransaction.getAmount();
            previousAmount *= previousTransaction.getType().equals("Deposit") ? 1 : -1;

            // Undo for budget and account balance
            associatedBudget.setBalance(associatedBudget.getBalance() + previousAmount);
            associatedAccount.setBalance(associatedAccount.getBalance() - previousAmount);
        }

        int amount = transaction.getAmount();
        amount *= transaction.getType().equals("Deposit") ? 1 : -1;
        associatedBudget.setBalance(associatedBudget.getBalance() - amount);
        associatedAccount.setBalance(associatedAccount.getBalance() + amount);

        budgetRepository.saveItem(associatedBudget);
        accountRepository.saveItem(associatedAccount);

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
                    1,
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
    public void saveItem(Budget budget) {
        budgetRepository.saveItem(budget);
        notifyObservers();
    }

    @Override
    public void importTransactions(String path, Integer accountId) {
        String [] tokenList = null;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = null;
            int lineNumber = 1;
            while((line = br.readLine()) != null) {
				/*
				 * tokenList maps the tokens as
				 * tokenList[0]: type
				 * tokenList[1]: date
				 * tokenList[2]: amount
				 */
                tokenList = line.split(",");

                Integer amount = Integer.parseInt(tokenList[2]);

                if(!Util.validDateString(tokenList[1]))
                    throw new InvalidInputException("Invalid date format found in import file at line " + lineNumber);

                //invalid transaction types are replaced by "Other"
                if(!Arrays.asList(Transaction.getTransactionTypes()).contains(tokenList[0]))
                    tokenList[0] = "Other";

                Transaction transaction = new Transaction();
                transaction.setAssociatedAccountId(accountId);
                transaction.setType(tokenList[0]);
                transaction.setDate(tokenList[1]);
                transaction.setAmount(amount);
                transaction.setAssociatedBudgetId(budgetRepository.getNoneBudgetId());


                saveItem(transaction);
                lineNumber++;
            }
            br.close();

        }catch(FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, fnfe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch(IOException ioe) {
            JOptionPane.showMessageDialog(null, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input for amount column", "Error", JOptionPane.ERROR_MESSAGE);
        }catch(InvalidInputException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Delete tables in the database and create new ones.
     */
    public void resetSQLStructure()
    {
        accountRepository.reinitSQLStructure();
        transactionRepository.reinitSQLStructure();
        budgetRepository.reinitSQLStructure();
        Budget noneBudget = new Budget(
                0, "None",0,0
        );
        saveItem(noneBudget);
    }

    /**
     * Deserialize the content of the database and populate the repositories
     */
    public void loadAllItems()
    {
        accountRepository.loadAllItems();
        transactionRepository.loadAllItems();
        budgetRepository.loadAllItems();
        notifyObservers();
    }

    /**
     * Create tables in the database and initialize their structure.
     */
    public void initSQLStructure()
    {
        accountRepository.initSQLStructure();
        transactionRepository.initSQLStructure();
        budgetRepository.initSQLStructure();
        // Create a default "none" budget
        Budget noneBudget = new Budget(
                0, "None",0,0
        );
        saveItem(noneBudget);
    }

    public Integer getNoneBudgetId()
    {
        return budgetRepository.getNoneBudgetId();
    }
}

class InvalidInputException extends Exception {
    InvalidInputException(String message) {super(message);}
}