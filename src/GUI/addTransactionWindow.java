package GUI;

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
		
		initMethods.initSubmitButton (submit, transactionTextfield);
		initMethods.initResetButton (reset, transactionTextfield);
		initMethods.initJFrame("Add Transaction", transactionPanel, 330, 200);
	}
}
