import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.*;
import java.io.File;

public class ImportTransactionTest {
	
	private static AccountTransactionRepository testAccTransacRepo;
	private static ImportTransaction transaction;
	private static Database testDatabase;
	private static AccountModel testAcc;
	private static TransactionModel expected;
	
	@BeforeClass 
	public static void setUpClass() {
		/* Test Account */
		testAcc = new AccountModel();
		testAcc.setId(1);
		testAcc.setBalance(0);
		testAcc.setBankName("Fort Knox");
		testAcc.setNickname("Oddjob");
		
		/* Expected transaction tuple */
		Integer accountID = 1;
		String type = "deposit";
		String date = "09-09-1999";
		Integer amount = 100;
		expected = new TransactionModel();
		expected.setAccountId(accountID);
		expected.setType(type);
		expected.setDate(date);
		expected.setAmount(amount);
		
		/* Test database for the transaction */
		testDatabase = new Database("transactions");
	}
	
	
	@Test
	public void testAddTransaction() {

		testAccTransacRepo = new AccountTransactionRepository(testDatabase, testAcc);
		
		transaction = new ImportTransaction();
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
	
	@AfterClass
	
	public static void tearDown() {
		File f = new File("transactions");
		f.delete();
	}
}