//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import java.sql.SQLException;
//import static org.junit.Assert.*;
//import java.io.File;
//
//public class ImportTransactionTest {
//
//	private static IModelController model;
//	private static Database testDatabase;
//	private static Account testAcc;
//	private static Transaction expected;
//
//	@BeforeClass
//	public static void setUpClass() {
//		/* Test Account */
//		testAcc = new Account();
//		testAcc.setId(1);
//		testAcc.setBalance(0);
//		testAcc.setBankName("Fort Knox");
//		testAcc.setNickname("Oddjob");
//
//		/* Expected transaction tuple */
//		Integer accountID = 1;
//		String type = "deposit";
//		String date = "09-09-1999";
//		Integer amount = 100;
//		expected = new Transaction();
//		expected.setAssociatedAccountId(accountID);
//		expected.setType(type);
//		expected.setDate(date);
//		expected.setAmount(amount);
//
//		/* Test database for the transaction */
//		testDatabase = new Database("transactions");
//	}
//
//
//	@Test
//	public void testAddTransaction() {
//
//        model = new RepositoryContainer(new TransactionRepository(testDatabase), new AccountRepository(testDatabase));
//
//        model.importTransactions("../tst/spread_sheet_test_case.csv", 1);
//
//		/*
//		 * Query transactions database to fetch accountID, type, date and amount.
//		 * The values obtained are stored in actualATTRIBUTE where ATTRIBUTE is either accountID, type, date or amount.
//		 **/
//		String dbAccountID = "select accountId from transactions;";
//		String dbAmount = "select amount from transactions where accountId = " + expected.getAssociatedAccountId() + ";";
//		String dbDate = "select date from transactions where accountId = " + expected.getAssociatedAccountId() + ";";;
//		String dbType = "select description from transactions where accountId = " + expected.getAssociatedAccountId() + ";";
//
//		Integer actualAccountID = null;
//		Float actualAmount = null;
//		String actualType = null;
//		String actualDate = null;
//
//		try {
//		actualAccountID = testDatabase.fetchSQL(dbAccountID).getInt("accountId");
//		actualAmount = testDatabase.fetchSQL(dbAmount).getFloat("amount");
//		actualType = testDatabase.fetchSQL(dbType).getString("description");
//		actualDate = testDatabase.fetchSQL(dbDate).getString("date");
//
//		}catch(SQLException sqle) {
//			System.err.println(sqle.getMessage());
//		}
//
//		assertEquals(expected.getAssociatedAccountId(), actualAccountID);
//		assertEquals(expected.getAmount(), actualAmount);
//		assertEquals(expected.getDate(), actualDate);
//		assertEquals(expected.getType(), actualType);
//	}
//
//	@AfterClass
//
//	public static void tearDown() {
//		File f = new File("transactions");
//		f.delete();
//	}
//}