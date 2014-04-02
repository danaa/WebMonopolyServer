package gameLogic.cards;


import gameLogic.players.Player;
import gameLogic.squares.ActionSquare;


/**
 * this abstract class represents a card (warrant or surprise) in a monopoly game
 * @author Dana Akerman
 */
public abstract class Card 
{
    
    // data members
    //-----------------------------------------------------------------

    private String _text;
    protected int _type;

    // c'tor
    //-----------------------------------------------------------------
    
    /**
     * constructs a new action card, called only by its derived classes
     * @param type the type of the card, surprise(1) or warrant(-1)
     * @throws IllegalArgumentException if the given type is illegal 
     */
    protected Card(int type) 
    {
        this.setType(type);
    }

    // methods
    //-------------------------------------------------------------------
    
    /**
     * activates the card on the player according to the card properties
     * @param player the player to activate the card on
     * @throws NullPointerException if player is null
     */
    public abstract void doCard(Player player);

    //-------------------------------------------------------------------
    
    // override only in pardon card
    /**
     * checks if the card is a pardon card
     * @return true if its a pardon card, false otherwise
     */
    public boolean isPardonCard(){return false;}

    //-------------------------------------------------------------------
    
    /**
     * gets the text of the card
     * @return the text of the card
     */
    public String getText(){return _text;}

    //-------------------------------------------------------------------
    
    /**
     * sets the text of the card
     * @param text the text of the card
     * @throws NullPointerException if the given text is null
     */
    public void setText(String text) 
    {
        if (text != null) 
        {
            _text = text;
        } 
        else
            throw new NullPointerException("text is null");
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the type of the card to surprise(1) or warrant(-1)
     * @param type the type of the card, surprise(1) or warrant(-1)
     * @throws IllegalArgumentException object if the given type is illegal 
     */
    public final void setType(int type) 
    {
        if (type == ActionSquare.SURPRISE || type == ActionSquare.WARRANT) 
        {
            _type = type;
        } 
        else 
            throw new IllegalArgumentException("illegal type input");
    }

    //-------------------------------------------------------------------
    
    /**
     * gets the type of the card
     * @return the type of the card
     */
    public int getType(){return _type;}

    //-------------------------------------------------------------------
    
    @Override
    public String toString(){return _text;}
}
