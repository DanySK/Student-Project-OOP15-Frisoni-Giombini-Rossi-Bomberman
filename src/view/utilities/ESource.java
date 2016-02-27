package view.utilities;


import java.util.HashSet;
import java.util.Set;

/**
 * A generic source of events. 
 * 
 * @param <T>
 *      the type of the event notification
 * */
public class ESource<T> {

    private final Set<EObserver<? super T>> set = new HashSet<>();

    /**
     * Add an observer to the source.
     * 
     * @param obs
     *      the EObserver object to add to the source's observers.
     **/
    public void addEObserver(final EObserver<? super T> obs) {
        this.set.add(obs);
    }

    /**
     * Send a message to all the observers.
     * 
     * @param msg
     *      the message sent to observers
     */
    public void notifyEObservers(final T msg) {
        for (final EObserver<? super T> obs : this.set) {
            obs.update(this, msg);
        }
    }
}