package gameLogic.innerEvents;


import java.util.EventListener;


/**
 * this interface represents an object that listens to change events
 * @author Dana Akerman
 */
public interface MyChangeListener extends EventListener 
{

    /**
     * handles the actions need to be done when change event received
     * @param e the event
     */
    public void changeEventRecieved(MyChangeEvent e);
}
