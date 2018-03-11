package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addAccountWindow {
	
	private addAccountWindow() {}
	
	public static void init() {
		
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
		
		// Submit button
		// Textfield[0] = Bank
		// Textfield[1] = Nickname
		// Textfield[2] = Balance
		
		// Add controller to here
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					for (int i = 0; i < accountTextfield.length; i++)
					{
						// Returns the value in textfield upon submitting
						System.out.println(accountTextfield[i].getText());
					}
				}
				
				catch(Exception e) {
					System.out.println("Error in submitting");
				}
			}
		});
		
		initMethods.initResetButton(reset, accountTextfield);
		
		initMethods.initJFrame("Add Account", addAccountPanel, 330, 200);
		
	}
}
