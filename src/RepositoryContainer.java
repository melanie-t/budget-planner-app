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

        int nullIndex = -1;
        for (int i = 0; i < allBudgets.size(); i++)
        {
            if (allBudgets.get(i).getName().equals("None"))
            {
                nullIndex = i;
                break;
            }
        }
        if (nullIndex != -1)
            allBudgets.remove(nullIndex);


        return allBudgets;
    }

    @Override
    public ArrayList<Account> getAllAccounts() {
        return accountRepository.getItems();
    }

    @Override
    public String getAccountName(Integer accountId) {
        return accountRepository.getItem(accountId).getBankName();
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        Transaction transactionToDelete = transactionRepository.getItem(transactionId);
        transactionRepository.deleteItem(transactionId);

        int amount = transactionToDelete.getAmount();
        amount *= transactionToDelete.getType().equals("Deposit") ? 1 : -1;

        Account associatedAccount = accountRepository.getItem(transactionToDelete.getAssociatedAccountId());
        if(associatedAccount != null) // This should never happen, if tests fails without this then the tests are wrong
        {
        	associatedAccount.setBalance(associatedAccount.getBalance() - amount);
        	accountRepository.saveItem(associatedAccount);
        }
        Budget associatedBudget = budgetRepository.getItem(transactionToDelete.getAssociatedBudgetId());
        if(associatedBudget != null) // This should never happen, if tests fails without this then the tests are wrong
        {
            associatedBudget.setBalance(associatedBudget.getBalance() + amount);
            budgetRepository.saveItem(associatedBudget);
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
        transactionRepository.clearBudgetAssociations(budgetId, getNoneBudgetId());
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

            // Undo for budget and account balance (we can change budgets, but not accounts)
            Budget previousBudget = budgetRepository.getItem(previousTransaction.getAssociatedBudgetId());
            previousBudget.setBalance(previousBudget.getBalance() + previousAmount);
            budgetRepository.saveItem(previousBudget);
        }

        budgetRepository.saveItem(associatedBudget);
        transactionRepository.saveItem(transaction);
        
        associatedAccount.setBalance(transactionRepository.fetchBlanaceForAccount(associatedAccount.getId()));
        accountRepository.saveItem(associatedAccount);

        notifyObservers();
    }

    @Override
    public void saveItem(Account account) {

        int initialAmount;
        Transaction deltaTransaction = null;
        
        
        //id 0 is reserved for new accounts
        if(account.getId() == 0 && account.getBalance() != 0) {
            initialAmount = account.getBalance();
            account.setBalance(0);
            deltaTransaction = new Transaction();
            deltaTransaction.setDescription("Initial Balance");
            deltaTransaction.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            deltaTransaction.setType(transactionRepository.depositType);
            deltaTransaction.setAmount(initialAmount);
                 
        } else {
        	System.out.println("Account "+account.getId());
        	
        	Integer transactionAccountBalance = transactionRepository.fetchBlanaceForAccount(account.getId());
        	
        	System.out.println("Old Transaction Balance: "+transactionAccountBalance);
        	if(account.getBalance() != transactionAccountBalance) {
        		
        		Integer delta = account.getBalance()-transactionAccountBalance;
        		Integer absoluteDelta = Math.abs(delta);
        		String transactionType = delta > 0 ? transactionRepository.depositType : transactionRepository.withdrawlType;
        		String transactionDescription = "Balance Difference";
        		
        		if(delta != 0) {
        			deltaTransaction = new Transaction();
                    deltaTransaction.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    deltaTransaction.setType(transactionType);
                    deltaTransaction.setAmount(absoluteDelta);
                    deltaTransaction.setDescription(transactionDescription);
                    System.out.println("New Transaction: "+deltaTransaction.toString());
        		}
        	}
        }
        
        //Make sure Account Exists
        accountRepository.saveItem(account);

        //Save Transaction to represent the discrepancy - this will update account balance
        if(deltaTransaction != null) {
        	System.out.println("save Transaction");
        	deltaTransaction.setAssociatedAccountId(account.getId()); //update the transaction with the new account id
            saveItem(deltaTransaction);
        }


        
        notifyObservers();
    }

    @Override
    public void saveItem(Budget budget) {
        budgetRepository.saveItem(budget);
        if (!budget.getName().equals("None"))
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
    }

    public Integer getNoneBudgetId()
    {
        return budgetRepository.getNoneBudgetId();
    }

    public void forceUpdate()
    {
        notifyObservers();
    }
}

class InvalidInputException extends Exception {
    InvalidInputException(String message) {super(message);}
}