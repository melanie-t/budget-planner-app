import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AbstractView {
	
	/*
	public void setUpdateFunction(Callable<update> func) {
		
	}
	*/
	ViewController controller;
	
	LinkedHashMap<String, ActionListener> listeners;
	
	private HashMap<String, AbstractViewController> controlMap;
		
	
	AbstractView(){
		listeners = new LinkedHashMap<String, ActionListener>();
		controlMap = new HashMap<String, AbstractViewController>();
		
	}
	
	public void setController(String key, AbstractViewController control) {
		controlMap.put(key, control);
	}
	
	public ActionListener getController(String key) {
		if(controlMap.containsKey(key))
			return controlMap.get(key);
		
		//should probably throw exception
		return null;
	}
	
	public void setController(ViewController controller) {
		this.controller = controller;
	}
	
	public void update(){}
	

	public void display() {
		
	}
	
	private void detach(){}
	
	
}
