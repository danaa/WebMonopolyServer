package gameLogic.cards;


import gameLogic.Game;


/**
 * this class represents an action card which is a financial card or a "go to" card
 * of surprise or warrant, in a monopoly game
 * this is an abstract class
 * @author Dana Akerman
 */
public abstract class ActionCard extends Card 
{
    // data members
    //-------------------------------------------------------------------

    protected Game _game;

    // c'tor
    //-------------------------------------------------------------------
    
    /**
     * constructs a new action card, called only by its derived classes
     * @param type warrant(-1) or surprise(1)
     * @param game the game this card belongs to
     * @throws IllegalArgumentException if the given type is illegal 
     * @throws NullPointerException if 'game' is null
     */
    protected ActionCard(int type, Game game) 
    {
        super(type);
        this.setGame(game);
    }

    // methods
    //-------------------------------------------------------------------
    
    /**
     * sets the game data member to the given game
     * @param game - the game this card belongs to
     * @throws NullPointerException if 'game' is null
     */
    public final void setGame(Game game) 
    {
        if (game != null) 
        {
            _game = game;
        } 
        else 
            throw new NullPointerException("game is null");
    }
}
