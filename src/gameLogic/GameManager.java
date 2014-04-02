package gameLogic;


import gameLogic.innerEvents.MyChangeEvent;
import gameLogic.innerEvents.MyChangeListener;
import gameLogic.players.HumanPlayer;
import gameLogic.players.Player;
import java.util.ArrayList;
import monopoly.Event;
import monopoly.EventsFactory;


/**
 * this class represents a game manager in a monopoly web service
 * this class is implemented as a singleton
 * @author Dana Akerman
 */
public class GameManager implements MyChangeListener
{
    
    // statics
    //--------------------------------------------------------------
    
    private static GameManager instance;
    
    // data members
    //--------------------------------------------------------------
    
    private Game _game;

    // c'tor
    //--------------------------------------------------------------
    
    /**
     * constructs a new game manager, available only in this class
     */
    private GameManager() 
    {
        _game = null;
    }
    
    // functions & methods
    //--------------------------------------------------------------
    
    /**
     * gets the game manager
     * @return the game manager
     */
    public static synchronized GameManager getInstance()
    {
        if(instance == null)
        {
            instance = new GameManager();
        }
        return instance;
    }
    
    //--------------------------------------------------------------
    
    /**
     * starts a new game with the given parameters
     * @param XmlFileName the configuration xml file name
     * @param gameName the name of the game
     * @param humanPlayers number of human players
     * @param computerizedPlayers number of computer players
     * @param useAutomaticDiceRoll is the dice roll automatic or not
     */
    public void startGame(String XmlFileName, String gameName, int humanPlayers, int computerizedPlayers, boolean useAutomaticDiceRoll)
    {
        try
        {
            _game = new Game(XmlFileName, gameName, humanPlayers, computerizedPlayers, useAutomaticDiceRoll);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * check if a game exists
     * @return true if a game exists
     */
    public boolean isGameExists(){return _game != null;}
    
    //--------------------------------------------------------------
    
    /**
     * checks if the current game is active
     * @return true if the game is active
     */
    public boolean isGameActive()
    {
        if(_game != null)
        {
            return _game.isActive();
        }
        else 
        {
            return false;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets the current game name
     * @return the game name or null if game doesn't exist
     */
    public String getGameName()
    {
        if(_game != null)
        {
            return _game.getName();
        }
        else
        {
            return null;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets the current game status
     * @return the current game status or null if game doesnt exist
     */
    public String getGameStatus()
    {
        if(_game != null)
        {
            return _game.getStatus();
        }
        else
        {
            return null;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets the total number of human players in the current game
     * @return the total number of human players in the current game or -1 if game doesn't exist
     */
    public int getGameTotalHumanNum()
    {
        if(_game != null)
        {
            return _game.getTotalHumanNum();
        }
        else
        {
            return -1;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets the current number of human players in the current game
     * @return the current number of human players in the current game or -1 if game doesn't exist
     */
    public int getGameCurHumanNum()
    {
        if(_game != null)
        {
            return _game.getCurHumanNum();
        }
        else
        {
            return -1;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets the number of computerized players in the current game
     * @return the number of computerized players in the current game
     */
    public int getGameCompNum(){return _game.getCompNum();}
    
    //--------------------------------------------------------------
    
    /**
     * check if auto dice is active in the current game
     * @return true if auto dice is active in the current game
     */
    public boolean getGameAutoDice(){return _game.isAutoDice();}
    
    //--------------------------------------------------------------
    
    /**
     * checks if the game is full
     * @return true if the game is full
     */
    public boolean isGameFull(){return _game.isFull();}
    
    //--------------------------------------------------------------
    
    /**
     * checks if the given name is identical to the current game name
     * @param gameName the game name to check
     * @return true if the names are identical
     */
    public boolean isGameSameName(String gameName)
    {
        boolean val = false;
        
        try
        {
            val = _game.getName().equals(gameName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return val;
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets a player with the given name in the current game
     * @param playerName the name of the player to get
     * @return the player with the given name or null if doesn't exist
     */
    public Player getPlayerByName(String playerName)
    {
        Player p = null;
        
        try
        {
            p = _game.getPlayerByName(playerName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return p;
    }
    
    //--------------------------------------------------------------
    
    /**
     * adds a human player with the given name to the current game
     * @param name the name of the player to add
     * @return a unique id to the player in this specific game or -1 if something went wrong
     */
    public int addPlayerToGame(String name)
    {
       int id = -1;
       
       try
       {
           id = _game.addPlayer(name);
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
       
       return id;
    }
    
    //--------------------------------------------------------------
    
    /**
     * runs the current game, does nothing if something went wrong
     */
    public void runGame()
    {
        try
        {
            Thread gameThread = new Thread(_game);
            gameThread.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * "erases" the current game if it's over
     */
    public void stopGame()
    {
        if(_game != null && _game.isGameOver())
        {
            EventsFactory.resetEventIdGenerator();
            _game = null;
        }
    }

    //--------------------------------------------------------------
    
    /**
     * get the players of the current game
     * @return the players of the current game or null if the game doesn't exist
     */
    public ArrayList<Player> getGamePlayers()
    {
        if(_game != null)
        {
            return _game.getPlayers();
        }
        else
        {
            return null;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets the events in the current game
     * @return the events in the current game or null if the game doesn't exist
     */
    public ArrayList<Event> getGameEvents()
    {
        if(_game != null)
        {
            return _game.getEvents();
        }
        else
        {
            return null;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets the last event in the current game
     * @return the last event in the current game or null if the game doesn't exist
     */
    public Event getLastGameEvent()
    {
        if(_game != null)
        {
            return _game.getLastEvent();
        }
        else
        {
            return null;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * gets a human player by his id
     * @param id the id of the player to get
     * @return the player with the given id of null if the player or game doesn't exist
     */
    public HumanPlayer getGamePlayerById(int id)
    {
        if(_game != null)
        {
            return _game.getPlayerByID(id);
        }
        else
        {
            return null;
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * sets the dice in the current game
     * @param dice1 the first dice
     * @param dice2 the second dice
     */
    public void setGameDice(int dice1, int dice2)
    {
        if(_game != null)
        {
            _game.setDice(dice1, dice2);
        }
    }
    
    //--------------------------------------------------------------
    
    /**
     * stops the timer of the current game
     */
    public void stopGameTimer()
    {
        if (_game != null)
        {
            _game.stopTimer();
        }
    }
    
    //--------------------------------------------------------------

    @Override
    public void changeEventRecieved(MyChangeEvent e) 
    {
        if(e == null)
            throw new NullPointerException("event is null");
        
        if(e.getMessege().equals("game over"))
        {
            stopGame();
        }
    }
   
}
