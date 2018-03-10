import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class AbstractView {
	
	private HashMap<String, AbstractViewController> controlMap;
	private HashMap<String, AbstractModel> modelMap;
	
		
	AbstractView(){
		controlMap = new HashMap<String, AbstractViewController>();
	}
	
	
	
	//=============================
	
	// 			CONTROLS
	
	//=============================
	public void setControl(String key, AbstractViewController control) {
		control.setView(this); //Link the controller back to the view
		controlMap.put(key, control);
	}
	
	public ActionListener getControl(String key) {
		if(controlMap.containsKey(key))
			return controlMap.get(key);
	
		//should probably throw exception
		return null;
	}
	
	
	//=============================

	// 			MODEL
	
	//=============================
	public void setModel(String key, AbstractModel model) {
		model.setView(this); //Link the controller back to the view
		modelMap.put(key, model);
	}
	
	public AbstractModel getModel(String key) {
		if(modelMap.containsKey(key))
			return modelMap.get(key);
	
		//should probably throw exception
		return null;
	}
	
	
	
	
	public void update(){}
	

	public void display() {}
	
	private void detach(){}
	
	
}
