
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class AccountView extends AbstractView implements IAccountView{

	// Account UI elements
	private JPanel panel;
	private DefaultTableModel tableModel;
	private JLabel accLabel;
	private JLabel accountIDLabel;
	private JLabel bankLabel;
	private JLabel nicknameLabel;
	private JLabel balanceLabel;
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton clearButton;
	private JTextField accountIDTextfield;
	private JTextField bankTextfield;
	private JTextField nicknameTextfield;
	private JTextField balanceTextfield;
	private JTable table;
	private JScrollPane scrollPane;

    private IModelView model;
	
	public AccountView(IModelView model)
	{
        super();
        this.model = model;
        model.attachObserver(this);
		createAccPanel();
		setLayout();
        // The clear button is handled directly by the view, no need for the controller here
        clearButton.addActionListener(e->clearFields());
	}

    public void update() {
        // Clear table
        tableModel.setRowCount(0);

        // Fetch transactions associated with account and display
        AccountList accounts = model.getAllAccounts();
        if (accounts != null) {
            //add rows to table
            for(Account account : accounts) {
                tableModel.addRow(new Object[] {account.getId(), account.getBankName(), account.getNickname(), account.getBalance()});
            }
        }
        tableModel.fireTableDataChanged();
    }

    @Override
    public JPanel getPanel()            {return panel;}
    @Override
    public JButton getAddButton()       {return addButton;}
    @Override
    public JButton getUpdateButton()    {return updateButton;}
    @Override
    public JButton getDeleteButton()    {return deleteButton;}
    @Override
    public void clearFields() {
        accountIDTextfield.setText("");
        bankTextfield.setText("");
        nicknameTextfield.setText("");
        balanceTextfield.setText("");
        setCurrentAccountView(0);
    }
    @Override
    public String getBankInput() {return bankTextfield.getText();}
    @Override
    public String getNicknameInput() {return nicknameTextfield.getText();}
    @Override
    public Integer getBalanceInput() {
        String amount = balanceTextfield.getText();
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
    public Integer getAccountId() {return getCurrentAccountView();}

    public DefaultTableModel getTableModel() {return tableModel;}

	public JTextField getAccountIDTextfield() {return accountIDTextfield;}

	public JTextField getBankTextfield() {return bankTextfield;}

	public JTextField getNicknameTextfield() {return nicknameTextfield;}

	public JTextField getBalanceTextfield() {return balanceTextfield;}

	private void createAccPanel() {
		// Create Account UI elements
		panel = new JPanel();
		accLabel = new JLabel("Accounts");
		accountIDLabel = new JLabel("Account ID");
		bankLabel = new JLabel("Bank");
		nicknameLabel = new JLabel("Nickname");
		balanceLabel = new JLabel("Balance (cents)");
		addButton = new JButton("Add");
		updateButton = new JButton("Update");
		deleteButton = new JButton("Delete");
		clearButton = new JButton("Clear");
		accountIDTextfield = new JTextField(15);
		bankTextfield = new JTextField(15);
		nicknameTextfield = new JTextField(15);
		balanceTextfield = new JTextField(15);
		table = new JTable();
		tableModel = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
		accountIDTextfield.setEditable(false);
		
		// Loading JTable
		Object[] columns = {"ID", "Bank", "Nickname", "Balance (cents)"};

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
					accountIDTextfield.setText(table.getValueAt(i, 0).toString());
					bankTextfield.setText(table.getValueAt(i, 1).toString());
					nicknameTextfield.setText(table.getValueAt(i, 2).toString());
					balanceTextfield.setText(table.getValueAt(i, 3).toString());
                    setCurrentAccountView(i+1);
				}
			}
		});
	}

    @Override
    protected void handleAccountIdChange() {
        // do nothing
    }

    private void setLayout() {
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);	
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(accLabel)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(accountIDLabel)
									.addComponent(bankLabel)
									.addComponent(nicknameLabel)
									.addComponent(balanceLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)		
									.addComponent(accountIDTextfield, 200, 200, 250)
									.addComponent(bankTextfield, 200, 200, 250)
									.addComponent(nicknameTextfield, 200, 200, 250)
									.addComponent(balanceTextfield, 200, 200, 250)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(addButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)									
									.addComponent(updateButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(clearButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(deleteButton))
								))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(scrollPane, 650, 650, 650))
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(accLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(scrollPane, 50, 80, 105)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(accountIDLabel)
										.addComponent(accountIDTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(bankLabel)
									.addComponent(bankTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(nicknameLabel)
									.addComponent(nicknameTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(balanceLabel)
									.addComponent(balanceTextfield))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(addButton)
									.addComponent(clearButton)
									.addComponent(updateButton)
									.addComponent(deleteButton)	
									))))
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, accountIDLabel, bankLabel, nicknameLabel, balanceLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, addButton, updateButton, deleteButton, clearButton);	
	}
}
