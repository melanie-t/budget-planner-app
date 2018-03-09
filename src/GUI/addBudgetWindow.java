package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addBudgetWindow {

	private addBudgetWindow(){}
	
	protected static void init(){
		
		JPanel addBudgetPanel = new JPanel();
		
		JLabel inputLabel[] = {
				new JLabel("Category:"), 
				new JLabel("Amount:"), 
				new JLabel("Balance:"),
				new JLabel("Time frame (days):")};
		
		JTextField budgetTextfield[] = {new JTextField(20), new JTextField(20), new JTextField(20), new JTextField(20)};
		
		// Setting labels for textfields and adding to panel
		for (int i = 0; i < inputLabel.length; i++)
		{
			inputLabel[i].setLabelFor(budgetTextfield[i]);
			addBudgetPanel.add(inputLabel[i]);
			addBudgetPanel.add(budgetTextfield[i]);
		}
		
		JButton submit = new JButton("Submit");
		JButton reset = new JButton("Reset");
		
		addBudgetPanel.add(submit);
		addBudgetPanel.add(reset);
		
		// Add controller to here
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					for (int i = 0; i < budgetTextfield.length; i++)
					{
						// Returns the value in textfield upon submitting
						System.out.println(budgetTextfield[i].getText());
					}
				}
				
				catch(Exception e) {
					System.out.println("Error in submitting");
				}
			}
		});
		
		initMethods.initResetButton(reset, budgetTextfield);
		
		initMethods.initJFrame("Add Budget", addBudgetPanel, 250, 300);
	}
}
