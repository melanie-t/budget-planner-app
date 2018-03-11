import java.awt.event.ActionEvent;

public abstract class AbstractViewController implements java.awt.event.ActionListener{
	
	AbstractModel model;
	AbstractView view;
	
	public AbstractViewController(){}
		
	
	public void setView(AbstractView view) {this.view = view;}
	public AbstractView getView() {return view;}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Please implement actionPerformed in the view controller");
	}
}
