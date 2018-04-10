import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Implementation of the ITransactionView interface. Requires a model that implements the IModelView
 * interface.
 */
public class TransactionView extends AbstractView<Transaction> implements ITransactionView, IViewGUI {

	// Transaction UI elements
	private JPanel panel;
	private DefaultTableModel tableModel;
	private JLabel transLabel;
	private JLabel typeLabel;
	private JLabel dateLabel;
	private JLabel budgetLabel;
	private JLabel amountLabel;
	private JLabel descriptionLabel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton clearButton;
	private JButton importButton;
    private JButton updateButton;
	private JTextField amountTextfield;
	private JComboBox budgetField;
	private JTextArea descriptionTextArea;
	private JTable table;
	private JScrollPane scrollPane;
	private JComboBox typeField;
	private JDatePickerImpl dateField;
	private JDatePanelImpl datePanel;

	private HashMap<String, Integer> budgetIndexes;

    /**
     * Cosntructor.
     * @param model model
     */
	public TransactionView(IModelView model)
	{
        super(model);
        budgetIndexes = new HashMap<>();
        items = new ArrayList<>();
        model.attachObserver(this);
		createTransPanel();
		setLayout();
        // The clear button is handled directly by the view, no need for the controller here
        clearButton.addActionListener(e->handleClear());
        typeField.addActionListener(e->handleTypeDropdown());
	}

	private void handleTypeDropdown()
    {
        if (typeField.getSelectedItem().toString().equals("Deposit"))
        {
            budgetField.setSelectedItem("None");
            budgetField.setVisible(false);
        }
        else
        {
            budgetField.setVisible(true);
        }
    }

    @Override
    public void update(){
        // Do not show the transaction window if the user has not selected an account
        if (getCurrentAccountSelection() == 0)
        {
            if (panel.isVisible())
                panel.setVisible(false);
            return;
        }
        else if (!panel.isVisible())
            panel.setVisible(true);

        budgetIndexes = model.getBudgetIndexes();
        budgetField.removeAllItems();

        for (String key : budgetIndexes.keySet())
        {
            budgetField.addItem(key);
        }
        budgetField.setSelectedIndex(budgetIndexes.size() - 1);
        items = model.getTransactionsFromAccount(getCurrentAccountSelection());

        // Also checks to see if previous selection is still there
        fillTable();

        highlightCurrentSelection();
    }

    @Override
    public Integer getBudgetIdInput() {
	    String selection = budgetField.getSelectedItem().toString();
        return budgetIndexes.get(selection);
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
    public void registerImportActionCallback(ActionListener listener, String actionCommand) {
        importButton.setActionCommand(actionCommand);
        importButton.addActionListener(listener);
    }

    @Override
    public JPanel   getPanel()          {return panel;}
    @Override
    public String getTypeInput() {return (String) typeField.getSelectedItem();}
    @Override
    public String getDateInput() {
	    int year = dateField.getModel().getYear();
	    int month = dateField.getModel().getMonth()+1; //months are zero-indexed
	    int day = dateField.getModel().getDay();

	    String monthZero = month < 10? "0": "";
	    String dayZero = day < 10? "0" : "";

	    return year + "-" + monthZero + month + "-" + dayZero + day;
	}
    @Override
    public Integer getAmountInput() {
        String amount = amountTextfield.getText();
        if (!amount.matches("\\d+") || amount.isEmpty())
        {
            return null;
        }
        else
        {
            return Integer.parseInt(amount);
        }
    }

    @Override
    public String getDescriptionInput() {return descriptionTextArea.getText();}
    @Override
    public Integer getSelectedTransactionId()   {return getCurrentTransactionSelection();}
    @Override
    public Integer getSelectedAccountId() {return getCurrentAccountSelection();}

    @Override
    public void setSelection(Integer id) {
        setCurrentTransactionSelection(id);
    }

    @Override
    protected void handleAccountSelectionChange() {
        setCurrentTransactionSelection(0);
        update();
    }

    @Override
    protected void handleTransactionSelectionChange() {
        Transaction currentSelection = findItem(getCurrentTransactionSelection());
        fillFields(currentSelection);
        highlightCurrentSelection();
    }

    protected void highlightCurrentSelection() {
        if (getCurrentTransactionSelection() == 0)
        {
            fillFields(null);
            budgetField.setEnabled(true);
            return;
        }

        Transaction item = null;
        for (int i = 0; i < items.size(); i++)
        {
            item = items.get(i);
            if (item.getId().equals(getCurrentTransactionSelection()))
            {
                table.setRowSelectionInterval(i, i);
                fillFields(item);
                return;
            }
        }
    }

    protected void handleClear()
    {
        setCurrentTransactionSelection(0);
        budgetField.setEnabled(true);
        update();
    }

    @Override
    protected void handleBudgetSelectionChange() {
        //do nothing
        return;
    }

    protected void fillFields(Transaction transaction)
    {
        if (transaction == null)
        {
            typeField.setSelectedIndex(0);
            amountTextfield.setText("");
            descriptionTextArea.setText("");

            LocalDateTime now = LocalDateTime.now();
            dateField.getModel().setDate(
                    now.getYear(),
                    now.getMonthValue() - 1,    //months are zero-indexed
                    now.getDayOfMonth()
            );
      
        }
        else
        { 		
            typeField.setSelectedIndex(Arrays.asList(Transaction.getTransactionTypes()).indexOf(transaction.getType()));
            amountTextfield.setText(transaction.getAmount().toString());
            descriptionTextArea.setText(transaction.getDescription());

            String budgetName = getBudgetNameFromId(transaction.getAssociatedBudgetId());

            budgetField.setSelectedItem(budgetName);

            String[] parts = transaction.getDate().split("-");
            dateField.getModel().setDate(
                    Integer.parseInt(parts[0]),         //year
                    Integer.parseInt(parts[1])-1,   //months are zero-indexed
                    Integer.parseInt(parts[2])            //day
            );
        }
    }

    private String getBudgetNameFromId(Integer budgetId)
    {
        Iterator it = budgetIndexes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
           if (pair.getValue() == budgetId)
               return pair.getKey().toString();
        }
        return "";
    }

