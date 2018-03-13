import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import static org.junit.Assert.*;


public class ImportTransactionTest {
	
	String transactionFilePath = "tst/spread_sheet_test_case.csv";
	private AccountTransactionRepository accountTransactionRepository;
	
	/* Link the account's AccountTransactionRepository before calling addTransaction() */
	public void setAccountTransactionRepository(AccountTransactionRepository accTransacRepo) {
		accountTransactionRepository = accTransacRepo;
	}
	
	/* Function to import transactions */
	public void addTransaction(String transactionFilePath) {
		String [] tokenList = null;
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(transactionFilePath));
			String line = null;
			
			while((line = br.readLine()) != null) {
				/*
				 * tokenList maps the tokens as
				 * tokenList[0]: accountID
				 * tokenList[1]: type
				 * tokenList[2]: date
				 * tokenList[3]: amount
				 */
				tokenList = line.split(",");
				
				Integer accountID = Integer.parseInt(tokenList[0]);
				Float amount = Float.parseFloat(tokenList[3]);
				
				TransactionModel transacMod = new TransactionModel();
				transacMod.setAccountId(accountID);
				transacMod.setType(tokenList[1]);
				transacMod.setDate(tokenList[2]);
				transacMod.setAmount(amount);
				
				accountTransactionRepository.initSQLStructure();
				accountTransactionRepository.saveItem(transacMod);
				
			}
			br.close();
			
		}catch(FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		}catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
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
		
		ImportTransactionTest transaction = new ImportTransactionTest();
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