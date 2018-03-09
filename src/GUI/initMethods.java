package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class initMethods {
	
	private initMethods() {}

	protected static void initJFrame(String frameName, JPanel panel, int width, int height) {
		JFrame frame = new JFrame(frameName);
		frame.add(panel);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
	}
	
	protected static void initResetButton (JButton resetButton, JTextField [] fieldReset)
	{
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					
					// Clear all textfields
					for (int i = 0; i < fieldReset.length; i++)
						fieldReset[i].setText("");
				}
				
				catch (Exception e) {
					System.out.println("Error in resetting");
				}
			};
		});
	}
	
	protected static void initSubmitButton (JButton submitButton, JTextField [] fieldSubmitted)
	{
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					// Controller for processing textfields
					
				}
				
				catch(Exception e) {
					System.out.println("Error in submitting");
				}
			}
		});
	}
	
	protected static void initDeleteButton (JButton deleteButton, JTextField [] entryDeleted)
	{	
		// Delete entry
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					
				}
				
				catch(Exception e) {
					System.out.println("Error in deleting");
				}
			}
		});
	}
	
	protected static void initEditButton (JButton editButton, JTextField [] editEntry)
	{	
		// Edit entry
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					
				}
				
				catch(Exception e) {
					System.out.println("Error in editting");
				}
			}
		});
	}
}
