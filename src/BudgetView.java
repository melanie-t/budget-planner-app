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

public class BudgetView extends AbstractView{
	// Budount UI elements
		private MainView mainView;
		private JPanel panel;
		private DefaultTableModel model;
		private JLabel budLabel;
		private JLabel typeLabel;
		private JLabel startLabel;
		private JLabel endLabel;
		private JLabel amountLabel;
		private JButton addButton;
		private JButton updateButton;
		private JButton deleteButton;
		private JButton clearButton;
		private JTextField typeTextfield;
		private JTextField startTextfield;
		private JTextField endTextfield;
		private JTextField amountTextfield;
		private JTable table;
		private JScrollPane scrollPane;
		
		public BudgetView() { 
			createBudPanel();
		}
		
		// Getters and setters
		public JPanel getPanel() {return panel;}
		public void setPanel(JPanel budPanel) {this.panel = budPanel;}
		
		public DefaultTableModel getTableModel() {return model;}
		public void setTableModel(DefaultTableModel budModel) {this.model = budModel;}
		
		public JLabel getBudLabel() {return budLabel;}
		public void setBudLabel(JLabel budLabel) {this.budLabel = budLabel;}
		
		public JLabel getTypeLabel () {return typeLabel;}
		public void setTypeLabel(JLabel typeLabel) {this.typeLabel = typeLabel;}
		
		public JLabel getStartLabel() {return startLabel;}
		public void setStart(JLabel startLabel) {this.startLabel = startLabel;}
		
		public JLabel getEndLabel() {return endLabel;}
		public void setEndLabel(JLabel endLabel) {this.endLabel = endLabel;}
		
		public JLabel getamountLabel() {return amountLabel;}
		public void setamountLabel(JLabel amountLabel) {this.amountLabel = amountLabel;}
		
		public JTextField getTypeTextfield() {return typeTextfield;}
		public void setTypeTextfield(JTextField typeTextfield) {this.typeTextfield = typeTextfield;}
		
		public JTextField getStartTextfield() {return startTextfield;}
		public void setStartTextfield(JTextField startTextfield) {this.startTextfield = startTextfield;}
		
		public JTextField getEndTextfield() {return endTextfield;}
		public void setEndTextfield(JTextField endTextfield) {this.endTextfield = endTextfield;}

		public JButton getAddButton() {return addButton;}
		public void setAddButton(JButton addButton) {this.addButton = addButton;}

		public JButton getUpdateButton() {return updateButton;}
		public void setUpdateButton(JButton updateButton) {this.updateButton = updateButton;}

		public JButton getDeleteButton() {return deleteButton;}
		public void setDeleteButton(JButton deleteButton) {this.deleteButton = deleteButton;}
		
		public JButton getClearButton() {return clearButton;}
		public void setClearButton(JButton clearButton) {this.clearButton = clearButton;}


		public JTable getTable() {return table;}
		public void setTable(JTable table) {this.table = table;}
		
		public JScrollPane getScrollPane() {return scrollPane;}
		public void setScrollPane(JScrollPane scrollPane) {this.scrollPane = scrollPane;}
		
		public void update() {
			// Updates JTable
			model.fireTableDataChanged();
		}
		
		private void createBudPanel() {
			// Create Budount UI elements
			panel = new JPanel();
			budLabel = new JLabel("Budget");
			typeLabel = new JLabel("Type");
			startLabel = new JLabel("Start Date");
			endLabel = new JLabel("End Date");
			amountLabel = new JLabel("Amount");
			addButton = new JButton("Add");
			updateButton = new JButton("Update");
			deleteButton = new JButton("Delete");
			clearButton = new JButton("Clear");
			typeTextfield = new JTextField(15);
			startTextfield = new JTextField(15);
			endTextfield = new JTextField(15);
			amountTextfield = new JTextField(15);
			table = new JTable();
			model = new DefaultTableModel() {
			    @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			scrollPane = new JScrollPane(table);
			
			// Settings labels to textfields
			typeLabel.setLabelFor(typeTextfield);
			startLabel.setLabelFor(startTextfield);
			endLabel.setLabelFor(endTextfield);
			amountLabel.setLabelFor(amountTextfield);
			
			// Loading JTable
			Object[] columns = {"Type", "Start", "End", "Amount"};

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
							.addComponent(budLabel)
							.addGroup(layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(typeLabel)
										.addComponent(startLabel)
										.addComponent(endLabel)
										.addComponent(amountLabel))
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)								
										.addComponent(typeTextfield)
										.addComponent(startTextfield)
										.addComponent(endTextfield)
										.addComponent(amountTextfield)))
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
							.addComponent(scrollPane))
			);
			
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(budLabel))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(scrollPane, 50, 80, 105)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
							.addGroup(layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(typeLabel)
										.addComponent(typeTextfield))
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(startLabel)
										.addComponent(startTextfield))
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(endLabel)
										.addComponent(endTextfield))
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(amountLabel)
											.addComponent(amountTextfield))
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(addButton)
										.addComponent(clearButton)
										.addComponent(updateButton)
										.addComponent(deleteButton)	
										))))
			);
			
			layout.linkSize(SwingConstants.HORIZONTAL, typeLabel, startLabel, endLabel, amountLabel);
			layout.linkSize(SwingConstants.HORIZONTAL, addButton, updateButton, deleteButton, clearButton);	
		}
}
