package gameLogic.squares;


import gameLogic.Game;
import gameLogic.players.Player;


/**
 * this abstract class represents a square in a monopoly game
 * @author Dana Akerman
 */
abstract public class Square
{
    // data members
    //---------------------------------------------------------
    
    Game _game;
    
    // c'tor
    //---------------------------------------------------------
    
    /**
     * constructs a new square
     * @param game the game this square belongs to
     * @throws NullPointerException if game is null
     */
    public Square(Game game)
    {
        this.setGame(game);
    }
    
    //methods
    //---------------------------------------------------------
    
    /**
     * set the game this square belongs to
     * @param game the game this square belongs to
     * @throws NullPointerException if game is null
     */
    public final void setGame(Game game)
    {
        if(game != null)
        {
            _game = game;
        }
        else
            throw new NullPointerException("game is null");
    }
    
    //---------------------------------------------------------
    
    // override only in parking and jail
    /**
     * checks if the player can roll the dice
     * @param player the player to check
     * @return true if the player can roll the dice
     * @throws NullPointerException if 'player' is null
     */
    public boolean shouldPlayerRollDice(Player player) 
    {
        if (player != null) 
        {
            return true;
        }
        else
            throw new NullPointerException("player is null");
    }

    //---------------------------------------------------------
    
    // override only in jail
    /**
     * checks if the player can move
     * @param player the player to check
     * @param dice1 the first dice
     * @param dice2 the second dice
     * @throws NullPointerException if 'player' is null
     */
    public boolean shouldPlayerMove(Player player, int dice1, int dice2) 
    {
        if (player != null) 
        {
            return true;
        } 
        else 
            throw new NullPointerException("player is null");
    }

    //---------------------------------------------------------
    
    /**
     * performing actions on the player that step on the square according to
     * the type of the square
     * @param player the player that stepped on the square
     * @throws NullPointerException if 'player' is null
     */
    public abstract void playerArrived(Player player);
}