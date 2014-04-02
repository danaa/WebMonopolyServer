package gameLogic.squares;


import gameLogic.Game;
import gameLogic.players.Player;
import monopoly.Event;
import monopoly.EventsFactory;


/**
 * this class represents jail/free pass square in a monopoly game
 * @author Dana Akerman
 */
public class JailOrFreePassSquare extends Square 
{
    
    // c'tor
    //-------------------------------------------------------------------
    
    /**
     * constructs a "jail/free pass" square
     * @param game the game this square belongs to
     * @throws NullPointerException if game is null
     */
    public JailOrFreePassSquare(Game game) 
    {
        super(game);
    }

    // methods
    //-------------------------------------------------------------------
    
    @Override
    public void playerArrived(Player player){} // not implemented

    //-------------------------------------------------------------------
    
    @Override
    public boolean shouldPlayerMove(Player player, int dice1, int dice2) 
    {
        if (player == null) 
            throw new NullPointerException("player is null");

        if (player.canPlayerMove()) 
        {
            return true;
        } 
        else if (dice1 == dice2) //double
        {
            player.setCanMove(true);
        } 
        else 
        {
            player.setCanMove(false);
        }
        return false;
    }

    //-------------------------------------------------------------------
    
    @Override
    public boolean shouldPlayerRollDice(Player player) 
    {
        
        if (player == null) 
            throw new NullPointerException("player is null");
 

        if (!player.canPlayerMove() && player.hasPardonCard()) 
        {
            if (player.pardonCardDecision()) // uses the pardon card
            {

                player.getPardonCard().returnToDeck();
                player.setPardonCard(null);
                player.setCanMove(true);

                // used pardon card event
                Event playerUsedPardonCardEvent = 
                        EventsFactory.createPlayerUsedPardonCardEvent(_game.getName(), player.getName());
                _game.addEvent(playerUsedPardonCardEvent);
            }
        }
        return true;
    }

    //-------------------------------------------------------------------
    
    @Override
    public String toString() 
    {
        return ("free pass/jail");
    }
}
