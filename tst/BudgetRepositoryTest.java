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
	    assertEquals(budgetRepo.getItems().size(), 1);	//Initial "None" budget inserted into repo
	}
	
	@Test
	public void saveItemTest() {
		String name = "Budget";
		Integer amount = 100;
		Integer balance = 1000;

		Budget testBudget1 = new Budget(); // Newly created Budget has ID 2
		testBudget1.setName(name);
		testBudget1.setBalance(balance);
		testBudget1.setAmount(amount);
		budgetRepo.saveItem(testBudget1);

		Budget t = budgetRepo.getItem(2);
        assertTrue(t.getId().equals(2));
        assertEquals(t.getName(), name);
        assertEquals(t.getAmount(), amount);
        assertEquals(t.getBalance(), balance);
        
        //Test "Update" mode of saveItem
		//Attempt to save Budget with existing ID, should update item
		Integer updatedBudgetAmount = 10000;
		Budget testBudget2 = budgetRepo.getItem(testBudget1.getId());
		testBudget2.setAmount(updatedBudgetAmount);
		
		budgetRepo.saveItem(testBudget2);
		assertEquals(testBudget2.getAmount(),updatedBudgetAmount);
		budgetRepo.deleteItem(testBudget1.getId());
	}

	// Methods aren't written to delete budgets yet
	/*
	@Test
	public void deleteItemTest() {		
		
		//add a transaction to repo
		String name_1 = "Test_Budget";
		Integer amount_1 = 100;
		Integer balance_1 = 1000;
	
		Budget testBudgetDelete1 = new Budget();
		testBudgetDelete1.setName(name_1);
		testBudgetDelete1.setAmount(amount_1);
		testBudgetDelete1.setBalance(balance_1);
		budgetRepo.saveItem(testBudgetDelete1);		
		
		//should now be 2 budgets ("None" and "Test_Budget") in the repo		
		ArrayList<Budget> budgetList = budgetRepo.getItemsFromAccount(associatedAccountId_1);
		assertEquals(budgetList.size(), 1);
			
		budgetRepo.deleteItem(associatedAccountId_1);

		//should now be 1 budget ("None") in the repo
		budgetList = budgetRepo.getItemsFromAccount(associatedAccountId_1);
		assertEquals(budgetList.size(), 0);
		
	}

	@Test
	public void deleteAllItemsFromAccountTest() {		
		
		//add two transactions to repo
		Integer associatedAccountId_1 = 1;
		String type_1 = "deposit";
		String date_1 = "2018-04-03";
		Integer amount_1 = 100;
		String description_1 = "This is test transaction number one";
		Transaction testDeleteTransaction_1 = new Transaction();
		testDeleteTransaction_1.setAssociatedAccountId(associatedAccountId_1);
		testDeleteTransaction_1.setName(type_1);
		testDeleteTransaction_1.setDate(date_1);
		testDeleteTransaction_1.setAmount(amount_1);
		testDeleteTransaction_1.setDescription(description_1);
		budgetRepo.saveItem(testDeleteTransaction_1);
		
		Integer associatedAccountId_2 = 1;
		String type_2 = "deposit";
		String date_2 = "2018-04-05";
		Integer amount_2 = 560;
		String description_2 = "This is test transaction number two";
		Transaction testDeleteTransaction_2 = new Transaction();
		testDeleteTransaction_2.setAssociatedAccountId(associatedAccountId_2);
		testDeleteTransaction_2.setName(type_2);
		testDeleteTransaction_2.setDate(date_2);
		testDeleteTransaction_2.setAmount(amount_2);
		testDeleteTransaction_2.setDescription(description_2);
		budgetRepo.saveItem(testDeleteTransaction_2);
		
		//should now be 2 transactions in the repo		
		ArrayList<Transaction> transList = budgetRepo.getItemsFromAccount(associatedAccountId_2);
		assertEquals(transList.size(), 2);
			
		budgetRepo.deleteAllItemsFromAccount(associatedAccountId_2);


		//should now be 0 transactions in the repo
		transList = budgetRepo.getItemsFromAccount(associatedAccountId_2);
		assertEquals(transList.size(), 0);
		
	}
	*/
}

