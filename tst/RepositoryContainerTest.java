import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class RepositoryContainerTest {

    TransactionRepository transactionRepoMock = mock(TransactionRepository.class);;
    AccountRepository accountRepoMock = mock(AccountRepository.class);
    RepositoryContainer testRepoContainer = new RepositoryContainer(transactionRepoMock, accountRepoMock);

    @Before
    public void resetMocks() {
        transactionRepoMock = mock(TransactionRepository.class);
        accountRepoMock = mock(AccountRepository.class);
        testRepoContainer = new RepositoryContainer(transactionRepoMock, accountRepoMock);
    }

    @Test
    public void testDeleteTransaction() {

        int transactionId = 1;
        Transaction testTransaction = new Transaction();
        testTransaction.setId(transactionId);

        //set up mock methods
        when(transactionRepoMock.getItem(transactionId)).thenReturn(testTransaction);

        //method to be tested
        testRepoContainer.deleteTransaction(transactionId);

        verify(transactionRepoMock).deleteItem(transactionId);
    }

    @Test
    public void testDeleteAccount() {

        int accountId = 1;

        //method to be tested
        testRepoContainer.deleteAccount(accountId);

        verify(accountRepoMock).deleteItem(accountId);
        verify(transactionRepoMock).deleteAllItemsFromAccount(accountId);
    }

    @Test
    public void testSaveTransactionItem() {

        Transaction testTransaction = new Transaction();

        //method to be tested
        testRepoContainer.saveItem(testTransaction);

        verify(transactionRepoMock).saveItem(testTransaction);
    }

    @Test
    public void testSaveAccountItem() {

        Account testAccount = new Account();

        //method to be tested
        testRepoContainer.saveItem(testAccount);

        verify(accountRepoMock).saveItem(testAccount);
    }

    //ImportTransaction is tested in its own class ImportTransactionTest

    @Test
    public void testResetSQLStructure() {

        //method to be tested
        testRepoContainer.resetSQLStructure();

        verify(accountRepoMock).reinitSQLStructure();
        verify(transactionRepoMock).reinitSQLStructure();
    }

    @Test
    public void testLoadAllItems() {

        //method to be tested
        testRepoContainer.loadAllItems();

        verify(accountRepoMock).loadAllItems();
        verify(transactionRepoMock).loadAllItems();
    }

    @Test
    public void testInitSQLStructure() {

        //method to be tested
        testRepoContainer.initSQLStructure();

        verify(accountRepoMock).initSQLStructure();
        verify(transactionRepoMock).initSQLStructure();
    }
}
