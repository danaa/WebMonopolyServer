package gameLogic.players;


import gameLogic.squares.SingleAsset;


/**
 * this class represents a computer player in a monopoly game.
 * computer player buys an asset if he has enough money for it.
 * he can't retire, 
 * @author Dana Akerman
 */
public class ComputerPlayer extends Player 
{
    
    // constants
    //----------------------------------------------------------------

    private static final String NAME = "comp";
    
    // statics
    //----------------------------------------------------------------
    
    private static int compCounter = 0;

    // c'tor
    //----------------------------------------------------------------
    
    /**
     * constructs a computer player
     * @param cash the amount of cash this player has
     * @throws IllegalArgumentException if cash is none-positive
     */
    public ComputerPlayer(int cash) 
    {
        super((NAME + (++compCounter)), cash);
    }

    // methods & functions
    //----------------------------------------------------------------
    
    @Override
    public boolean buyDecision(SingleAsset asset, int what) 
    {
        if (asset != null) 
        {
            if (_cash > asset.getCostPrice()) 
            {
                return true;
            }
            return false;
        } 
        else 
            throw new NullPointerException("asset is null");
    }

    //----------------------------------------------------------------
    
    @Override
    public boolean isHuman() {return false;}

    //---------------------------------------------------------------
    
    // resets the computer players counter
    public static void resetCompCounter() 
    {
        compCounter = 0;
    }
}
