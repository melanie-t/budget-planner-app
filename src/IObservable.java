import java.util.HashSet;

/**
 * The IObservable interface exposes the functions for the subject in the Observer pattern.
 */
public interface IObservable
{
    /**
     * Attach an observer to this object.
     * @param o observer to add
     */
    void attachObserver(IObserver o);

    /**
     * Detach an observer from this object.
     * @param o observer to detach
     */
    void detachObserver(IObserver o);

    /**
     * Calls the update() method on all attached observers.
     */
    void notifyObservers();
}
