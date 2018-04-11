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
                message += "Name. ";

            if (amount == null)
                message += "Amount must be a number.";

            showMessageDialog("Input Error", message);

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
        model.deleteBudget(view.getSelectedBudgetId());
        view.setSelection(0);
    }

    protected void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

}
