import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class TransactionTest {
	
	@Test
	public void setUpClass() {
		
		/* Test Transaction Creation */
		Integer transactionID = 1;
		Integer accountID = 1;
		String type = "Grocery";
		String date = "2018-02-12";
		Integer amount = 2000;
		String description = "Sandwich ingredients for the week";
		
		Transaction testTransaction = new Transaction(transactionID, accountID, type, date, amount, description);
		assertEquals(testTransaction.getId(), transactionID);
		assertEquals(testTransaction.getAssociatedAccountId(), accountID);
		assertEquals(testTransaction.getType(), type);
		assertEquals(testTransaction.getDate(), date);
		assertEquals(testTransaction.getAmount(), amount);
		assertEquals(testTransaction.getDescription(), description);
	}

	//Tests the setters and getters of the class
	@Test
	public void testUpdateTransaction() {
		
		/* Transaction Creation */
		Integer transactionID = 1;
		Integer accountID = 1;
		String type = "Grocery";
		String date = "2018-02-12";
		Integer amount = 2000;
		String description = "Sandwich ingredients for the week";
		Transaction testTransaction = new Transaction(transactionID, accountID, type, date, amount, description);
		
		/* Creating Updated Transaction */
		Integer newTransactionID = 1;
		Integer newAccountID = 2;
		Integer newAmount = 3000;
		String newType = "Snacks";
		String newDate = "2018-02-13";
		String newDescription = "Sandwich and snacks";
		
		Transaction updatedTransaction = new Transaction();
		updatedTransaction.setId(newTransactionID);
		updatedTransaction.setAssociatedAccountId(newAccountID);
		updatedTransaction.setType(newType);
		updatedTransaction.setDate(newDate);
		updatedTransaction.setDescription(newDescription);
		updatedTransaction.setAmount(newAmount);
		
		/* Updating transaction */
		testTransaction.updateWith(updatedTransaction);
		assertEquals(testTransaction.getId(), newTransactionID);
		assertEquals(testTransaction.getAssociatedAccountId(), newAccountID);
		assertEquals(testTransaction.getType(), newType);
		assertEquals(testTransaction.getDate(), newDate);
		assertEquals(testTransaction.getAmount(), newAmount);
		assertEquals(testTransaction.getDescription(), newDescription);
		
	}

	@Test
	public void testUpdateWith() {
		/* Transaction Creation */
		Integer transactionID = 1;
		Integer accountID = 1;
		String type = "Grocery";
		String date = "2018-02-12";
		Integer amount = 2000;
		String description = "Sandwich ingredients for the week";
		Transaction other = new Transaction(transactionID, accountID, type, date, amount, description);
		Transaction testTransaction = new Transaction();

		//call updateWith()
		testTransaction.updateWith(other);

		/* assert result */
		assertEquals(testTransaction.getId(),other.getId());
        assertEquals(testTransaction.getAssociatedAccountId(), other.getAssociatedAccountId());
        assertEquals(testTransaction.getType(), other.getType());
        assertEquals(testTransaction.getDate(), other.getDate());
        assertEquals(testTransaction.getAmount(), other.getAmount());
        assertEquals(testTransaction.getDescription(), other.getDescription());
	}

}
