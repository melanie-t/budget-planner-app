

import java.awt.event.ActionEvent;

public abstract class AbstractViewController{
	
	
	public AbstractViewController(){
		controllerInitialized = false;
	}
		
	
	AbstractView view;
	public void setView(AbstractView view) {this.view = view;}
	public AbstractView getView() {return view;}
	
	
	private boolean controllerInitialized;
	public void setIsInitialized(boolean bool) {this.controllerInitialized = bool; }
	public boolean getIsInitialized() {	return controllerInitialized; }
	
}
