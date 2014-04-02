package gameLogic.cards;


import gameLogic.players.Player;
import gameLogic.squares.ActionSquare;


/**
 * this card represents a pardon card in a monopoly game
 * @author Dana Akerman
 */
public class PardonCard extends Card 
{
    
    // data members
    //---------------------------------------------------------------------

    private ActionSquare _square;

    // c'tor
    //---------------------------------------------------------------------
    
    /**
     * constructs a pardon card
     * @param square the ActionSquare this card belongs to
     */
    public PardonCard(ActionSquare square) 
    {
        super(ActionSquare.SURPRISE); // the pardon card belongs to surprise only
        this.setSquare(square);
    }

    // methods
    //---------------------------------------------------------------------
    
    /**
     * inits a pardon card from the XMLpardonCard object created from the schema generated class Pardon
     * @param XMLpardonCard the XMLpardonCard object created from the schema generated class Pardon
     * @throws NullPointerException if 'XMLpardonCard' is null
     */
    public void init(generated.Pardon XMLpardonCard) 
    {
        if (XMLpardonCard != null) 
        {
            this.setText(XMLpardonCard.getText());
        } 
        else 
            throw new NullPointerException("XMLpardonCard is null");
    }

    //---------------------------------------------------------------------
    
    /**
     * sets the square of this card
     * @param square the ActionSquare this card belongs to
     * @throws NullPointerException if the given square is null
     */
    public final void setSquare(ActionSquare square) 
    {
        if (square != null) 
        {
            _square = square;
        } 
        else 
            throw new NullPointerException("square is null");
    }

    //---------------------------------------------------------------------
    
    @Override
    public void doCard(Player player) 
    {
        if (player != null) 
        {
            player.setPardonCard(this);
        } 
        else 
            throw new NullPointerException("player is null");
        
    }

    //---------------------------------------------------------------------
    
    /**
     * adds the pardon card to the end of the deck 
     */
    public void returnToDeck() 
    {
        _square.addCard(this);
    }

    //---------------------------------------------------------------------
    
    @Override
    public boolean isPardonCard(){return true;}
}
