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
 * Test Suite for input validation of BudgetController
 * handleAddOrUpdate()
 */

public class BudgetControllerInputTest {

    private static IModelController modelMock;
    private static Budget testBudget;

    private static String ERROR_TITLE = "Input Error";
    private static String BASE_ERROR_MESSAGE = "Please complete the fields: ";
    private static String EMPTY_NAME_MESSAGE = "Name. ";
    private static String AMOUNT_IS_NULL_MESSAGE = "Amount must be a number.";

    @BeforeClass
    public static void setupTestBudget() {
        int budgetId = (int) (Math.random() * Integer.MAX_VALUE);
        String budgetName = "testBudget";
        int amount = (int) (Math.random() * Integer.MAX_VALUE);

        testBudget = new Budget(budgetId, budgetName, amount, 0);       //new budgets will have balance 0
    }

    @Test
    public void testAddAccountEmptyName() {
        //build a mocked view with the test conditions
        IBudgetView viewMock = buildViewMock(testBudget.getId(),
                                              "",
                                              testBudget.getAmount());

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + EMPTY_NAME_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }

    @Test
    public void testAddAccountAmountIsNull() {
        //build a mocked view with the test conditions
        IBudgetView viewMock = buildViewMock(testBudget.getId(),
                                             testBudget.getName(),
                                             null);

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + AMOUNT_IS_NULL_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }

    @Test
    public void testAddAccountAllWrong() {
        //build a mocked view with the test conditions
        //build a mocked view with the test conditions
        IBudgetView viewMock = buildViewMock(testBudget.getId(),
                                             "",
                                             null);

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + EMPTY_NAME_MESSAGE + AMOUNT_IS_NULL_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }



    //run test given a mocked view and an error title and error message
    //no accounts should be saved (all invalid cases)
    private void runTestWithInputs(IBudgetView viewMock, String expectedTitle, String expectedMessage) {
        IModelController modelMock = mock(IModelController.class);

        //spy on the controller
        BudgetController spyController = spy(new BudgetController(viewMock, modelMock));

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

        //verify no budgets were saved
        verify(modelMock, never()).saveItem((Budget) any());
    }

    /*
     * Builds a mocked view containing the inputs
     */
    private IBudgetView buildViewMock(int budgetId, String name, Integer amount) {
        IBudgetView viewMock = mock(IBudgetView.class);

        when(viewMock.getSelectedBudgetId()).thenReturn(budgetId);
        when(viewMock.getNameInput()).thenReturn(name);
        when(viewMock.getAmountInput()).thenReturn(amount);

        return viewMock;
    }
}