    protected void fillTable()
    {
        // Clear table
        tableModel.setRowCount(0);

        // Fetch transactions associated with account and display
        boolean validSectionFound = false;
        if (!items.isEmpty()) {
            //add rows to table
            for (Transaction item : items)
            {
                String budget = getBudgetNameFromId(item.getAssociatedBudgetId());

                if (budget.equals("None"))
                    budget = "";

                tableModel.addRow(new Object[] {item.getType(), item.getDate(), item.getAmount(), item.getDescription(), budget});
                if (!validSectionFound && item.getId().equals(getCurrentTransactionSelection()))
                {
                    validSectionFound = true;
                }
            }
        }
        if (!validSectionFound)
            setCurrentTransactionSelection(0);
    }

    /**
     * Create GUI elements
     */
    private void createTransPanel() {
        //JDatePicker properties
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

		// Create Transaction UI elements
		panel = new JPanel();
		transLabel = new JLabel("Transactions");
		typeLabel = new JLabel("Type");
		dateLabel = new JLabel("Date");
		budgetLabel = new JLabel("Budget");
		amountLabel = new JLabel("Amount (cents)");
		descriptionLabel = new JLabel("Description");
		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		clearButton = new JButton("Clear");
		importButton = new JButton("Import");
        updateButton = new JButton("Update");
        budgetField = new JComboBox();
		typeField = new JComboBox(Transaction.getTransactionTypes());
		datePanel = new JDatePanelImpl(model, p);
		dateField = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		amountTextfield = new JTextField(15);
		descriptionTextArea = new JTextArea(2, 10);
		table = new JTable();
		scrollPane = new JScrollPane(table);
		
		descriptionTextArea.setLineWrap(true);
		
		// Loading JTable
		Object[] columns = {"Type", "Date", "Amount (cents)", "Description", "Budget"};
		tableModel = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel.setColumnIdentifiers(columns);
		table.setModel(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(300, 80));
		table.setFillsViewportHeight(true);

        // Set mouse click to update values in field with currently selected row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // i = the index of the selected row
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    Transaction selectedItem = items.get(selectedRow);
                    setCurrentTransactionSelection(selectedItem.getId());
                }
            }
        });
	}

    /**
     * Set layout for GUI elements
     */
	private void setLayout() {
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);	
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(transLabel)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(typeLabel)
									.addComponent(dateLabel)
									.addComponent(amountLabel)
									.addComponent(budgetLabel)
									.addComponent(descriptionLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(typeField, 250, 250, 250)
									.addComponent(dateField, 250, 250, 250)
									.addComponent(amountTextfield, 250, 250, 250)
									.addComponent(budgetField, 250, 250, 250)
									.addComponent(descriptionTextArea, 100, 100, 250)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(addButton)
                                    .addComponent(importButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(updateButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(deleteButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(clearButton))))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(scrollPane, 650, 650, 650))
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(transLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(scrollPane, 50, 80, 105)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(typeLabel)
									.addComponent(typeField))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(dateLabel)
									.addComponent(dateField))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(amountLabel)
									.addComponent(amountTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(budgetLabel)
										.addComponent(budgetField))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(descriptionLabel)
										.addComponent(descriptionTextArea, 50, 50, 50))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(addButton)
									.addComponent(updateButton)
									.addComponent(deleteButton)	
									.addComponent(clearButton))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(importButton)))))
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, typeLabel, dateLabel, amountLabel, descriptionLabel, budgetLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, addButton, updateButton, deleteButton, importButton, clearButton);
	}
}
