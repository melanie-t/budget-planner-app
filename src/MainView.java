
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainView extends AbstractView{
	
	// View uses Swing framework to display UI to user
	protected JFrame mainFrame;
	private String title;
	
	public MainView() {
	}
	
	public MainView(String title) {	
		this.title = title;
		display();
	}
	
	// Getters and setters
	public JFrame getFrame() {return mainFrame;}
	public void setFrame(JFrame frame) {this.mainFrame = frame;}
	
	public void update() {
		// Repaints the main frame
		mainFrame.validate();
		mainFrame.repaint();
	}
	
	public void display() {
		// Create principal frame
		mainFrame = new JFrame(title);
		mainFrame.setSize(670, 350);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
	}
	
	public void setLayout(AccountView accView, TransactionView transView) {
		// Add Account UI elements to frame
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JPanel accountPanel = accView.getPanel();
		JPanel transactionPanel = transView.getPanel();
		
		mainPanel.add(accountPanel);
		mainPanel.add(transactionPanel);
		
		mainFrame.add(mainPanel);
		update();
	}
}
