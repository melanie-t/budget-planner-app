import java.util.HashSet;
import GUI.AbstractView;

public class ViewContainer {
	private HashSet<AbstractView> m_views = new HashSet<AbstractView>();
	
	public ViewContainer(){}
		
	public void attach(AbstractView view){
		m_views.add(view);
	}
	
	public void detach(AbstractView view){
		m_views.remove(view);
	}
	
	private void notifyViews()	{
		for (AbstractView view : m_views)
			view.update();
	}
}
