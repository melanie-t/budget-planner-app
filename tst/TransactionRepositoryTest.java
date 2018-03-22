import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class TransactionRepositoryTest {

	private static TransactionRepository transacRepoTest;

	@BeforeClass

	public static void setUpClass() {

	    Transaction newTransaction = new Transaction();
	    newTransaction.setAssociatedAccountId(1);
	    newTransaction.setAmount(1000);
	    newTransaction.setDate("2018-03-22");
	    newTransaction.setDescription("test transaction");
	    
	    transacRepoTest = new TransactionRepository(new Database("TransactionTest"));
	    transacRepoTest.reinitSQLStructure();
	    transacRepoTest.saveItem(newTransaction);
	}

	@Test
	public void loadAllTest() {
        assertEquals(transacRepoTest.itemMap.size(), 1);

        transacRepoTest.itemMap.clear();
		assertEquals(transacRepoTest.itemMap.size(), 0);
		transacRepoTest.loadAllItems();
		
		assertEquals(transacRepoTest.itemMap.size(), 1);
	}
}
