package gameLogic.cards;


import gameLogic.Game;
import gameLogic.players.Player;
import gameLogic.squares.ActionSquare;
import monopoly.Event;
import monopoly.EventsFactory;

/**
 * this class represents a financial card (warrant or surprise) in a monopoly game
 * @author Dana Akerman
 */
public class FinancialCard extends ActionCard 
{

    // constants
    //-------------------------------------------------------------------
    
    private static final int OTHERS = 0;
    private static final int TREASURY = 1;
    
    // data members
    //-------------------------------------------------------------------
    
    private int _onWho; // flag
    private int _amount;

    //-------------------------------------------------------------------
    
    // c'tor
    /**
     * creates a new financial card
     * @param type the type of the card, surprise(1) or warrant(-1)
     * @param game the game this card belongs to
     * @throws IllegalArgumentException if the given type is illegal 
     * @throws NullPointerException if 'game' is null
     */
    public FinancialCard(int type, Game game) 
    {
        super(type, game);
    }

    //-------------------------------------------------------------------
    
    /**
     * initializes the financial card from the XMLfinanceCard Finance object created from the 
     * schema generated class 'Finance'
     * @param XMLfinanceCard the XMLfinanceCard Finance object created from the schema generated class 'Finance'
     * @throws NullPointerException if 'XMLfinanceCard' is null 
     */
    public void init(generated.Finance XMLfinanceCard) 
    {
        if (XMLfinanceCard == null) 
        {
            throw new NullPointerException("XMLfinanceCard is null");
        }
        
        this.setText(XMLfinanceCard.getText());
        this.setOnWho(XMLfinanceCard.getOnWho().ordinal());
        this.setAmount(XMLfinanceCard.getAmount());
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the "on who" flag
     * @param onWho who this card operates on, may be TREASURY or OTHRES (other players)
     * @throws IllegalArgumentException if 'onWho' is not TREASURY or OTHRES
     */
    public void setOnWho(int onWho) 
    {
        if (onWho == TREASURY || onWho == OTHERS) 
        {
            _onWho = onWho;
        } 
        else 
            throw new IllegalArgumentException("illegal onWho input");
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the amount of cash in the card
     * @param amount the amount of cash in the card
     * @throws IllegalArgumentException if 'amount' is non-positive
     */
    public void setAmount(int amount) 
    {
        if (amount > 0) 
        {
            _amount = amount;
        } 
        else 
            throw new IllegalArgumentException("illegal amount input");
    }

    //-------------------------------------------------------------------
    
    @Override
    public void doCard(Player player) 
    {
        if (player == null) 
            throw new NullPointerException("player is null");
  
        
        switch (_onWho) 
        {
            case TREASURY:
                
                if (_type == ActionSquare.SURPRISE)
                {
                    player.addCash(_amount);
                    
                    // payment event
                    Event paymentEvent 
                            = EventsFactory.createPaymentToOrFromTreasuryEvent(_game.getName(), player.getName(), _amount, false);
                    _game.addEvent(paymentEvent);
                } 
                else // WARRANT
                {
                    int payment = player.reduceCash(_amount);
                    
                    if (payment > 0) 
                    {
                        // payment event
                        Event paymentEvent 
                                = EventsFactory.createPaymentToOrFromTreasuryEvent(_game.getName(), player.getName(), payment, true);
                        _game.addEvent(paymentEvent);
                    }
                }
                break;
            
            case OTHERS:
                
                boolean paymentPossible = true;
                
                if (_type == ActionSquare.SURPRISE) 
                {
                    for (int i = 0; i < _game.getTotalNumPlayers() && paymentPossible; i++) 
                    {
                        Player curr = _game.getPlayerByIndex(i);
                        
                        if (curr != null && curr != player) 
                        {
                            if (curr.isInGame()) 
                            {
                                int payment = curr.reduceCash(_amount);
                                
                                if (payment > 0) 
                                {
                                    player.addCash(payment);
                                    
                                    // payment event
                                    Event paymentEvent 
                                            = EventsFactory.createPaymentToOrFromPlayerEvent(_game.getName(), player.getName(), curr.getName(), payment, false);
                                    _game.addEvent(paymentEvent);
                                } 
                                else 
                                {
                                    paymentPossible = false;
                                }
                            }
                        }
                    }
                } 
                else // WARRANT
                {
                    for (int i = 0; i < _game.getTotalNumPlayers() && paymentPossible; i++) 
                    {
                        Player curr = _game.getPlayerByIndex(i);
                        
                        if (curr != null && curr != player) 
                        {
                            if (player.isInGame() && curr.isInGame()) 
                            {
                                int payment = player.reduceCash(_amount);
                                
                                if (payment > 0) 
                                {
                                    curr.addCash(payment);
                                    
                                    // payment event
                                    Event paymentEvent 
                                            = EventsFactory.createPaymentToOrFromPlayerEvent(_game.getName(), player.getName(), curr.getName(), payment, true);
                                    _game.addEvent(paymentEvent);
                                } 
                                else 
                                {
                                    paymentPossible = false;
                                }
                            }
                        }
                    }
                }
                break;
            
            default:
                break;
        }        
    }
}
