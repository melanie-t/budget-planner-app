import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(SQLStringFactoryTest.class, AccountRepositoryTest.class, BudgetRepositoryTest.class, ImportTransactionTest.class, TransactionRepositoryTest.class, AccountTest.class, TransactionTest.class, AccountControllerTest.class, TransactionControllerTest.class, RepositoryContainerTest.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }

      System.out.println(result.wasSuccessful());
   }
}