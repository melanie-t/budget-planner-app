import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class RepositoryContainerTest {
	
	@Test 
	public void saveItemTest() {
		String TestTransDBName = "TransactionTest";
		Database testTransDatabase = new Database(TestTransDBName);
		TransactionRepository transacRepoTest = new TransactionRepository(testTransDatabase);
	    transacRepoTest.reinitSQLStructure();
	    
	    String TestAccDBName = "AccountsTest";
		Database testAccDatabase = new Database(TestAccDBName);
		AccountRepository accountRepoTest = new AccountRepository(testAccDatabase);
		accountRepoTest.reinitSQLStructure();		
		
		RepositoryContainer repoContainer = new RepositoryContainer(transacRepoTest, accountRepoTest);				
		
		//Create fake account
		String bankName = "Fort Knox";
		String nickname = "My PiggyBank";		
		Integer accBalance = 200;

		Account testAcc1 = new Account();
		testAcc1.setBalance(accBalance);
		testAcc1.setBankName(bankName);
		testAcc1.setNickname(nickname);
		repoContainer.saveItem(testAcc1);
		//Did the fake account  get added to the correct DB?
		Integer actual = accountRepoTest.getItems().size();
		Integer expected = 1;
		assertEquals(actual, expected);
				
				
		//Create fake transaction
		Integer associatedAccountId = testAcc1.getId();
		String type = "deposit";
		String date = "2018-04-03";
		Integer amount = 100;
		String description = "Test transaction";
		Transaction testTransaction1 = new Transaction();
		testTransaction1.setAssociatedAccountId(associatedAccountId);
		testTransaction1.setType(type);
		testTransaction1.setDate(date);
		testTransaction1.setAmount(amount);
		testTransaction1.setDescription(description);
		repoContainer.saveItem(testTransaction1);	
		
		//Did the fake transaction get added to the correct DB?
		actual = transacRepoTest.getItems(associatedAccountId).size();
		expected = 1;
		assertEquals(actual, expected);
	}
	
}
