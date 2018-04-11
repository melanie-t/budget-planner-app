import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.awt.event.ActionEvent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BudgetControllerTest {

    private static BudgetController controller;
    private static IModelController modelMock;
    private static IBudgetView viewMock;
    private static Budget budget;

    @BeforeClass
    public static void setUpClass() {
        int budgetId = (int) (Math.random() * Integer.MAX_VALUE);
        String budgetName = "testBank";
        int amount = (int) (Math.random() * Integer.MAX_VALUE);

        budget = new Budget(budgetId, budgetName, amount, 0);       //new budgets will have balance 0

        viewMock = mock(IBudgetView.class);
        when(viewMock.getSelectedBudgetId()).thenReturn(budgetId);
        when(viewMock.getNameInput()).thenReturn(budgetName);
        when(viewMock.getAmountInput()).thenReturn(amount);
    }

    @Before
    public void resetModelMock() {
        modelMock = mock(IModelController.class);
        controller = new BudgetController(viewMock, modelMock);
    }

    @Test
    public void testAddOrUpdateBudget() {
        ActionEvent e = mock(ActionEvent.class);
        when(e.getActionCommand()).thenReturn("Update");
        controller.actionPerformed(e);

        ArgumentCaptor<Budget> argument = ArgumentCaptor.forClass(Budget.class);
        verify(modelMock).saveItem(argument.capture());
        assertTrue("Saved budget does not match inputs",
                   new ReflectionEquals(budget).matches(argument.getValue()));
    }
    @Test
    public void testDeleteAccount() {
        ActionEvent e = mock(ActionEvent.class);
        when(e.getActionCommand()).thenReturn("Delete");
        controller.actionPerformed(e);

        verify(modelMock).deleteBudget(budget.getId());
    }
}
