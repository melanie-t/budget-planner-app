import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class BudgetRepositoryTest {

	private static BudgetRepository budgetRepo;
	private static String TestDBName;
	private static Database testDatabase;

	
	@Test
	public void setUpClass() {	    
		TestDBName = "BudgetTest";
		testDatabase = new Database(TestDBName);
	    budgetRepo = new BudgetRepository(testDatabase);
	    budgetRepo.reinitSQLStructure();
		
	    // Does budget repo have "None" entry?
		Budget none = budgetRepo.getItem(budgetRepo.getNoneBudgetId());
		assertEquals(none.getName(), "None");
	    assertEquals(budgetRepo.getItems().size(), 1);
	}
	
	@Test
	public void saveItemTest() {
		String name = "Budget";
		Integer amount = 100;
		Integer balance = 1000;

		 // Newly created Budget starts at ID 2
		Budget expectedBudget = new Budget();
		expectedBudget.setName(name);
		expectedBudget.setBalance(balance);
		expectedBudget.setAmount(amount);
		budgetRepo.saveItem(expectedBudget);
		
		//If item is fetched in a fresh repo means save to DB worked
		BudgetRepository freshBudgetRepo = new BudgetRepository(testDatabase);
		freshBudgetRepo.loadAllItems();
		Budget returnedBudget = freshBudgetRepo.getItem(2);
        assertTrue(returnedBudget.getId().equals(expectedBudget.getId()));
        assertEquals(returnedBudget.getName(), expectedBudget.getName());
        assertEquals(returnedBudget.getAmount(), expectedBudget.getAmount());
        assertEquals(returnedBudget.getBalance(), expectedBudget.getBalance());
        
        //Test "Update" mode of saveItem
		//Attempt to save Budget with existing ID, should update item
		Integer updatedBudgetAmount = 10000;
		Budget updatedBudget = freshBudgetRepo.getItem(expectedBudget.getId());
		updatedBudget.setAmount(updatedBudgetAmount);
		budgetRepo.saveItem(updatedBudget);
		
		assertEquals(updatedBudget.getAmount(),updatedBudgetAmount);
	}
	
	@Test
	public void DeleteItemTest() {		
		
		Database db = new Database("DeleteItemTest");
		budgetRepo = new BudgetRepository(db);
		budgetRepo.reinitSQLStructure();
		
		String name = "Test Budget";
		Integer amount = 1000;
		Integer balance = 100;

		Budget testBudget1 = new Budget();
		testBudget1.setName(name);
		testBudget1.setAmount(amount);
		testBudget1.setBalance(balance);
		budgetRepo.saveItem(testBudget1);
		
		Budget testBudget2 = new Budget();
		testBudget1.setName(name);
		testBudget1.setAmount(amount);
		testBudget1.setBalance(balance);
		budgetRepo.saveItem(testBudget2);
		
		int sizeBeforeDelete = BudgetRepositoryTest.budgetRepo.getItems().size();
		System.out.println("Tuples in Budget repository test before delete: "+ sizeBeforeDelete);
		
		BudgetRepositoryTest.budgetRepo.deleteItem(2);
		int sizeAfterDelete = BudgetRepositoryTest.budgetRepo.getItems().size();
		
		System.out.println("Tuples in Budget repository test after delete: "+ sizeAfterDelete);
		System.out.println("Current items loaded in repo: " + BudgetRepositoryTest.budgetRepo.getItems().size());
		
		//check if delete worked in current repo 
		assertEquals(BudgetRepositoryTest.budgetRepo.getItems().size(), sizeBeforeDelete-1);

		//check if delete worked in fresh repo
		BudgetRepository freshBudgetRepo = new BudgetRepository(db);
		freshBudgetRepo.loadAllItems();
		System.out.println(freshBudgetRepo.getItems().size());
		assertEquals(freshBudgetRepo.getItems().size(), sizeBeforeDelete-1);
		
	}
}

