import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ViewController {
	
	AbstractModel model;
	AbstractView view;
	
	
	public ViewController(){}
		
	public void setModel(AbstractModel model) {
		this.model = model;
	}
	
	public void setView(AbstractView view) {
		this.view = view;
	}
	
	public void display() {
		
	}
	
	
	
}
