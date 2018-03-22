/**
 * The IObserver interface exposes the method required from the observers in the Observer pattern.
 */
public interface IObserver
{
    /**
     * Called by the subject when observer must update its view.
     */
    void update();
}
