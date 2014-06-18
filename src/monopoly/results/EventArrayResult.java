package monopoly.results;


import monopoly.Event;


/**
 * @author Dana Akerman
 */
public class EventArrayResult extends MonopolyResult
{

    private static final Event[] EMPTY_RESULTS = new Event[0];

    private Event[] results;

    public EventArrayResult(Event... results) 
    {
        super ();
        this.results = results;
    }

    public EventArrayResult(String errorMessage) 
    {
        super (errorMessage);
    }

    public EventArrayResult(boolean isSuccessfull, String errorMessage, Event[] results) 
    {
        super (isSuccessfull, errorMessage);
        this.results = results;
    }

    public Event[] getResults() 
    {
        return results == null ? EMPTY_RESULTS : results;
    }

    public static EventArrayResult error(String message) 
    {
        return new EventArrayResult(message);
    }
}
