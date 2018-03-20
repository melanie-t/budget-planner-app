import java.awt.event.ActionListener;
import java.util.Observer;

public abstract class AbstractController<T> implements ActionListener
{
    protected T view;
    protected IModelController model;

    public AbstractController(T view, IModelController model)
    {
        this.model = model;
        this.view = view;
    }
}
