package gameLogic.innerEvents;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * this class represent an event generator object
 * @author Dana Akerman
 */
public abstract class EventGenerator 
{
    
    // data members
    //------------------------------------------------------------

    private ArrayList<MyChangeListener> _listeners;

    // c'tor
    //------------------------------------------------------------
    
    /**
     * constructs a new event generator. called only in derived classed
     */
    protected EventGenerator() 
    {
        _listeners = new ArrayList<MyChangeListener>();
    }

    // methods
    //------------------------------------------------------------
    
    /**
     * adds a listener to the event generator
     * @param listener the listener to add
     * @throws NullPointerException if listener is null
     */
    public void addMyChangeListener(MyChangeListener listener) 
    {
        if (listener == null) 
            throw new NullPointerException();

        if (!_listeners.contains(listener)) 
        {
            _listeners.add(listener);
        }
    }

    //------------------------------------------------------------
    
    /**
     * shoots an event with the given message to all the listeners
     * @param message the message of the event
     * @throws NullPointerException if message is null
     */
    protected void fireMyChangeEvent(String message) 
    {
        if (message == null) 
            throw new NullPointerException();

        MyChangeEvent e = new MyChangeEvent(this, message);
        Iterator<MyChangeListener> listeners = _listeners.iterator();
        while (listeners.hasNext()) {
            ((MyChangeListener) listeners.next()).changeEventRecieved(e);
        }
    }
}
