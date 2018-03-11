import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;


public abstract class AbstractView {
	
	private HashMap<String, AbstractEventListener> listenerMap;
	private HashMap<String, AbstractModel> modelMap;
	
		
	AbstractView(){
		listenerMap = new HashMap<String, AbstractEventListener>();
	}
	
	
	
	//=============================
	
	// 			CONTROLS
	
	//=============================
	public void setListener(String key, AbstractEventListener listener) {
		listener.setView(this); //Link the controller back to the view
		listenerMap.put(key, listener);
	}
	
	public ActionListener getListener(String key) {
		if(listenerMap.containsKey(key))
			return listenerMap.get(key);
	
		//should probably throw exception
		return null;
	}
	
	//------------------------------------------
	// TODO LISTENER - for things left to be done
	public TodoListener todoListener() {
		return new TodoListener();
	}
	public class TodoListener extends AbstractEventListener{
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("TODO");
		}
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
	
	
	
}
