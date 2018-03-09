package GUI;

import javax.swing.*;

public class mainWindow {

	protected static JPanel mainPanel = new JPanel();
	
	public mainWindow() {
		
		accountWindow.init();
		transactionWindow.init();
		budgetWindow.init();
		
//		initMethods.initJFrame("My Money Manager", mainPanel, 500, 500);

	}
}
