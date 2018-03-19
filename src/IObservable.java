import java.util.HashSet;

/**
 * Created by Pierre-André on 2018-03-18.
 */
public interface IObservable
{
    void attachObserver(IObserver o);

    void detachObserver(IObserver o);

    void notifyObservers();
}
