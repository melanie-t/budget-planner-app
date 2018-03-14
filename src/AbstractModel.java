
import java.util.HashSet;

public abstract class AbstractModel {
		
	boolean boolNew;
		
	public AbstractModel(){
		boolNew = true;
	}
	
	// Is new
	// used to determine wither id has been set
	public boolean isNew() {return boolNew;}
	public void setIsNewModel(boolean boolNew) {this.boolNew = boolNew;}
	
	
	
	/////////////////////////////////////////////////////////////////   
	// --- Observer pattern things ----------------------------------
	
	// unused atm
	
	private HashSet<AbstractView> m_views = new HashSet<AbstractView>();
	
	public void setView(AbstractView view){
		m_views.add(view);
	}
	
	public void removeView(AbstractView view){
		m_views.remove(view);
	}
	
	private void notifyViews(){ // would probably need to be called in each setter ?
		for (AbstractView view : m_views)
			view.update();
	}
	// ______________________________________________________________
}
