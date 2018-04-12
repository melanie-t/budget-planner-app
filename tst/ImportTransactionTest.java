
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.*;
import java.io.File;
import java.util.List;

public class ImportTransactionTest {

	private static RepositoryContainer model;
	private static Database testDatabase;
	private static Account testAcc;
	private static Transaction expected;
	private static Integer targetAccountId;
	private static Integer targetBudgetId;

	@BeforeClass
	public static void setUpClass() {

		testDatabase = new Database("transactions");

		model = new RepositoryContainer(new TransactionRepository(testDatabase), new AccountRepository(testDatabase), new BudgetRepository((testDatabase)));
		model.resetSQLStructure();

		/* Test Account */
		testAcc = new Account();
		testAcc.setId(0);
		testAcc.setBalance(0);
		testAcc.setBankName("Fort Knox");
		testAcc.setNickname("Oddjob");

		model.saveItem(testAcc);

		List<Account> accounts = model.getAllAccounts();
		targetAccountId = accounts.get(0).getId();

		targetBudgetId = model.getNoneBudgetId();

		/* Expected transaction tuple */
		String type = "Deposit";
		String date = "09-09-1999";
		Integer amount = 100;
		expected = new Transaction();
		expected.setAssociatedAccountId(targetAccountId);
		expected.setType(type);
		expected.setDate(date);
		expected.setAmount(amount);
		expected.setAssociatedBudgetId(targetBudgetId);
	}


	@Test
	public void testAddTransaction() {

        model.importTransactions("tst/spread_sheet_test_case.csv", targetAccountId);
		/*
		 * Query transactions database to fetch accountID, type, date and amount.
		 * The values obtained are stored in actualATTRIBUTE where ATTRIBUTE is either accountID, type, date or amount.
		 **/
		String dbAmount = "select amount from transactions where accountId = " + targetAccountId + ";";
		String dbDate = "select date from transactions where accountId = " + targetAccountId + ";";
		String dbType = "select type from transactions where accountId = " + targetAccountId + ";";

		Integer actualAmount = null;
		String actualType = null;
		String actualDate = null;

		try {
			actualAmount = testDatabase.fetchSQL(dbAmount).getInt("amount");
			actualType = testDatabase.fetchSQL(dbType).getString("type");
			actualDate = testDatabase.fetchSQL(dbDate).getString("date");

		}catch(SQLException sqle) {
			System.err.println(sqle.getMessage());
		}

		
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