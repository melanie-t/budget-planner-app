import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/*
 * WARNING:: each test depends on the previous ones
 * */
public class AccountRepositoryTest {
	private static AccountRepository accoutRepo;
	private static String TestDBName;
	private static Database testDatabase;


	@Test
	public void setUpClass() {
		TestDBName = "AccountsTest";
		testDatabase = new Database(TestDBName);
		accoutRepo = new AccountRepository(testDatabase);
		accoutRepo.reinitSQLStructure();

		assertEquals(accoutRepo.getItems().size(), 0); // not the best methods to check if db structure is intact but if no errors thown is good sign
	}

	@Test
	//Assumes tests are executed in same order as listed in this class
	public void saveItemTest() {
		String bankName = "Fort Knox";
		String nickname = "My PiggyBank";
		@SuppressWarnings("deprecation")
		Integer accBalance = new Integer(200);


		Account testAcc1 = new Account();
		testAcc1.setBalance(accBalance);
		testAcc1.setBankName(bankName);
		testAcc1.setNickname(nickname);
		accoutRepo.saveItem(testAcc1);


		//If item is fetched in a fresh repo means save to DB worked
		AccountRepository freshAccountRepo = new AccountRepository(testDatabase);
		freshAccountRepo.loadAllItems();
        Account a = freshAccountRepo.getItem(1);
        assertTrue(a.getId().equals(1));
		assertEquals(a.getBalance(), accBalance);
		assertEquals(a.getBankName(), bankName);
		assertEquals(a.getNickname(), nickname);

	}

	@Test
	//Assumes tests are executed in same order as listed in this class
	public void saveSecondItemTest() {
		String bankName1 = "Fort Knox";
		String nickname1 = "My PiggyBank";
		@SuppressWarnings("deprecation")
		Integer accBalance1 = new Integer(200);


		String bankName2 = "Sock Drawer";
		String nickname2 = "Coffe funds";
		@SuppressWarnings("deprecation")
		Integer accBalance2 = new Integer(20200);


		Account testAcc2 = new Account();
		testAcc2.setBalance(accBalance2);
		testAcc2.setBankName(bankName2);
		testAcc2.setNickname(nickname2);
		accoutRepo.saveItem(testAcc2);


		//If item is fetched in a fresh repo means save to DB worked
		AccountRepository freshAccountRepo = new AccountRepository(testDatabase);
		freshAccountRepo.loadAllItems();
		ArrayList<Account> accountsList1 = freshAccountRepo.getItems();
		assertEquals(accountsList1.size(), 2);


		//Test data from first row inserted previously
        Account a1 = accountsList1.get(0);
        assertTrue(a1.getId().equals(1));
		assertEquals(a1.getBalance(), accBalance1);
		assertEquals(a1.getBankName(), bankName1);
		assertEquals(a1.getNickname(), nickname1);


		//Test second row
        Account a2 = accountsList1.get(1);
        assertTrue(a2.getId().equals(2));
		assertEquals(a2.getBalance(), accBalance2);
		assertEquals(a2.getBankName(), bankName2);
		assertEquals(a2.getNickname(), nickname2);

	}

	@Test
	public void DeleteItemTest() {
		
		
		
		Database db = new Database("DeleteItemTest");
		accoutRepo = new AccountRepository(db);
		accoutRepo.reinitSQLStructure();
		
		
		String bankName = "Fort Knox";
		String nickname = "My PiggyBank";
		@SuppressWarnings("deprecation")
		Integer accBalance = new Integer(200);


		Account testAcc1 = new Account();
		testAcc1.setBalance(accBalance);
		testAcc1.setBankName(bankName);
		testAcc1.setNickname(nickname);
		accoutRepo.saveItem(testAcc1);
		
		
		Account testAcc2 = new Account();
		testAcc2.setBalance(accBalance);
		testAcc2.setBankName(bankName);
		testAcc2.setNickname(nickname);
		accoutRepo.saveItem(testAcc2);
		
		
		
		System.out.println("Before "+AccountRepositoryTest.accoutRepo.getItems().size());
		
		AccountRepositoryTest.accoutRepo.deleteItem(1);
		System.out.println("After "+AccountRepositoryTest.accoutRepo.getItems().size());
		System.out.println("currenlt items loaded in repo" + AccountRepositoryTest.accoutRepo.getItems().size());
		
		//check in current repo if delete worked
		assertEquals(AccountRepositoryTest.accoutRepo.getItems().size(), 1);

		//check in fresh repo to check if delete worked
		AccountRepository freshAccountRepo = new AccountRepository(db);
		freshAccountRepo.loadAllItems();
		System.out.println(freshAccountRepo.getItems().size());
		assertEquals(freshAccountRepo.getItems().size(), 1);
		
	}

}
