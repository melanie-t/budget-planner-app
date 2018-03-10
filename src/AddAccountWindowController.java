
import java.awt.event.ActionEvent;

import GUI.addAccountWindow;

public class AddAccountWindowController extends AccountController{
	public void actionPerformed(ActionEvent arg0) {
		//still need a way of updating the model here
		try {
			System.out.println("addAccountWindow opened");
			addAccountWindow.init();
		}
		catch(Exception e) {
			System.out.println("Error in opening add window");
		}
	}
}
