package to_be_removed;

import java.awt.event.ActionEvent;

public abstract class AbstractEventListener implements java.awt.event.ActionListener{
	
	public AbstractEventListener(){}
	
	AbstractView view;
	public void setView(AbstractView view) {this.view = view;}
	public AbstractView getView() {return view;}
	
	AbstractViewController controller;
	public void setController(AbstractViewController controller) {this.controller = controller;}
	public AbstractViewController getController() {return controller;}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Please implement actionPerformed in the view controller");
	}
}
