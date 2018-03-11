package GUI;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;

public class mainWindow {
	
	private JPanel mainPanel = new JPanel();
	private JPanel accountPanel = new JPanel();
	
	public mainWindow() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		
		accountWindow acc = new accountWindow();
		acc.display();
		
		transactionWindow trans = new transactionWindow();
		trans.display();
		
		budgetWindow budget = new budgetWindow();
		budget.display();
		
		mainPanel.add(acc.accountPanel);
		mainPanel.add(trans.transactionPanel);
		mainPanel.add(budget.budgetPanel);
		
		String name = "My Money Manager";
		
		JFrame mainFrame = new JFrame(name);
		mainFrame.add(mainPanel);
		mainFrame.setSize(700, 400);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
	}
				
	// Only esthetics, will finish later if there's time
	private void layoutManager() {
		
	}
	
}
