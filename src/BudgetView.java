import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class BudgetView extends AbstractView<Budget> implements IBudgetView, IViewGUI{
	
	//Budget UI elements	
	private JPanel mainPanel = new JPanel();
	private JPanel panel;
	private DefaultTableModel budgetModel;
	private JLabel budgetCategoryLabel;
	private JLabel budgetLabel;
	private JLabel nameLabel;
	private JLabel amountLabel;
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton clearButton;
	private JTextField nameTextfield;
	private JTextField amountTextfield;
	private JTable budgetTable;
	private JScrollPane budgetScrollPane;
	
	private JPanel secondPanel;
	private DefaultTableModel transactionModel;
	private JTable transactionTable;
	private JScrollPane transactionScrollPane;
	private JLabel viewLabel;
	private JLabel transactionLabel;
	private JComboBox monthField;
	private JComboBox yearField;
	private JLabel balanceLabel;

	public BudgetView(IModelView model) {

		super(model);
		items = new ArrayList<>();
		model.attachObserver(this);
		createBudgetPanel();
		createTransactionPanel();
        setLayout();

		// The clear button is handled directly by the view, no need for the controller here
		clearButton.addActionListener(e->handleClear());
	}
  
	public JPanel getPanel()		{return mainPanel;}
	public String getNameInput()	{return nameTextfield.getText();}
	public Integer getAmountInput()
	{
		String amount = amountTextfield.getText();
		if (!amount.matches("\\d+") || amount.isEmpty())
			return null;
		else
			return Integer.parseInt(amount);
	}
	
	private void createBudgetPanel() {
		panel = new JPanel();
		budgetLabel = new JLabel("Budgets");
		nameLabel = new JLabel("Name");
		amountLabel = new JLabel("Amount (cents)");
		addButton = new JButton("Add");
		updateButton = new JButton("Update");
		deleteButton = new JButton("Delete");
		clearButton = new JButton("Clear");
		nameTextfield = new JTextField(15);
		amountTextfield = new JTextField(15);
		budgetTable = new JTable();
		budgetModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		budgetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		budgetScrollPane = new JScrollPane(budgetTable);
		
		// Loading JTable
		Object[] columns = {"Name", "Budget Amount"};
		budgetModel.setColumnIdentifiers(columns);
		budgetTable.setModel(budgetModel);
		budgetTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		budgetTable.setFillsViewportHeight(true);
		
		// Set mouse click to update values in field with currently selected row
		budgetTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// i = the index of the selected row
				int selectedRow = budgetTable.getSelectedRow();
				if (selectedRow >= 0) {
                    Budget selectedItem = items.get(selectedRow);
                    setCurrentBudgetSelection(selectedItem.getId());
                    update();
				}
			}
		});
	}
	
	private void createTransactionPanel() {
		secondPanel = new JPanel();
		viewLabel = new JLabel("View in");
		balanceLabel = new JLabel("Balance : ");
		transactionLabel = new JLabel("Budget Transactions");
		transactionTable = new JTable();
		monthField = new JComboBox(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
		yearField = new JComboBox(new String[]{"2018", "2017"});
		transactionModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transactionScrollPane = new JScrollPane(transactionTable);
		
		// Loading JTable
		Object[] columns = {"Account", "Date", "Amount (cents)", "Description"};
		transactionModel.setColumnIdentifiers(columns);
		transactionTable.setModel(transactionModel);
		transactionTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		transactionTable.setFillsViewportHeight(true);
		transactionTable.setFocusable(false);

		monthField.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					handleDateSelectionChange();
			}
		});
		yearField.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					handleDateSelectionChange();
			}
		});
	}

	private void handleDateSelectionChange()
	{
		if (getCurrentBudgetSelection() == model.getNoneBudgetId())
		{
			fillTransactionTable(new ArrayList<>());
			balanceLabel.setText("Balance : ");
			return;
		}
		else
		{
			ArrayList<Transaction> transactions = model.getTransactionsFromBudget(getSelectedBudgetId());
			ArrayList<Transaction> toDisplay = new ArrayList<>();
			int balanceCount = 0;

			for (Transaction transaction : transactions)
			{
				if (isWithinSelectedDate(transaction.getDate()))
				{
					toDisplay.add(transaction);
					balanceCount += transaction.getAmount();
				}
			}

			fillTransactionTable(toDisplay);
			balanceLabel.setText("Balance : " + (findItem(getSelectedBudgetId()).getAmount() - balanceCount));
		}

	}

	private void fillTransactionTable(ArrayList<Transaction> list)
	{
		transactionModel.setRowCount(0);

		// Fetch transactions associated with account and display
		if (!list.isEmpty()) {
			//add rows to table
			for (Transaction item : list)
			{
				String accountName = model.getAccountName(item.getAssociatedAccountId());

				transactionModel.addRow(new Object[] {accountName, item.getDate(), item.getAmount(), item.getDescription()});
			}
		}
	}

	private boolean isWithinSelectedDate(String date)
	{
		String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		String[] parts = date.split("-");

		String selYear = yearField.getSelectedItem().toString();
		String selMonth = monthField.getSelectedItem().toString();

		if (parts[0].equals( selYear ) &&
			months[Integer.parseInt(parts[1])-1].equals( selMonth ))
		{
			return true;
		}

		return false;

	}


	private void setLayout() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        secondPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(panel);
        mainPanel.add(secondPanel);
        
		layoutBudgetPanel();
		layoutTransactionPanel();
	}
	
	private void layoutBudgetPanel() {
		
		// Budget Layout
		GroupLayout budgetLayout = new GroupLayout(panel);
		panel.setLayout(budgetLayout);
		budgetLayout.setAutoCreateGaps(true);
		budgetLayout.setAutoCreateContainerGaps(true);
		
		budgetLayout.setHorizontalGroup(budgetLayout.createSequentialGroup()
				.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(budgetLabel)
						.addGroup(budgetLayout.createSequentialGroup()
								.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(nameLabel)
									.addComponent(amountLabel))
								.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)		
									.addComponent(nameTextfield, 200, 200, 250)
									.addComponent(amountTextfield, 200, 200, 250)))
						.addGroup(budgetLayout.createSequentialGroup()
								.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(addButton))
								.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)									
									.addComponent(updateButton))
                                .addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(deleteButton)).
                                addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(clearButton))

								))
				.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(budgetScrollPane, 650, 650, 700))
		);
		
		budgetLayout.setVerticalGroup(budgetLayout.createSequentialGroup()
				.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(budgetLabel))
				.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(budgetScrollPane, 50, 80, 105)
				.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)	
						.addGroup(budgetLayout.createSequentialGroup()
								.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(nameLabel)
									.addComponent(nameTextfield))
								.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(amountLabel)
									.addComponent(amountTextfield))
								.addGroup(budgetLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(addButton)
									.addComponent(clearButton)
									.addComponent(updateButton)
									.addComponent(deleteButton)	
									))))
		);
		
		budgetLayout.linkSize(SwingConstants.HORIZONTAL, nameLabel, amountLabel);
		budgetLayout.linkSize(SwingConstants.HORIZONTAL, addButton, updateButton, deleteButton, clearButton);	
	}

	private void layoutTransactionPanel() {
		// Transaction Layout
		GroupLayout transactionLayout = new GroupLayout(secondPanel);
		secondPanel.setLayout(transactionLayout);
		transactionLayout.setAutoCreateGaps(true);
		transactionLayout.setAutoCreateContainerGaps(true);
		
		transactionLayout.setHorizontalGroup(transactionLayout.createSequentialGroup()		
				.addGroup(transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(transactionLabel)
						.addGroup(transactionLayout.createSequentialGroup()
							.addComponent(viewLabel)
							.addComponent(monthField)
							.addComponent(yearField)
							.addComponent(balanceLabel))
						.addComponent(transactionScrollPane, 1000, 1000, 1000))	
		);
		
		transactionLayout.setVerticalGroup(transactionLayout.createSequentialGroup()
				.addGroup(transactionLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(transactionLabel))
				.addGroup(transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(viewLabel)
								.addComponent(monthField, 20, 20, 20)
								.addComponent(yearField, 20, 20, 20)
								.addComponent(balanceLabel))
				.addGroup(transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(transactionScrollPane, 50, 80, 105))
		);
		
		transactionLayout.linkSize(SwingConstants.HORIZONTAL, yearField, monthField);
	}
	
    @Override
    protected void handleAccountSelectionChange() {
	    // do nothing
        return;
    }

    @Override
    protected void handleTransactionSelectionChange() {
        // do nothing
	    return;
    }

    @Override
    protected void handleBudgetSelectionChange() {
		Budget currentSelection = findItem(getCurrentBudgetSelection());
		fillFields(currentSelection);
		highlightCurrentSelection();
    }

    @Override
    protected void highlightCurrentSelection() {
        if (getCurrentBudgetSelection() == model.getNoneBudgetId())
        {
            fillFields(null);
            return;
        }
        Budget item;
        for (int i = 0; i < items.size(); i++)
        {
            item = items.get(i);
            if (item.getId().equals(getCurrentBudgetSelection()))
            {
                budgetTable.setRowSelectionInterval(i, i);
                fillFields(item);
                return;
            }
        }
    }

    @Override
    protected void handleClear() {
        setCurrentBudgetSelection(model.getNoneBudgetId());
        update();
    }

    @Override
    protected void fillFields(Budget item) {
        if (item == null)
        {
            nameTextfield.setText("");
            amountTextfield.setText("");
        }
        else
        {
            nameTextfield.setText(item.getName());
            amountTextfield.setText(item.getAmount().toString());
        }
    }

    @Override
    protected void fillTable() {
        // Clear table
        budgetModel.setRowCount(0);

        // Fetch transactions associated with account and display
        boolean validSelectionFound = false;
        
        System.out.println("======="+items.size());
        
        if (!items.isEmpty()) {
            //add rows to table
            for (Budget item : items)
            {
                if (item.getId() == model.getNoneBudgetId()) // This is the 'none' budget
                    continue;

                budgetModel.addRow(new Object[] {item.getName(), item.getAmount()});
                if (!validSelectionFound && item.getId().equals(getCurrentBudgetSelection()))
                {
                    validSelectionFound = true;
                }
            }
        }
        if (!validSelectionFound)
            setCurrentBudgetSelection(model.getNoneBudgetId());
    }

    @Override
    public void registerAddActionCallback(ActionListener listener, String actionCommand) {
        addButton.setActionCommand(actionCommand);
        addButton.addActionListener(listener);
    }

    @Override
    public void registerUpdateActionCallback(ActionListener listener, String actionCommand) {
        updateButton.setActionCommand(actionCommand);
        updateButton.addActionListener(listener);
    }

    @Override
    public void registerDeleteActionCallback(ActionListener listener, String actionCommand) {
        deleteButton.setActionCommand(actionCommand);
        deleteButton.addActionListener(listener);
    }

    @Override
    public Integer getSelectedBudgetId() {
        return getCurrentBudgetSelection();
    }

    @Override
    public void setSelection(Integer id) {
		if (id == 0)
			setCurrentBudgetSelection(model.getNoneBudgetId());
		else
        	setCurrentBudgetSelection(id);
    }

    @Override
    public void update() {
        items = model.getAllBudgets();

        // Also checks to see if previous selection is still there
        fillTable();

        highlightCurrentSelection();

        handleDateSelectionChange();
    }
}
