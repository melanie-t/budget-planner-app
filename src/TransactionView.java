import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransactionView extends AbstractView implements ITransactionView {

	// Transaction UI elements
	private JPanel panel;
	private DefaultTableModel tableModel;
	private JLabel transLabel;
	private JLabel typeLabel;
	private JLabel dateLabel;
	private JLabel amountLabel;
	private JLabel descriptionLabel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton clearButton;
	private JButton importButton;
    private JButton updateButton;
	private JTextField typeTextfield;
	private JTextField dateTextfield;
	private JTextField amountTextfield;
	private JTextArea descriptionTextArea;
	private JTable table;
	private JScrollPane scrollPane;

    private IModelView model;
    private TransactionList items;
    private int selectedRow;

	public TransactionView(IModelView model)
	{
        super();
        this.model = model;
        items = new TransactionList();
        selectedRow = -1;
        model.attachObserver(this);
		createTransPanel();
		setLayout();
        // The clear button is handled directly by the view, no need for the controller here
        clearButton.addActionListener(e->handleClear());
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

        items = model.getTransactions(getCurrentAccountSelection());

        // Also checks to see if previous selection is still there
        fillTable();

        highlightCurrentSelection();
    }
    @Override
    public JPanel   getPanel()          {return panel;}
    @Override
    public JButton  getAddButton()      {return addButton;}
    @Override
    public JButton  getDeleteButton()   {return deleteButton;}
    @Override
    public JButton  getImportButton()   {return importButton;}
    @Override
    public JButton  getUpdateButton()   {return updateButton;}
    @Override
    public String getTypeInput() {return typeTextfield.getText();}
    @Override
    public String getDateInput() {return dateTextfield.getText();}
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
    public Integer getTransactionId()   {return items.get(selectedRow).getId();}
    @Override
    public Integer getAccountId() {return getCurrentAccountSelection();}

    @Override
    public void setSelection(Integer id) {
        setCurrentTransactionSelection(id);
    }

    @Override
    protected void handleAccountSelectionChange() {
        setCurrentTransactionSelection(0);
        highlightCurrentSelection();
        update();
    }

    @Override
    protected void handleTransactionSelectionChange() {
        Transaction currentSelection = findTransaction(getCurrentTransactionSelection());
        fillFieldsFromTransaction(currentSelection);
        highlightCurrentSelection();
    }

    private void highlightCurrentSelection() {
        if (getCurrentTransactionSelection() == 0)
        {
            fillFieldsFromTransaction(null);
            return;
        }

        Transaction item = null;
        for (int i = 0; i < items.size(); i++)
        {
            item = items.get(i);
            if (item.getId().equals(getCurrentTransactionSelection()))
            {
                table.setRowSelectionInterval(i, i);
                fillFieldsFromTransaction(item);
                return;
            }
        }
    }

    private void handleClear()
    {
        setCurrentTransactionSelection(0);
        update();
    }

    private Transaction findTransaction(Integer id)
    {
        for (Transaction transaction : items)
        {
            if (transaction.getId().equals(id))
                return transaction;
        }
        return null;
    }

    private void fillFieldsFromTransaction(Transaction transaction)
    {
        if (transaction == null)
        {
            typeTextfield.setText("");
            dateTextfield.setText("");
            amountTextfield.setText("");
            descriptionTextArea.setText("");
        }
        else
        {
            typeTextfield.setText(transaction.getType());
            dateTextfield.setText(transaction.getDate());
            amountTextfield.setText(transaction.getAmount().toString());
            descriptionTextArea.setText(transaction.getDescription());
        }
    }

    private void fillTable()
    {
        // Clear table
        tableModel.setRowCount(0);

        // Fetch transactions associated with account and display
        boolean validSectionFound = false;
        if (!items.isEmpty()) {
            //add rows to table
            for (Transaction item : items)
            {
                tableModel.addRow(new Object[] {item.getType(), item.getDate(), item.getAmount(), item.getDescription()});
                if (!validSectionFound && item.getId().equals(getCurrentTransactionSelection()))
                {
                    validSectionFound = true;
                }
            }
        }
        if (!validSectionFound)
            setCurrentTransactionSelection(0);
    }

    private void createTransPanel() {
		// Create Transaction UI elements
		panel = new JPanel();
		transLabel = new JLabel("Transactions");
		typeLabel = new JLabel("Type");
		dateLabel = new JLabel("Date");
		amountLabel = new JLabel("Amount (cents)");
		descriptionLabel = new JLabel("Description");
		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		clearButton = new JButton("Clear");
		importButton = new JButton("Import");
        updateButton = new JButton("Update");
		typeTextfield = new JTextField(15);
		dateTextfield = new JTextField(15);
		amountTextfield = new JTextField(15);
		descriptionTextArea = new JTextArea(2, 10);
		table = new JTable();
		scrollPane = new JScrollPane(table);
		
		descriptionTextArea.setLineWrap(true);
		
		// Loading JTable
		Object[] columns = {"Type", "Date", "Amount (cents)", "Description"};
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
                selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    Transaction selectedItem = items.get(selectedRow);
                    setCurrentTransactionSelection(selectedItem.getId());
                }
            }
        });
	}
	
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
									.addComponent(descriptionLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(typeTextfield, 250, 250, 250)
									.addComponent(dateTextfield, 250, 250, 250)
									.addComponent(amountTextfield, 250, 250, 250)
									.addComponent(descriptionTextArea, 100, 100, 220)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(addButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(updateButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(deleteButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(clearButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(importButton))))
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
									.addComponent(typeTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(dateLabel)
									.addComponent(dateTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(amountLabel)
									.addComponent(amountTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(descriptionLabel)
										.addComponent(descriptionTextArea))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(addButton)
									.addComponent(updateButton)
									.addComponent(deleteButton)	
									.addComponent(clearButton)
									.addComponent(importButton)))))
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, typeLabel, dateLabel, amountLabel, descriptionLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, addButton, updateButton, deleteButton, importButton, clearButton);
	}
}
