package gameLogic.innerEvents;


import java.util.EventObject;


/**
 * this class represents a change event
 * @author Dana Akerman
 */
public class MyChangeEvent extends EventObject 
{

    // constants
    //--------------------------------------------------------------
    
    private static final long serialVersionUID = 1L;
    
    // data members
    //--------------------------------------------------------------
    
    private String _message;

    // c'tor
    //--------------------------------------------------------------
   
    /**
     * constructs a new change event
     * @param source the source object of the event
     * @param message a description of the event
     */
    public MyChangeEvent(Object source, String message) 
    {
        super(source);
        _message = message;
    }

    // methods
    //--------------------------------------------------------------
    
    /**
     * gets the message
     * @return the message
     */
    public String getMessege(){return _message;}
}