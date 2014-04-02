package gameLogic.squares;


import gameLogic.Game;
import gameLogic.players.Player;
import monopoly.Event;
import monopoly.EventsFactory;


/**
 * this class represents a start square in a monopoly game
 * @author Dana Akerman
 */
public class StartSquare extends Square 
{
    
    // c'tor
    //-----------------------------------------------------------------------
    
    /**
     * constructs a start square
     * @param game the game this square belongs to
     * @throws NullPointerException if game is null
     */
    public StartSquare(Game game) 
    {
        super(game);
    }

    // methods
    //-----------------------------------------------------------------------
    
    @Override
    public void playerArrived(Player player) 
    {
        if (player != null) 
        {
            player.addCash(Game.STEP_START_CASH);

            // landed on start and payment events
            Event playerLandedOnStartEvent = EventsFactory.createLandedOnStartEvent(_game.getName(), player.getName());
            Event paymentEvent = EventsFactory.createPaymentToOrFromTreasuryEvent(_game.getName(), player.getName(), Game.STEP_START_CASH, false);
            _game.addEvent(playerLandedOnStartEvent);
            _game.addEvent(paymentEvent);
        } 
        else 
            throw new NullPointerException("player is null");
    }

    //-----------------------------------------------------------------------
    
    @Override
    public String toString(){return ("start");}
    
}
