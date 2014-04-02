package gameLogic.squares;


import gameLogic.Game;
import gameLogic.players.Player;
import monopoly.Event;
import monopoly.EventsFactory;


/**
 * this class represents a "go to jail" square in a monopoly game
 * @author Dana Akerman
 */
public class GoToJailSquare extends Square 
{

    // c'tor
    //-------------------------------------------------------------------
    
    /**
     * constructs a "go to jail" square
     * @param game the game this square belongs to
     * @throws NullPointerException if game is null
     */
    public GoToJailSquare(Game game) 
    {
        super(game);
    }
    
    //methods
    //-------------------------------------------------------------------

    @Override
    public void playerArrived(Player player) 
    {
        if (player != null) 
        {
            // go to jail and player moved events
            Event sentToJailEvent = EventsFactory.createGoToJailEvent(_game.getName(), player.getName());
            Event moveToJailEvent = EventsFactory.createPlayerMovedEvent(_game.getName(), player, Game.JAIL_OR_FREE_PASS_SQUARE, Game.TELEPORT_MOVE);
            _game.addEvent(sentToJailEvent);
            _game.addEvent(moveToJailEvent);

            player.setPosition(Game.JAIL_OR_FREE_PASS_SQUARE);
            player.setCanMove(false);
        } 
        else 
            throw new NullPointerException("player is null");
    }
}
