
import java.awt.Component;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainView
{
	// View uses Swing framework to display UI to user
    private JFrame mainFrame;

	public MainView(String title,
                    IViewGUI accountView,
                    IViewGUI transactionView,
                    IViewGUI budgetView,
                    WindowListener listener)
    {
        // Create main frame container
        mainFrame = new JFrame(title);
        mainFrame.setSize(1050, 500);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));

        // Create main panel
        JPanel mainPanel = new JPanel();
        JPanel secondaryPanel = new JPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        secondaryPanel.setLayout(new BoxLayout(secondaryPanel, BoxLayout.Y_AXIS));
        
        // Attach account panel
        JPanel accountPanel = accountView.getPanel();
        accountPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(accountPanel);
        // Attach transaction panel
        JPanel transactionPanel = transactionView.getPanel();
        transactionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(transactionPanel);
        
        // Attach budget panel
        JPanel budgetPanel = budgetView.getPanel();
        budgetPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        secondaryPanel.add(budgetPanel);
        
        tabbedPane.addTab("Accounts", mainPanel);
        tabbedPane.addTab("Budget", secondaryPanel);
        
        // Attach main panel to frame
        mainFrame.add(tabbedPane);
        mainFrame.addWindowListener(listener);

        mainFrame.validate();
        mainFrame.repaint();
	}
}
