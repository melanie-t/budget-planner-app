import javax.swing.*;
import java.awt.event.ActionEvent;

public class BudgetController extends AbstractController<IBudgetView> {

    public BudgetController(IBudgetView view, IModelController model) {
        super(view, model);
        view.registerAddActionCallback(this, "Add");
        view.registerDeleteActionCallback(this, "Delete");
        view.registerUpdateActionCallback(this, "Update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand())
        {
            case "Add":
                handleAddOrUpdate(0);
                break;
            case "Delete":
                handleDelete();
                break;
            case "Update":
                handleAddOrUpdate(view.getSelectedBudgetId());
                break;
        }
    }


    private void handleAddOrUpdate(Integer budgetId)
    {
        boolean success = true;

        String name = view.getNameInput();
        Integer amount = view.getAmountInput();

        if (name.isEmpty() || amount == null)
        {
            String message = "Please complete the fields: ";
            if (name.isEmpty())
                message += "Bank. ";

            if (amount == null)
                message += "Balance must be a number.";

            JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);

            success = false;
        }

        if (success)
        {
            Budget budget = new Budget(
                    budgetId,
                    name,
                    amount,
                    0
            );

            model.saveItem(budget);
            view.setSelection(budget.getId());
        }
        else
            System.out.println("Add error");
    }

    private void handleDelete()
    {

    }


}
