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

	    Transaction newTransaction = new Transaction(1, 1, "Type", "Date", 1000, "Description");
	    transacRepoTest = new TransactionRepository(new Database("TransactionTest"));
	    transacRepoTest.reinitSQLStructure();
	    transacRepoTest.saveItem(newTransaction);
	}

	@Test
	public void loadAllTest() {
        assertEquals(transacRepoTest.itemMap.size(), 1);
		transacRepoTest.itemMap.clear(); //Clear ItemMap to test loadItem() and loadAll() successively.
        assertEquals(transacRepoTest.itemMap.size(), 0);
        transacRepoTest.loadAllItems();
		assertEquals(transacRepoTest.itemMap.size(), 1);
	}
}
