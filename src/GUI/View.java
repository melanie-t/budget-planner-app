package GUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class View extends AbstractView {
	
	// View uses Swing framework to display UI to user
	private JFrame frame;
	private String title;
	
	// Account UI elements
	private JPanel accPanel;
	private JLabel accLabel;
	private JLabel accBankLabel;
	private JLabel accNicknameLabel;
	private JLabel accBalanceLabel;
	private JButton accAddButton;
	private JButton accUpdateButton;
	private JButton accDeleteButton;
	private JTextField accBankTextfield;
	private JTextField accNicknameTextfield;
	private JTextField accBalanceTextfield;
	private JTable accTable;
	
	public View(String title) {	
		this.title = title;
		display();
	}
	
	// Getters and setters
	public JFrame getFrame() {return frame;}
	public void setFrame(JFrame frame) {this.frame = frame;}
	
	public JPanel getAccPanel() {return accPanel;}
	public void setAccPanel(JPanel accPanel) {this.accPanel = accPanel;}
	
	public JLabel getAccLabel() {return accLabel;}
	public void setAccLabel(JLabel accLabel) {this.accLabel = accLabel;}
	
	public JLabel getAccBankLabel () {return accBankLabel;}
	public void setAccBankLabel(JLabel accBankLabel) {this.accBankLabel = accBankLabel;}
	
	public JLabel getAccNicknameLabel() {return accNicknameLabel;}
	public void setAccNicknameLabel(JLabel accNicknameLabel) {this.accNicknameLabel = accNicknameLabel;}
	
	public JLabel getAccBalanceLabel() {return accBalanceLabel;}
	public void setAccBalanceLabel(JLabel accBalanceLabel) {this.accBalanceLabel = accBalanceLabel;}
	
	public JTextField getAccBankTextfield() {return accBankTextfield;}
	public void setAccBankTextfield(JTextField accBankTextfield) {this.accBankTextfield = accBankTextfield;}
	
	public JTextField getAccNicknameTextfield() {return accNicknameTextfield;}
	public void setAccNicknameTextfield(JTextField accNicknameTextfield) {this.accNicknameTextfield = accNicknameTextfield;}
	
	public JTextField getAccBalanceTextfield() {return accBalanceTextfield;}
	public void setAccBalanceTextfield(JTextField accBalanceTextfield) {this.accBalanceTextfield = accBalanceTextfield;}

	public JButton getAccAddButton() {return accAddButton;}
	public void setAccAddButton(JButton accAddButton) {this.accAddButton = accAddButton;}

	public JButton getAccUpdateButton() {return accUpdateButton;}
	public void setAccUpdateButton(JButton accUpdateButton) {this.accUpdateButton = accUpdateButton;}

	public JButton getAccDeleteButton() {return accDeleteButton;}
	public void setAccDeleteButton(JButton accDeleteButton) {this.accDeleteButton = accDeleteButton;}

	public JTable getAccTable() {return accTable;}
	public void setAccTable(JTable accTable) {this.accTable = accTable;}
	
	public void update() {
		// Updates JTable
	}
	
	public void display() {
		// Create principal frame
		frame = new JFrame(title);
		addAccPanel();
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
	//	frame.getContentPane().setLayout(new SpringLayout());
	}
	
	private void addAccPanel() {
		// Create Account UI elements
		accPanel = new JPanel();
		accLabel = new JLabel("Accounts");
		accBankLabel = new JLabel("Bank");
		accNicknameLabel = new JLabel("Nickname");
		accBalanceLabel = new JLabel("Balance");
		accAddButton = new JButton("Add");
		accUpdateButton = new JButton("Update");
		accDeleteButton = new JButton("Delete");
		accBankTextfield = new JTextField(15);
		accNicknameTextfield = new JTextField(15);
		accBalanceTextfield = new JTextField(15);
		accTable = new JTable();
		
		// Settings labels to textfields
		accBankLabel.setLabelFor(accBankTextfield);
		accNicknameLabel.setLabelFor(accNicknameTextfield);
		accBalanceLabel.setLabelFor(accBalanceTextfield);
		
		// Add Account UI elements to Panel
		accPanel.add(accLabel);
		accPanel.add(accBankLabel);
		accPanel.add(accBankTextfield);
		accPanel.add(accNicknameLabel);
		accPanel.add(accNicknameTextfield);
		accPanel.add(accBalanceLabel);
		accPanel.add(accBalanceTextfield);
		accPanel.add(accAddButton);
		accPanel.add(accUpdateButton);
		accPanel.add(accDeleteButton);
		accPanel.add(accTable);

		frame.add(accPanel);
		
		/*
		// Add Account UI elements to frame
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(accBankLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(accBankLabel)
						.addComponent(accBankTextfield)
						.addComponent(accTable))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(accNicknameLabel)
						.addComponent(accNicknameTextfield))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(accBankLabel)
						.addComponent(accBalanceTextfield))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(accAddButton)
						.addComponent(accUpdateButton)
						.addComponent(accDeleteButton))
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(accBankLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(accBankLabel)
						.addComponent(accNicknameLabel)
						.addComponent(accBalanceLabel)
						.addComponent(accAddButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(accBankTextfield)
						.addComponent(accNicknameTextfield)
						.addComponent(accBalanceTextfield)
						.addComponent(accUpdateButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(accDeleteButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(accTable))
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, accBankLabel, accNicknameLabel, accBalanceLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, accAddButton, accUpdateButton, accDeleteButton);
		frame.getContentPane().setLayout(layout);
		*/
	}
}
