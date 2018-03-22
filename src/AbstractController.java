import java.awt.event.ActionListener;

/**
 * Base class for controller objects.
 * Classes that inherit should specialize the template with the Interface type
 * of the view it is associated with.
 * @param <T> Interface type of the associated view
 */
public abstract class AbstractController<T> implements ActionListener {
    protected T view;
    protected IModelController model;

    /**
     * Constructor.
     * @param view associated view interface
     * @param model associated model interface
     */
    public AbstractController(T view, IModelController model) {
        this.model = model;
        this.view = view;
    }
}

