package GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addAccountWindow {
	
	private void addAccountWindow() {
	}
	
	protected static void init() {
		
		JPanel addAccountPanel = new JPanel();
		
		JLabel inputLabel[] = {
				new JLabel("Bank:"), 
				new JLabel("Nickname:"), 
				new JLabel("Balance:")};
		
		JTextField accountTextfield[] = {new JTextField(20), new JTextField(20), new JTextField(20)};
		
		// Setting labels for textfields and adding to panel
		for (int i = 0; i < inputLabel.length; i++)
		{
			inputLabel[i].setLabelFor(accountTextfield[i]);
			addAccountPanel.add(inputLabel[i]);
			addAccountPanel.add(accountTextfield[i]);
		}
		
		JButton submit = new JButton("Submit");
		JButton reset = new JButton("Reset");
		
		addAccountPanel.add(submit);
		addAccountPanel.add(reset);
		
		initMethods.initSubmitButton(submit, accountTextfield);
		initMethods.initResetButton(reset, accountTextfield);
		
		initMethods.initJFrame("Add Account", addAccountPanel, 330, 200);
	}

}
