package gameLogic.players;


import gameLogic.Game;
import gameLogic.squares.SingleAsset;
import monopoly.Event;
import monopoly.EventsFactory;


/**
 * this class represents a human player in a monopoly web service
 * @author Dana Akerman
 */
public class HumanPlayer extends Player 
{
    // data members
    //---------------------------------------------------------------
    
    private int _playerID;
    private boolean _resign;
    private Game _game;
    private boolean _decision;
    
    //---------------------------------------------------------------
    
    /**
     * constructs a human player that represents a client
     * @param name the name of the player
     * @param id the id of the player
     * @param cash the initial cash amount
     * @param game the game this player belongs to
     * @throws NullPointerException if name or game are null
     * @throws IllegalArgumentException if cash is illegal
     */
    public HumanPlayer(String name, int id, int cash, Game game)
    {
        super(name, cash);
        this.setGame(game);
        _resign = false;
        _decision = false;
        _playerID = id;
    }
    
    //---------------------------------------------------------------

    @Override
    public boolean isHuman(){return true;}
    
    //---------------------------------------------------------------
    
    /**
     * gets the id of the player
     * @return the id of the player
     */
    public int getID(){return _playerID;}
    
    //---------------------------------------------------------------
    
    /**
     * checks if the user resigned
     * @return 
     */
    public boolean isResign(){return _resign;}
    
     //---------------------------------------------------------------
    
    /**
     * sets the resign flag
     * @param val the boolean value to set
     */
    public synchronized void setResign(boolean val)
    {
        _resign = val;
    }
    
    //---------------------------------------------------------------
    
    /**
     * sets the game
     * @param game the game this player belongs to
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
    
    //---------------------------------------------------------------
    
    /**
     * sets the decision of the player
     * @param val the decision
     */
    public synchronized void setDesicion(boolean val)
    {
        _decision = val;
    }
    
    //---------------------------------------------------------------
    
    /**
     * askes the player for dice input
     */
    public void playerRollDice()
    {
        // prompt dice roll event
        Event promptDiceRollEvent 
                = EventsFactory.createPromptDiceRollEvent(_game.getName(), _name, 30);
        _game.addEvent(promptDiceRollEvent);
        _game.startTimer(Game.PROMPT_TIMER_SECS);

        // wait until client answers or timer expire
        synchronized (this) 
        {
            try 
            {
                this.wait();
            } 
            catch (InterruptedException ex) 
            {
                ex.printStackTrace();
            }
        }
    }
    
     //---------------------------------------------------------------

    @Override
    public boolean buyDecision(SingleAsset asset, int what) 
    {
        if(asset == null)
            throw new NullPointerException("asset is null");
		
	int cost = asset.getCostPrice();
		
	if(_cash >= cost && !_resign)
	{
            //return UserInterface.getBuyDecision(asset, cost);
            if(what == SingleAsset.ASSET)
            {
                Event promptBuyAssetEvent
                        = EventsFactory.createPromptBuyAssetEvent(_game.getName(), this, Game.PROMPT_TIMER_SECS);
                _game.addEvent(promptBuyAssetEvent);
            }
            else // house
            {
                Event promptBuyHouseEvent
                        = EventsFactory.createPromptBuyHouseEvent(_game.getName(), this, Game.PROMPT_TIMER_SECS);
                _game.addEvent(promptBuyHouseEvent);
            }
            _game.startTimer(Game.PROMPT_TIMER_SECS);
            
            // stop game
            // the game will be notified if timer expires or stops
            synchronized(this)
            {
                try 
                {
                    this.wait();
                } 
                catch (InterruptedException ex) 
                {
                    ex.printStackTrace();
                }
            }
            
            return _decision;
	}
	else
	{
            return false;
        }
      
    }
    
    
}
