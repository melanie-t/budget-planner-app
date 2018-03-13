
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

import to_be_removed.AbstractView;

public class MainView {
	
	// View uses Swing framework to display UI to user
	protected JFrame mainFrame;
	private String title;
	
	public MainView(String title) {	
		this.title = title;
		display();
	}
	
	// Getters and setters
	public JFrame getFrame() {return mainFrame;}
	public void setFrame(JFrame frame) {this.mainFrame = frame;}
	
	public void update() {
		// Updates JTable
		mainFrame.validate();
		mainFrame.repaint();
	}
	
	public void display() {
		// Create principal frame
		mainFrame = new JFrame(title);
		mainFrame.setSize(500, 500);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
	//	frame.getContentPane().setLayout(new SpringLayout());
	}
		/*
		// Add Account UI elements to frame
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(BankLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(BankLabel)
						.addComponent(BankTextfield)
						.addComponent(Table))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(NicknameLabel)
						.addComponent(NicknameTextfield))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(BankLabel)
						.addComponent(BalanceTextfield))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(AddButton)
						.addComponent(UpdateButton)
						.addComponent(DeleteButton))
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(BankLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(BankLabel)
						.addComponent(NicknameLabel)
						.addComponent(BalanceLabel)
						.addComponent(AddButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(BankTextfield)
						.addComponent(NicknameTextfield)
						.addComponent(BalanceTextfield)
						.addComponent(UpdateButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(DeleteButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(Table))
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, BankLabel, NicknameLabel, BalanceLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, AddButton, UpdateButton, DeleteButton);
		frame.getContentPane().setLayout(layout);
		*/
}
