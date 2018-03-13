import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TransactionView {
	
	private TransactionView() {}
	
	public TransactionView(MainView v) { 
		createTransPanel();
		v.getFrame().add(transPanel);
		v.update();
	}

	// Transaction UI elements
	private JPanel transPanel;
	private DefaultTableModel transModel;
	private JLabel transLabel;
	private JLabel transTypeLabel;
	private JLabel transDateLabel;
	private JLabel transAmountLabel;
	private JButton transAddButton;
	private JButton transUpdateButton;
	private JButton transDeleteButton;
	private JTextField transTypeTextfield;
	private JTextField transDateTextfield;
	private JTextField transAmountTextfield;
	private JTable transTable;
	
	// Getters and setters
	public JPanel getTransPanel() {return transPanel;}
	public void setTransPanel(JPanel transPanel) {this.transPanel = transPanel;}
	
	public DefaultTableModel getTransModel() {return transModel;}
	public void setTransModel(DefaultTableModel transModel) {this.transModel = transModel;}
	
	public JLabel getTransLabel() {return transLabel;}
	public void setTransLabel(JLabel transLabel) {this.transLabel = transLabel;}
	
	public JLabel getTransTypeLabel () {return transTypeLabel;}
	public void setTransTypeLabel(JLabel transTypeLabel) {this.transTypeLabel = transTypeLabel;}
	
	public JLabel getTransDateLabel() {return transDateLabel;}
	public void setTransDateLabel(JLabel transDateLabel) {this.transDateLabel = transDateLabel;}
	
	public JLabel getTransAmountLabel() {return transAmountLabel;}
	public void setTransAmountLabel(JLabel transAmountLabel) {this.transAmountLabel = transAmountLabel;}
	
	public JTextField getTransTypeTextfield() {return transTypeTextfield;}
	public void setTransTypeTextfield(JTextField transTypeTextfield) {this.transTypeTextfield = transTypeTextfield;}
	
	public JTextField getTransDateTextfield() {return transDateTextfield;}
	public void setTransDateTextfield(JTextField transDateTextfield) {this.transDateTextfield = transDateTextfield;}
	
	public JTextField getTransAmountTextfield() {return transAmountTextfield;}
	public void setTransAmountTextfield(JTextField transAmountTextfield) {this.transAmountTextfield = transAmountTextfield;}

	public JButton getTransAddButton() {return transAddButton;}
	public void setTransAddButton(JButton transAddButton) {this.transAddButton = transAddButton;}

	public JButton getTransUpdateButton() {return transUpdateButton;}
	public void setTransUpdateButton(JButton transUpdateButton) {this.transUpdateButton = transUpdateButton;}

	public JButton getTransDeleteButton() {return transDeleteButton;}
	public void setTransDeleteButton(JButton transDeleteButton) {this.transDeleteButton = transDeleteButton;}

	public JTable getTransTable() {return transTable;}
	public void setTransTable(JTable transTable) {this.transTable = transTable;}
	
	public void update() {
		// Updates JTable
		transModel.fireTableDataChanged();
	}
	
	private void createTransPanel() {
		// Create Transaction UI elements
		transPanel = new JPanel();
		transLabel = new JLabel("Transactions");
		transTypeLabel = new JLabel("Type");
		transDateLabel = new JLabel("Date");
		transAmountLabel = new JLabel("Amount");
		transAddButton = new JButton("Add");
		transUpdateButton = new JButton("Update");
		transDeleteButton = new JButton("Delete");
		transTypeTextfield = new JTextField(15);
		transDateTextfield = new JTextField(15);
		transAmountTextfield = new JTextField(15);
		transTable = new JTable();
		
		// Settings labels to textfields
		transTypeLabel.setLabelFor(transTypeTextfield);
		transDateLabel.setLabelFor(transDateTextfield);
		transAmountLabel.setLabelFor(transAmountTextfield);
		
		// Loading JTable
		Object[] columns = {"Type", "Date", "Amount"};
		transModel = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		transModel.setColumnIdentifiers(columns);
		transTable.setModel(transModel);
		transTable.setPreferredScrollableViewportSize(new Dimension(300, 80));
		transTable.setFillsViewportHeight(true);
	
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(transTable);
		
		// Add Transaction UI elements to Panel
		transPanel.add(transLabel);
		transPanel.add(transTypeLabel);
		transPanel.add(transTypeTextfield);
		transPanel.add(transDateLabel);
		transPanel.add(transDateTextfield);
		transPanel.add(transAmountLabel);
		transPanel.add(transAmountTextfield);
		transPanel.add(transAddButton);
		transPanel.add(transUpdateButton);
		transPanel.add(transDeleteButton);
		transPanel.add(scrollPane);
		
		/*
		// Add Transaction UI elements to frame
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(transTypeLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(transTypeLabel)
						.addComponent(transTypeTextfield)
						.addComponent(transTable))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(transDateLabel)
						.addComponent(transDateTextfield))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(transTypeLabel)
						.addComponent(transAmountTextfield))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(transAddButton)
						.addComponent(transUpdateButton)
						.addComponent(transDeleteButton))
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(transTypeLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(transTypeLabel)
						.addComponent(transDateLabel)
						.addComponent(transAmountLabel)
						.addComponent(transAddButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(transTypeTextfield)
						.addComponent(transDateTextfield)
						.addComponent(transAmountTextfield)
						.addComponent(transUpdateButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(transDeleteButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(transTable))
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, transTypeLabel, transDateLabel, transAmountLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, transAddButton, transUpdateButton, transDeleteButton);
		frame.getContentPane().setLayout(layout);
		*/
	}
}
