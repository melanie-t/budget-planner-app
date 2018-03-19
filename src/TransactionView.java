import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransactionView extends AbstractView implements ITransactionView {

	// Transaction UI elements
	private JPanel panel;
	private DefaultTableModel tableModel;
	private JLabel accountIDLabel;
	private JLabel transactionIDLabel;
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
	private JTextField accountIDTextfield;
	private JTextField transactionIDTextfield;
	private JTextField typeTextfield;
	private JTextField dateTextfield;
	private JTextField amountTextfield;
	private JTextArea descriptionTextArea;
	private JTable table;
	private JScrollPane scrollPane;

    private IModelView model;

	public TransactionView(IModelView model)
	{
        super();
        this.model = model;
        model.attachObserver(this);
		createTransPanel();
		setLayout();
        // The clear button is handled directly by the view, no need for the controller here
        clearButton.addActionListener(e->clearFields());
	}
    @Override
    public void update(){
        // Do not show the transaction window if the user has not selected an account
        if (getCurrentAccountView() == 0)
        {
            if (panel.isVisible())
                panel.setVisible(false);
            return;
        }
        else if (!panel.isVisible())
            panel.setVisible(true);

        // Clear table
        tableModel.setRowCount(0);

        // Fetch transactions associated with account and display
        TransactionList transactions = model.getTransactions(getCurrentAccountView());
        if (transactions != null) {
            //add rows to table
            for(Transaction transaction : transactions) {
                tableModel.addRow(new Object[] {transaction.getId(), transaction.getType(), transaction.getDate(), transaction.getAmount(), transaction.getDescription()});
            }
        }
        tableModel.fireTableDataChanged();
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
    public void clearFields() {
        transactionIDTextfield.setText("");
        typeTextfield.setText("");
        dateTextfield.setText("");
        amountTextfield.setText("");
        descriptionTextArea.setText("");
        table.clearSelection();
    }

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
    public Integer getTransactionId() {return Integer.parseInt(transactionIDTextfield.getText());}
    @Override
    public Integer getAccountId() {return getCurrentAccountView();}
    @Override
    protected void handleAccountIdChange() {
        update();
    }

    private void createTransPanel() {
		// Create Transaction UI elements
		panel = new JPanel();
		transLabel = new JLabel("Transactions");
		accountIDLabel = new JLabel("Account ID");
		transactionIDLabel = new JLabel("Transaction ID");
		typeLabel = new JLabel("Type");
		dateLabel = new JLabel("Date");
		amountLabel = new JLabel("Amount (cents)");
		descriptionLabel = new JLabel("Description");
		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		clearButton = new JButton("Clear");
		importButton = new JButton("Import");
        updateButton = new JButton("Update");
		accountIDTextfield = new JTextField(15);
		transactionIDTextfield = new JTextField(15);
		typeTextfield = new JTextField(15);
		dateTextfield = new JTextField(15);
		amountTextfield = new JTextField(15);
		descriptionTextArea = new JTextArea(2, 10);
		table = new JTable();
		scrollPane = new JScrollPane(table);
		
		accountIDTextfield.setEditable(false);
		transactionIDTextfield.setEditable(false);
		descriptionTextArea.setLineWrap(true);
		
		// Loading JTable
		Object[] columns = {"Transaction ID", "Type", "Date", "Amount (cents)", "Description"};
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
                int i = table.getSelectedRow();
                if (i >= 0) {
                    accountIDTextfield.setText(getCurrentAccountView() + "");
                    transactionIDTextfield.setText(table.getValueAt(i, 0).toString());
                    typeTextfield.setText(table.getValueAt(i, 1).toString());
                    dateTextfield.setText(table.getValueAt(i, 2).toString());
                    amountTextfield.setText(table.getValueAt(i, 3).toString());
                    descriptionTextArea.setText(table.getValueAt(i, 4).toString());
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
									.addComponent(accountIDLabel)
									.addComponent(transactionIDLabel)
									.addComponent(typeLabel)
									.addComponent(dateLabel)
									.addComponent(amountLabel)
									.addComponent(descriptionLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(accountIDTextfield, 250, 250, 250)
									.addComponent(transactionIDTextfield, 250, 250, 250)
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
										.addComponent(accountIDLabel)
										.addComponent(accountIDTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(transactionIDLabel)
										.addComponent(transactionIDTextfield))
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
		
		layout.linkSize(SwingConstants.HORIZONTAL, transactionIDLabel, accountIDLabel, typeLabel, dateLabel, amountLabel, descriptionLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, addButton, updateButton, deleteButton, importButton, clearButton);
	}
}
