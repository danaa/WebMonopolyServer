package gameLogic.squares;


import java.util.ArrayList;
import java.util.Random;
import gameLogic.Game;
import gameLogic.cards.*;
import gameLogic.players.Player;
import monopoly.Event;
import monopoly.EventsFactory;


/**
 * this class represents an action square (warrant or surprise) in a monopoly game
 * @author Dana Akerman
 */
public class ActionSquare extends Square 
{
    
    // constants
    //-------------------------------------------------------------------

    public static final int SURPRISE = 1;
    public static final int WARRANT = -1;
    
    // data members
    //-------------------------------------------------------------------
    
    private ArrayList<Card> _cards;
    private int _type;

    // c'tor
    //-------------------------------------------------------------------
    
    /**
     * constructs a new ActionSquare
     * @param type the type of the ActionSquare, surprise(1) or warrant{-1)
     * @param game the game this square belongs to
     * @throws IllegalArgumentException if the type is illegal
     * @throws NullPointerException if 'game' is null
     */
    public ActionSquare(int type, Game game) 
    {
        super(game);
        _cards = new ArrayList<Card>();
        this.setType(type);
    }

    // methods
    //-------------------------------------------------------------------
    
    /**
     * returns the game this square belongs to
     * @return the game this square belongs to
     */
    public Game getGame(){return _game;}

    //-------------------------------------------------------------------
    
    /**
     * returns the type of the ActionSquare, , surprise(1) or warrant{-1)
     * @return the type of the ActionSquare, , surprise(1) or warrant{-1)
     */
    public int getType() {return _type;}

    //-------------------------------------------------------------------
    
    /**
     * sets the type of the ActionSquare
     * @param type the type of the ActionSquare, , surprise(1) or warrant{-1)
     * @throws IllegalArgumentException if 'type' is illegal
     */
    public final void setType(int type) 
    {
        if (type == SURPRISE || type == WARRANT) 
        {
            _type = type;
        } 
        else 
            throw new IllegalArgumentException("illegal type input");
    }

    //-------------------------------------------------------------------
    
    /**
     * initializes the action square cards from the XMLcards Cards object created from the 
     * schema generated class 'Cards', and adds them to the square's deck
     * @param XMLcards the XMLcards object created from the schema generated class 'Cards'
     * @throws NullPointerException if 'XMLcards' is null
     */
    public void init(generated.Cards XMLcards) 
    {
        if (XMLcards == null) 
            throw new NullPointerException("XMLcards is null");

        // init "go to" cards
        generated.Gotos XMLgotos;

        if (_type == SURPRISE) 
        {
            XMLgotos = XMLcards.getSurprises().getGotos();
        } 
        else // WARRANT
        {
            XMLgotos = XMLcards.getWarrants().getGotos();
        }

        int gotoSize = XMLgotos.getSize();

        for (int i = 0; i < gotoSize; i++) 
        {
            generated.Goto XMLgotoCard = XMLgotos.getGoto().get(i);
            GotoCard myGotoCard = new GotoCard(_type, _game);
            myGotoCard.init(XMLgotoCard);
            this.addCard(myGotoCard);
        }

        // init "finance" cards
        generated.Finances XMLfinances;
        if (_type == SURPRISE) 
        {
            XMLfinances = XMLcards.getSurprises().getFinances();
        } 
        else // WARRANT
        {
            XMLfinances = XMLcards.getWarrants().getFinances();
        }

        int financesSize = XMLfinances.getSize();

        for (int i = 0; i < financesSize; i++) 
        {
            generated.Finance XMLfinanceCard = XMLfinances.getFinance().get(i);
            FinancialCard myFinanceCard = new FinancialCard(_type, _game);
            myFinanceCard.init(XMLfinanceCard);
            this.addCard(myFinanceCard);
        }

        // init "pardon" cards
        if (_type == SURPRISE) 
        {
            generated.Pardons XMLpardons = XMLcards.getSurprises().getPardons();
            int pardonSize = XMLpardons.getSize();
            
            for (int i = 0; i < pardonSize; i++) 
            {
                generated.Pardon XMLpardonCard = XMLpardons.getPardon().get(i);
                PardonCard myPardonCard = new PardonCard(this);
                myPardonCard.init(XMLpardonCard);
                this.addCard(myPardonCard);
            }
        }

        this.mixCards();
    }

    //-------------------------------------------------------------------
    
    @Override
    public void playerArrived(Player player) 
    {
        if (player == null) 
            throw new NullPointerException("player is null");

        
        Card card = _cards.remove(0);

        // we return the card to the end of the deck if its not a pardon card
        if (!(card.isPardonCard())) 
        {
            _cards.add(card);
        } 
        else // its a pardon card, we need to create an event
        {
            Event pardonCardEvent = EventsFactory.createGetOutOfJailCardEvent(_game.getName(), player.getName());
            _game.addEvent(pardonCardEvent);
        }

        if (card.getType() == SURPRISE) 
        {
            Event surpriseCardEvent = EventsFactory.createSurpriseCardEvent(_game.getName(), player.getName(), card.getText());
            _game.addEvent(surpriseCardEvent);
        } 
        else // warrant
        {
            Event warrantCardEvent = EventsFactory.createWarrantCardEvent(_game.getName(), player.getName(), card.getText());
            _game.addEvent(warrantCardEvent);
        }

        card.doCard(player);

    }

    //-------------------------------------------------------------------
    
    /**
     * adds a card to the cards ArrayList
     * @param card the card to add
     * @throws nullPoinerException if 'card' is null
     */
    public void addCard(Card card) 
    {
        if (card != null) 
        {
            _cards.add(card);
        } 
        else 
            throw new NullPointerException("card is null");
    }

    //-------------------------------------------------------------------
    
    /**
     * mixes the cards
     */
    private void mixCards() 
    {
        Random generator = new Random();

        for (int i = 0; i < 100; i++) 
        {
            int pos1 = generator.nextInt(_cards.size());
            int pos2 = generator.nextInt(_cards.size());

            if (pos1 != pos2) 
            {
                this.swapCards(pos1, pos2);
            }
        }
    }

    //-------------------------------------------------------------------
    
    /**
     * gets two positions in the ArrayList, swaps the cards in those positions
     * @param pos1 the first position
     * @param pos2 the second position
     */
    private void swapCards(int pos1, int pos2) 
    {
        Card temp = _cards.get(pos1);
        _cards.set(pos1, _cards.get(pos2));
        _cards.set(pos2, temp);
    }

    //-------------------------------------------------------------------
    
    /**
     * gets the card on the top of the deck
     * @return the card on the top of the deck, or null if the deck is empty
     */
    public Card getCardOnTop() 
    {
        if (_cards.size() > 0) 
        {
            return _cards.get(0);
        } 
        else
        {
            return null;
        }
    }

    //-------------------------------------------------------------------
    
    @Override
    public String toString() 
    {
        if (_type == SURPRISE) 
        {
            return ("surprise");
        } 
        else // warrant
        {
            return ("warrant");
        }
    }
}
