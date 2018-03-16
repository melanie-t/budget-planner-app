import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TransactionView {

	// Transaction UI elements
	private JPanel panel;
	private DefaultTableModel model;
	private JLabel transLabel;
	private JLabel typeLabel;
	private JLabel dateLabel;
	private JLabel amountLabel;
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton clearButton;
	private JButton importButton;
	private JTextField typeTextfield;
	private JTextField dateTextfield;
	private JTextField amountTextfield;
	private JTable table;
	private JScrollPane scrollPane;
	
	public TransactionView() { 
		createTransPanel();
	}
	
	// Getters and setters
	public JPanel getPanel() {return panel;}
	public void setPanel(JPanel panel) {this.panel = panel;}
	
	public DefaultTableModel getModel() {return model;}
	public void setModel(DefaultTableModel model) {this.model = model;}
	
	public JLabel getTransLabel() {return transLabel;}
	public void setTransLabel(JLabel Label) {this.transLabel = Label;}
	
	public JLabel getTypeLabel () {return typeLabel;}
	public void setTypeLabel(JLabel typeLabel) {this.typeLabel = typeLabel;}
	
	public JLabel getDateLabel() {return dateLabel;}
	public void setDateLabel(JLabel dateLabel) {this.dateLabel = dateLabel;}
	
	public JLabel getAmountLabel() {return amountLabel;}
	public void setAmountLabel(JLabel amountLabel) {this.amountLabel = amountLabel;}
	
	public JTextField getTypeTextfield() {return typeTextfield;}
	public void setTypeTextfield(JTextField typeTextfield) {this.typeTextfield = typeTextfield;}
	
	public JTextField getDateTextfield() {return dateTextfield;}
	public void setDateTextfield(JTextField dateTextfield) {this.dateTextfield = dateTextfield;}
	
	public JTextField getAmountTextfield() {return amountTextfield;}
	public void setAmountTextfield(JTextField amountTextfield) {this.amountTextfield = amountTextfield;}

	public JButton getAddButton() {return addButton;}
	public void setAddButton(JButton addButton) {this.addButton = addButton;}

	public JButton getUpdateButton() {return updateButton;}
	public void setUpdateButton(JButton updateButton) {this.updateButton = updateButton;}

	public JButton getDeleteButton() {return deleteButton;}
	public void setDeleteButton(JButton deleteButton) {this.deleteButton = deleteButton;}

	public JButton getClearButton() {return clearButton;}
	public void setClearButton(JButton clearButton) {this.clearButton = clearButton;}
	
	public JButton getImportButton() {return importButton;}
	public void setImportButton(JButton importButton) {this.importButton = importButton;}
	
	public JTable getTable() {return table;}
	public void setTable(JTable table) {this.table = table;}
	
	public JScrollPane getScrollPane() {return scrollPane;}
	public void setScrollPane(JScrollPane scrollPane) {this.scrollPane = scrollPane;}
	
	public void update() {
		// Updates JTable
		model.fireTableDataChanged();
	}
	
	private void createTransPanel() {
		// Create Transaction UI elements
		panel = new JPanel();
		transLabel = new JLabel("Transactions");
		typeLabel = new JLabel("Type");
		dateLabel = new JLabel("Date");
		amountLabel = new JLabel("Amount");
		addButton = new JButton("Add");
		//updateButton = new JButton("Update");
		deleteButton = new JButton("Delete");
		clearButton = new JButton("Clear");
		importButton = new JButton("Import");
		typeTextfield = new JTextField(15);
		dateTextfield = new JTextField(15);
		amountTextfield = new JTextField(15);
		table = new JTable();
		scrollPane = new JScrollPane(table);
		
		// Settings labels to textfields
		typeLabel.setLabelFor(typeTextfield);
		dateLabel.setLabelFor(dateTextfield);
		amountLabel.setLabelFor(amountTextfield);
		
		// Loading JTable
		Object[] columns = {"Account ID", "Transaction ID", "Type", "Date", "Amount"};
		model = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.setPreferredScrollableViewportSize(new Dimension(300, 80));
		table.setFillsViewportHeight(true);
	
		setLayout();
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
									.addComponent(amountLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)								
									.addComponent(typeTextfield)
									.addComponent(dateTextfield)
									.addComponent(amountTextfield)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(addButton))
								//.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)									
									//.addComponent(updateButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(deleteButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(clearButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(importButton))))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(scrollPane, 550, 550, 550))
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
									.addComponent(addButton)
									//.addComponent(updateButton)
									.addComponent(deleteButton)	
									.addComponent(clearButton)
									.addComponent(importButton)))))
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, typeLabel, dateLabel, amountLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, addButton, deleteButton, importButton, clearButton);	
	}
}
