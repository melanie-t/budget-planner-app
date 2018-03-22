import org.junit.Before;
import org.junit.Test;
import java.awt.event.ActionEvent;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.BeforeClass;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;


/*
 * Tests whether the controller passes the correct arguments
 * to the model
 */
public class TransactionControllerTest {

    private static TransactionController controller;
    private static IModelController modelMock;
    private static ITransactionView viewMock;
    private static Transaction transaction;

    @BeforeClass
    public static void setUpClass() {
        int transactionId = (int) (Math.random() * 10000000);;
        int accountId = (int) (Math.random() * 10000000);;
        String type = "testType";
        String date = "01-01-1970";
        int amount = (int) (Math.random() * 10000000);;
        String description = "THIS IS A TEST TRANSACTION";

        transaction = new Transaction(transactionId, accountId, type, date, amount, description);

        viewMock = mock(ITransactionView.class);
        when(viewMock.getTypeInput()).thenReturn(type);
        when(viewMock.getDateInput()).thenReturn(date);
        when(viewMock.getAmountInput()).thenReturn(amount);
        when(viewMock.getDescriptionInput()).thenReturn(description);
        when(viewMock.getSelectedAccountId()).thenReturn(accountId);

        when(viewMock.getSelectedTransactionId()).thenReturn(transaction.getId());
    }

    @Before
    public void resetModelMock() {
        modelMock = mock(IModelController.class);
        controller = new TransactionController(viewMock, modelMock);
    }

    @Test
    public void testAddOrUpdateTransaction() {
        ActionEvent e = mock(ActionEvent.class);
        when(e.getActionCommand()).thenReturn("Update");
        controller.actionPerformed(e);

        ArgumentCaptor<Transaction> argument = ArgumentCaptor.forClass(Transaction.class);
        verify(modelMock).saveItem(argument.capture());
        assertTrue("Saved transaction does not match inputs",
                new ReflectionEquals(transaction).matches(argument.getValue()));
    }
    @Test
    public void testDeleteTransaction() {
        ActionEvent e = mock(ActionEvent.class);
        when(e.getActionCommand()).thenReturn("Delete");
        controller.actionPerformed(e);

        verify(modelMock).deleteTransaction(transaction.getId());
    }
}
