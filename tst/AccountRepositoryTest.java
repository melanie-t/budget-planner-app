import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/*
 * WARNING:: each test depends on the previous ones
 * */
public class AccountRepositoryTest {
	private static AccountRepository accountRepo;
	private static String TestDBName;
	private static Database testDatabase;


	@Test
	public void setUpClass() {
		TestDBName = "AccountsTest";
		testDatabase = new Database(TestDBName);
		accountRepo = new AccountRepository(testDatabase);
		accountRepo.reinitSQLStructure();

		assertEquals(accountRepo.getItems().size(), 0); // not the best methods to check if db structure is intact but if no errors thrown is good sign
	}

	@Test
	//Assumes tests are executed in same order as listed in this class
	public void saveItemTest() {
		String bankName = "Fort Knox";
		String nickname = "My PiggyBank";		
		Integer accBalance = 200;


		Account testAcc1 = new Account();
		testAcc1.setBalance(accBalance);
		testAcc1.setBankName(bankName);
		testAcc1.setNickname(nickname);
		accountRepo.saveItem(testAcc1);


		//If item is fetched in a fresh repo means save to DB worked
		AccountRepository freshAccountRepo = new AccountRepository(testDatabase);
		freshAccountRepo.loadAllItems();
        Account a = freshAccountRepo.getItem(1);
        assertTrue(a.getId().equals(1));
		assertEquals(a.getBalance(), accBalance);
		assertEquals(a.getBankName(), bankName);
		assertEquals(a.getNickname(), nickname);
		
		//Attempt to save account with existing ID, should update item
		Integer updatedAccBalance = 10000;
		Account testUpdateAcc = freshAccountRepo.getItem(testAcc1.getId());
		testUpdateAcc.setBalance(updatedAccBalance);
		accountRepo.saveItem(testUpdateAcc);
		
		assertEquals(testUpdateAcc.getBalance(),updatedAccBalance);

	}

	@Test
	//Assumes tests are executed in same order as listed in this class
	public void saveSecondItemTest() {
		String bankName1 = "Fort Knox";
		String nickname1 = "My PiggyBank";		
		Integer accBalance1 = 10000;

		String bankName2 = "Sock Drawer";
		String nickname2 = "Coffee funds";		
		Integer accBalance2 = 20200;

		Account testAcc2 = new Account();
		testAcc2.setBalance(accBalance2);
		testAcc2.setBankName(bankName2);
		testAcc2.setNickname(nickname2);
		accountRepo.saveItem(testAcc2);


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
		accountRepo = new AccountRepository(db);
		accountRepo.reinitSQLStructure();
		
		
		String bankName = "Fort Knox";
		String nickname = "My PiggyBank";		
		Integer accBalance = 200;


		Account testAcc1 = new Account();
		testAcc1.setBalance(accBalance);
		testAcc1.setBankName(bankName);
		testAcc1.setNickname(nickname);
		accountRepo.saveItem(testAcc1);
		
		
		Account testAcc2 = new Account();
		testAcc2.setBalance(accBalance);
		testAcc2.setBankName(bankName);
		testAcc2.setNickname(nickname);
		accountRepo.saveItem(testAcc2);
		
		
		
		System.out.println("Tuples in Account repository test before delete: "+AccountRepositoryTest.accountRepo.getItems().size());
		
		AccountRepositoryTest.accountRepo.deleteItem(1);
		System.out.println("Tuples in Account repository test after delete: "+AccountRepositoryTest.accountRepo.getItems().size());
		System.out.println("Current items loaded in repo:" + AccountRepositoryTest.accountRepo.getItems().size());
		
		//check if delete worked in current repo 
		assertEquals(AccountRepositoryTest.accountRepo.getItems().size(), 1);

		//check if delete worked in fresh repo
		AccountRepository freshAccountRepo = new AccountRepository(db);
		freshAccountRepo.loadAllItems();
		System.out.println(freshAccountRepo.getItems().size());
		assertEquals(freshAccountRepo.getItems().size(), 1);
		
	}

}
