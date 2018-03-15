

import java.awt.event.ActionEvent;

public abstract class AbstractViewController{
	
	
	public AbstractViewController(){}
		
	
	AbstractView view;
	public void setView(AbstractView view) {this.view = view;}
	public AbstractView getView() {return view;}
	
	
}
