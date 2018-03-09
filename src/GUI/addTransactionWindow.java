package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addTransactionWindow {

	private addTransactionWindow() {}
	
	protected static void initialize() {
		
		JPanel transactionPanel = new JPanel();
		
		JLabel transactionLabel[] = {
				new JLabel("Date:"),
				new JLabel("Amount:"),
				new JLabel("Category:")};
	
		JTextField transactionTextfield[] = {
				new JTextField(20),
				new JTextField(20),
				new JTextField(20)};

		// Setting labels for textfields and adding to panel
		for (int i = 0; i<transactionLabel.length; i++)
		{
			transactionLabel[i].setLabelFor(transactionTextfield[i]);
			transactionPanel.add(transactionLabel[i]);
			transactionPanel.add(transactionTextfield[i]);
		}
		
		JButton submit = new JButton("Submit");
		JButton reset = new JButton("Reset");

		transactionPanel.add(submit);
		transactionPanel.add(reset);
		
		// Add controller to here
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					for (int i = 0; i < transactionTextfield.length; i++)
					{
						// Returns the value in textfield upon submitting
						System.out.println(transactionTextfield[i].getText());
					}
				}
				
				catch(Exception e) {
					System.out.println("Error in submitting");
				}
			}
		});
		
		
		initMethods.initResetButton (reset, transactionTextfield);
		
		initMethods.initJFrame("Add Transaction", transactionPanel, 330, 200);
	}
}
