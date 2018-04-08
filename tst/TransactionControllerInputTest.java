import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import javax.imageio.ImageTranscoder;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

/*
 * Test Suite for input validation of TransactionController
 * handleAddOrUpdate()
 */

public class TransactionControllerInputTest {

    private static IModelController modelMock;
    private static Transaction transaction;

    private static String ERROR_TITLE = "Input Error";
    private static String EMPTY_TYPE_MESSAGE = "Please complete the fields: Type. ";
    private static String EMPTY_DATE_MESSAGE = "Please complete the fields: Date. ";
    private static String EMPTY_TYPE_AND_DATE_MESSAGE = "Please complete the fields: Type. Date. ";
    private static String INVALID_TYPE_MESSAGE = "Invalid Inputs: Type. ";
    private static String INVALID_DATE_MESSAGE = "Invalid Inputs: Date. ";
    private static String INVALID_TYPE_AND_DATE_MESSAGE = "Invalid Inputs: Type. Date. ";

    @BeforeClass
    public static void setupTestTransaction() {
        int transactionId = (int) (Math.random() * 10000000);
        int accountId = (int) (Math.random() * 10000000);
        int budgetId = 0;
        String type = "Deposit";
        String date = "01-01-1970";
        int amount = (int) (Math.random() * 10000000);
        String description = "THIS IS A TEST TRANSACTION";

        transaction = new Transaction(transactionId, accountId, budgetId, type, date, amount, description);
    }

    @Test
    public void testAddTransactionEmptyType() {
        //build a mocked view with the test conditions
        ITransactionView viewMock = buildViewMock("",
                                                  transaction.getDate(),
                                                  transaction.getAmount(),
                                                  transaction.getDescription(),
                                                  transaction.getAssociatedAccountId());
        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, EMPTY_TYPE_MESSAGE);
    }

    @Test
    public void testAddTransactionEmptyDate() {
        //build a view mock with the test conditions
        ITransactionView viewMock = buildViewMock(transaction.getType(),
                                                  "",
                                                  transaction.getAmount(),
                                                  transaction.getDescription(),
                                                  transaction.getAssociatedAccountId());

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, EMPTY_DATE_MESSAGE);
    }

    @Test
    public void testAddTransactionEmptyTypeAndDate() {
        //build a view mock with the test conditions
        ITransactionView viewMock = buildViewMock("",
                                                  "",
                                                  transaction.getAmount(),
                                                  transaction.getDescription(),
                                                  transaction.getAssociatedAccountId());

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, EMPTY_TYPE_AND_DATE_MESSAGE);
    }

    @Test
    public void testUpdateTransactionInvalidType() {
        //build a view mock with the test conditions
        String invalidType = "Someinvalidtype";
        ITransactionView viewMock = buildViewMock(invalidType,
                                                  transaction.getDate(),
                                                  transaction.getAmount(),
                                                  transaction.getDescription(),
                                                  transaction.getAssociatedAccountId());

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, INVALID_TYPE_MESSAGE);
    }

    @Test
    public void testUpdateTransactionInvalidDate() {
        //build a view mock with the test conditions
        String invalidDate = "ab-re-2343";
        ITransactionView viewMock = buildViewMock(transaction.getType(),
                                                  invalidDate,
                                                  transaction.getAmount(),
                                                  transaction.getDescription(),
                                                  transaction.getAssociatedAccountId());

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, INVALID_DATE_MESSAGE);
    }

    @Test
    public void testUpdateTransactionInvalidTypeAndDate() {
        //build a view mock with the test conditions
        String invalidType = "Someinvalidtype";
        String invalidDate = "ab-re-2343";
        ITransactionView viewMock = buildViewMock(invalidType,
                                                  invalidDate,
                                                  transaction.getAmount(),
                                                  transaction.getDescription(),
                                                  transaction.getAssociatedAccountId());

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, INVALID_TYPE_AND_DATE_MESSAGE);
    }

    private void runTestWithInputs(ITransactionView viewInputs, String expectedTitle, String expectedMessage) {
        IModelController modelMock = mock(IModelController.class);

        //spy on the controller
        TransactionController spyController = spy(new TransactionController(viewInputs, modelMock));

        //Setup argument captors for error message dialog
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        //stub out the error dialog box method
        doNothing().when(spyController).showMessageDialog(titleCaptor.capture(), messageCaptor.capture());

        //mock an update action event
        ActionEvent e = mock(ActionEvent.class);
        when(e.getActionCommand()).thenReturn("Update");

        //call the method to test
        spyController.actionPerformed(e);

        //assert the right error title and message were produced
        assertEquals("Title", expectedTitle, titleCaptor.getValue());
        assertEquals("Message", expectedMessage, messageCaptor.getValue());

        //verify no transactions were saved
        verify(modelMock, never()).saveItem((Transaction) any());
    }

    /*
     * Builds a mocked view containing the inputs
     */
    private ITransactionView buildViewMock(String type, String date, int amount, String description, int accountId) {
        ITransactionView viewMock = mock(ITransactionView.class);

        when(viewMock.getTypeInput()).thenReturn(type);
        when(viewMock.getDateInput()).thenReturn(date);
        when(viewMock.getAmountInput()).thenReturn(amount);
        when(viewMock.getDescriptionInput()).thenReturn(description);
        when(viewMock.getSelectedAccountId()).thenReturn(accountId);
        when(viewMock.getSelectedTransactionId()).thenReturn(transaction.getId());

        return viewMock;
    }
}