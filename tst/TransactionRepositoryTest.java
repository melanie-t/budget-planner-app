import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.AfterClass;

public class TransactionRepositoryTest {

	private static TransactionRepository transacRepoTest;
	private static String TestDBName;
	private static Database testDatabase;

	
	@Test
	public void setUpClass() {	    
		TestDBName = "TransactionTest";
		testDatabase = new Database(TestDBName);
	    transacRepoTest = new TransactionRepository(testDatabase);
	    transacRepoTest.reinitSQLStructure();
	    assertEquals(transacRepoTest.getItems().size(), 0);
	}
	
	@Test
	public void saveItemTest() {
		Integer associatedAccountId = 1;
		String type = "deposit";
		String date = "2018-04-03";
		Integer amount = 100;
		String description = "This is test transaction number one";

		Transaction testTransaction1 = new Transaction();
		testTransaction1.setAssociatedAccountId(associatedAccountId);
		testTransaction1.setType(type);
		testTransaction1.setDate(date);
		testTransaction1.setAmount(amount);
		testTransaction1.setDescription(description);
		transacRepoTest.saveItem(testTransaction1);

		Transaction t = transacRepoTest.getItem(associatedAccountId);
        assertTrue(t.getId().equals(1));
        assertEquals(t.getAssociatedAccountId(), associatedAccountId);
        assertEquals(t.getType(), type);
        assertEquals(t.getDate(), date);
        assertEquals(t.getAmount(), amount);
        assertEquals(t.getDescription(), description);
		
        //Test "Update" mode of saveItem
		//Attempt to save transaction with existing ID, should update item
		Integer updatedTransactionAmount = 10000;
		Transaction testTransaction2 = transacRepoTest.getItem(testTransaction1.getId());
		testTransaction2.setAmount(updatedTransactionAmount);
		transacRepoTest.saveItem(testTransaction2);
		
		assertEquals(testTransaction2.getAmount(),updatedTransactionAmount);
		transacRepoTest.deleteItem(testTransaction1.getId());
	}

	@Test
	public void deleteAllItemsFromAccountTest() {
		//will test both deleteAllItems and deleteItem
		
		
		//add two transactions to repo
		Integer associatedAccountId_1 = 1;
		String type_1 = "deposit";
		String date_1 = "2018-04-03";
		Integer amount_1 = 100;
		String description_1 = "This is test transaction number one";
		Transaction testDeleteTransaction_1 = new Transaction();
		testDeleteTransaction_1.setAssociatedAccountId(associatedAccountId_1);
		testDeleteTransaction_1.setType(type_1);
		testDeleteTransaction_1.setDate(date_1);
		testDeleteTransaction_1.setAmount(amount_1);
		testDeleteTransaction_1.setDescription(description_1);
		transacRepoTest.saveItem(testDeleteTransaction_1);
		
		Integer associatedAccountId_2 = 1;
		String type_2 = "deposit";
		String date_2 = "2018-04-05";
		Integer amount_2 = 560;
		String description_2 = "This is test transaction number two";
		Transaction testDeleteTransaction_2 = new Transaction();
		testDeleteTransaction_2.setAssociatedAccountId(associatedAccountId_2);
		testDeleteTransaction_2.setType(type_2);
		testDeleteTransaction_2.setDate(date_2);
		testDeleteTransaction_2.setAmount(amount_2);
		testDeleteTransaction_2.setDescription(description_2);
		transacRepoTest.saveItem(testDeleteTransaction_2);
		
		//should now be 2 transactions in the repo		
		ArrayList<Transaction> transList = transacRepoTest.getItemsFromAccount(associatedAccountId_2);
		assertEquals(transList.size(), 2);
			
		transacRepoTest.deleteAllItemsFromAccount(associatedAccountId_2);


		//should now be 0 transactions in the repo
		transList = transacRepoTest.getItemsFromAccount(associatedAccountId_2);
		assertEquals(transList.size(), 0);
		
	}
}
