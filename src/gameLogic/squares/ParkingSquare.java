package gameLogic.squares;


import gameLogic.Game;
import gameLogic.players.Player;


/**
 * this class represents a parking square in a monopoly game
 * @author Dana Akerman
 */
public class ParkingSquare extends Square 
{
    
    // c'tor
    //---------------------------------------------------------------------
    
    /**
     * constructs a parking square
     * @param game the game this square belongs to
     * @throws NullPointerException if game is null
     */
    public ParkingSquare(Game game) 
    {
        super(game);
    }
    
    // methods
    //---------------------------------------------------------------------

    @Override
    public void playerArrived(Player player) 
    {
        if (player != null) 
        {
            player.setCanMove(false);
        } 
        else 
            throw new NullPointerException("player is null");
    }

    //---------------------------------------------------------------------
    
    @Override
    public boolean shouldPlayerRollDice(Player player) 
    {
        if (player != null) 
        {
            if (!(player.canPlayerMove())) 
            {
                player.setCanMove(true);
                return false;
            }
            return true;
        } 
        else 
            throw new NullPointerException("player is null");
    }

    //---------------------------------------------------------------------
    
    @Override
    public String toString() 
    {
        return ("parking");
    }
}
