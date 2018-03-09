package GUI;

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
		
		JTextField budgetTextField[] = {new JTextField(20), new JTextField(20), new JTextField(20), new JTextField(20)};
		
		// Setting labels for textfields and adding to panel
		for (int i = 0; i < inputLabel.length; i++)
		{
			inputLabel[i].setLabelFor(budgetTextField[i]);
			addBudgetPanel.add(inputLabel[i]);
			addBudgetPanel.add(budgetTextField[i]);
		}
		
		JButton submit = new JButton("Submit");
		JButton reset = new JButton("Reset");
		
		addBudgetPanel.add(submit);
		addBudgetPanel.add(reset);
		
		initMethods.initSubmitButton(submit, budgetTextField);
		initMethods.initResetButton(reset, budgetTextField);
		
		initMethods.initJFrame("Add Budget", addBudgetPanel, 250, 300);
	}
}
