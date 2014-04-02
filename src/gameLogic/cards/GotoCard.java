package gameLogic.cards;

import gameLogic.Game;
import gameLogic.players.Player;
import gameLogic.squares.ActionSquare;
import gameLogic.squares.Square;
import monopoly.Event;
import monopoly.EventsFactory;


/**
 * this card represents a "go to" card (surprise or warrant) in a monopoly game
 * @author Dana Akerman
 */
public class GotoCard extends ActionCard 
{

    // constants
    //-------------------------------------------------------------------
    
    private static final int START = 0;
    private static final int NEXT = 1; // the next square of the same type
    private static final int JAIL = 2;
    
    // data members
    //-------------------------------------------------------------------
    
    private int _target;

    // c'tor
    //-------------------------------------------------------------------
    
    /**
     * creates a new go to card
     * @param type the type of the card, surprise(1) or warrant(-1)
     * @param game the game this card belongs to
     * @throws IllegalArgumentException if the given type is illegal 
     * @throws NullPointerException if 'game' is null
     */
    public GotoCard(int type, Game game) 
    {
        super(type, game);
    }

    // methods
    //-------------------------------------------------------------------
    
    /**
     * initializes the go to card from the XMLgotoCard Goto object created from the 
     * schema generated class 'Goto'
     * @param XMLgotoCard the XMLgotoCard Goto object created from the schema generated class 'Goto'
     * @throws NullPointerException if 'XMLgotoCard' is null 
     */
    public void init(generated.Goto XMLgotoCard) 
    {
        if (XMLgotoCard == null) 
            throw new NullPointerException("XMLgotoCard is null");

        setTarget(XMLgotoCard.getTarget().ordinal());
        this.setText(XMLgotoCard.getText());
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the target of the card (destination square), must be START, NEXT or JAIL
     * @param target the target of the card
     * @throws IllegalArgumentException if target is not one of the above
     */
    public void setTarget(int target) 
    {
        if (target >= START && target <= JAIL) 
        {
            _target = target;
        } 
        else 
            throw new IllegalArgumentException("illegal target input");
    }

    //-------------------------------------------------------------------
    
    @Override
    public void doCard(Player player) 
    {
        if (player == null)
            throw new NullPointerException("player is null");
        

        switch (_target) 
        {
            case START:
                
                // move event
                Event playerMoveToStartEvent =
                        EventsFactory.createPlayerMovedEvent(_game.getName(), player, Game.START_SQUARE, Game.TELEPORT_MOVE);
                _game.addEvent(playerMoveToStartEvent);

                player.setPosition(Game.START_SQUARE);
                _game.getSquareByIndex(Game.START_SQUARE).playerArrived(player);
                break;

            case JAIL:
                
                // move and jail events
                Event playerSentToJailEvent 
                        = EventsFactory.createGoToJailEvent(_game.getName(), player.getName());
                Event playerMoveToJailEvent 
                        = EventsFactory.createPlayerMovedEvent(_game.getName(), player, Game.JAIL_OR_FREE_PASS_SQUARE, Game.TELEPORT_MOVE);
                _game.addEvent(playerSentToJailEvent);
                _game.addEvent(playerMoveToJailEvent);

                player.setPosition(Game.GO_TO_JAIL_SQUARE);
                _game.getSquareByIndex(Game.GO_TO_JAIL_SQUARE).playerArrived(player);
                break;

            case NEXT:

                boolean moved = false;
                int playerPos = player.getPosition();
                Square curr = _game.getSquareByIndex(playerPos);

                for (int i = playerPos + 1; moved == false; i++) 
                {
                    i = i % Game.BOARD_SIZE;
                    
                    // if its a surprise and we passed start player gets money
                    if (_type == ActionSquare.SURPRISE) 
                    {
                        // if we passed start
                        if (_game.getSquareByIndex(i) == _game.getSquareByIndex(Game.START_SQUARE)) 
                        {
                            _game.passedStartOnTheWay(player);
                        }
                    }
                    
                    // go to the closest surprise/warrant square
                    if (_game.getSquareByIndex(i) == curr) 
                    {
                        // move event
                        Event playerMoveToNextEvent = 
                                EventsFactory.createPlayerMovedEvent(_game.getName(), player, i, Game.TELEPORT_MOVE);
                        _game.addEvent(playerMoveToNextEvent);

                        player.setPosition(i);
                        _game.getSquareByIndex(i).playerArrived(player);
                        moved = true;
                    }
                }

            default:
                
                break;

        }
    }
}
