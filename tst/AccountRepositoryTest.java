//import org.junit.Test;
//import java.io.File;
//import static org.junit.Assert.*;
//import org.junit.BeforeClass;
//import org.junit.AfterClass;
///*
// * WARNING:: each test depends on the previous ones
// * */
//public class AccountRepositoryTest {
//	private AccountRepository accoutRepo;
//	private String TestDBName;
//	private Database testDatabase;
//
//
//	@Test
//	public void setupRepoTest() {
//		TestDBName = "AccountsTest";
//		testDatabase = new Database(TestDBName);
//		accoutRepo = new AccountRepository(testDatabase);
//		accoutRepo.reinitSQLStructure();
//
//		assertEquals(accoutRepo.getListOfAllItems().size(), 0); // not the best methods to check if db structure is intact but if no errors thown is good sign
//	}
//
//	@Test
//	//Assumes tests are executed in same order as listed in this class
//	public void saveItemTest() {
//		String bankName = "Fort Knox";
//		String nickname = "My PiggyBank";
//		@SuppressWarnings("deprecation")
//		Integer accBalance = new Integer(200);
//
//
//		AccountModel testAcc1 = new AccountModel();
//		testAcc1.setBalance(accBalance);
//		testAcc1.setBankName(bankName);
//		testAcc1.setNickname(nickname);
//		accoutRepo.saveItem(testAcc1);
//
//
//		//If item is fetched in a fresh repo means save to DB worked
//		AccountRepository freshAccountRepo = new AccountRepository(testDatabase);
//		AccountModel a = freshAccountRepo.getItem(1);
//		assertEquals(a.getId(), 1);
//		assertEquals(a.getBalance(), accBalance);
//		assertEquals(a.getBankName(), bankName);
//		assertEquals(a.getNickname(), nickname);
//
//	}
//
//	@Test
//	//Assumes tests are executed in same order as listed in this class
//	public void saveSecondItemTest() {
//		String bankName1 = "Fort Knox";
//		String nickname1 = "My PiggyBank";
//		@SuppressWarnings("deprecation")
//		Integer accBalance1 = new Integer(200);
//
//
//		String bankName2 = "Sock Drawer";
//		String nickname2 = "Coffe funds";
//		@SuppressWarnings("deprecation")
//		Integer accBalance2 = new Integer(20200);
//
//
//		AccountModel testAcc2 = new AccountModel();
//		testAcc2.setBalance(accBalance2);
//		testAcc2.setBankName(bankName2);
//		testAcc2.setNickname(nickname2);
//		accoutRepo.saveItem(testAcc2);
//
//
//		//If item is fetched in a fresh repo means save to DB worked
//		AccountRepository freshAccountRepo = new AccountRepository(testDatabase);
//		AccountList accountsList1 = freshAccountRepo.getListOfAllItems();
//		assertEquals(accountsList1.size(), 1);
//
//
//		//Test data from first row inserted previously
//		AccountModel a1 = accountsList1.get(0);
//		assertEquals(a1.getId(), 1);
//		assertEquals(a1.getBalance(), accBalance1);
//		assertEquals(a1.getBankName(), bankName1);
//		assertEquals(a1.getNickname(), nickname1);
//
//
//		//Test second row
//		AccountModel a2 = accountsList1.get(1);
//		assertEquals(a2.getId(), 2);
//		assertEquals(a2.getBalance(), accBalance2);
//		assertEquals(a2.getBankName(), bankName2);
//		assertEquals(a2.getNickname(), nickname2);
//
//	}
//
//	@Test
//	public void DeleteItemTest() {
//		accoutRepo.deleteTransaction(1);
//
//		//check in current repo if delete worked
//		assertEquals(accoutRepo.getListOfAllItems().size(), 1);
//
//		//check in fresh repo to check if delete worked
//		AccountRepository freshAccountRepo = new AccountRepository(testDatabase);
//		assertEquals(freshAccountRepo.getListOfAllItems().size(), 1);
//	}
//
//}
