import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AbstractView {
	
	private HashMap<String, AbstractViewController> controlMap;
		
	AbstractView(){
		controlMap = new HashMap<String, AbstractViewController>();
	}
	
	public void setControl(String key, AbstractViewController control) {
		controlMap.put(key, control);
	}
	
	public ActionListener getControl(String key) {
		if(controlMap.containsKey(key))
			return controlMap.get(key);
	
		//should probably throw exception
		return null;
	}
	
	
	public void update(){}
	

	public void display() {}
	
	private void detach(){}
	
	
}
