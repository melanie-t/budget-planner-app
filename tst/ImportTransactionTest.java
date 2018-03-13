import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.*;


public class ImportTransactionTest {
	
	@Test
	public void testImportTransaction() {
		/* Test Account */
		AccountModel testAcc = new AccountModel();
		testAcc.setId(1);
		testAcc.setBalance(0);
		testAcc.setBankName("Fort Knox");
		testAcc.setNickname("Oddjob");
		
		/* Expected transaction tuple */
		Integer accountID = 1;
		String type = "deposit";
		String date = "09-09-1999";
		Float amount = 100.0f;
		TransactionModel expected = new TransactionModel();
		expected.setAccountId(accountID);
		expected.setType(type);
		expected.setDate(date);
		expected.setAmount(amount);
		
		/* Test database for the transaction */
		Database testDatabase = new Database("transactions");
		AccountTransactionRepository testAccTransacRepo = new AccountTransactionRepository(testDatabase, testAcc);
		
		ImportTransaction transaction = new ImportTransaction();
		transaction.setAccountTransactionRepository(testAccTransacRepo);
		transaction.addTransaction("tst/spread_sheet_test_case.csv");
		
		/* 
		 * Query transactions database to fetch accountID, type, date and amount.
		 * The values obtained are stored in actualATTRIBUTE where ATTRIBUTE is either accountID, type, date or amount.
		 **/
		String dbAccountID = "select accountId from transactions;";
		String dbAmount = "select amount from transactions where accountId = " + expected.getAccountId() + ";";
		String dbDate = "select date from transactions where accountId = " + expected.getAccountId() + ";";;
		String dbType = "select description from transactions where accountId = " + expected.getAccountId() + ";";
		
		Integer actualAccountID = null;
		Float actualAmount = null;
		String actualType = null;
		String actualDate = null;
		
		try {
		actualAccountID = testDatabase.fetchSQL(dbAccountID).getInt("accountId");
		actualAmount = testDatabase.fetchSQL(dbAmount).getFloat("amount");
		actualType = testDatabase.fetchSQL(dbType).getString("description");
		actualDate = testDatabase.fetchSQL(dbDate).getString("date");
		
		}catch(SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
		
		assertEquals(expected.getAccountId(), actualAccountID);
		assertEquals(expected.getAmount(), actualAmount);
		assertEquals(expected.getDate(), actualDate);
		assertEquals(expected.getType(), actualType);
	}
}