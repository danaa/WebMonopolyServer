package gameLogic;


import gameLogic.groups.*;
import gameLogic.innerEvents.EventGenerator;
import gameLogic.players.*;
import gameLogic.squares.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import monopoly.Event;
import monopoly.EventsFactory;
import monopoly.MonopolyUtils;

/**
 * this class represents a single monopoly game in a monopoly web service
 * it can be run on a different thread
 * when the game is over it notifies the game manager so it can "delete" it
 * @author Dana Akerman
 */
public class Game extends EventGenerator implements Runnable
{
    // constants
    //------------------------------------------------------
    
    public static final String ACTIVE = "ACTIVE";
    public static final String WAIT = "WAIT";
    public static final int BOARD_SIZE = 36;
    public static final int MAX_NUM_PLAYERS = 6;
    public static final int MIN_NUM_PLAYERS = 2;
    public static final int PROMPT_TIMER_SECS = 120; // 2 minutes due to slow animetion in client
    
    public static final int START_SQUARE = 0;
    public static final int JAIL_OR_FREE_PASS_SQUARE = 9;
    public static final int PARKING_SQUARE = 18;
    public static final int GO_TO_JAIL_SQUARE = 27;
    
    public static final int DEFAULT_CASH = 1500;
    public static final int PASSED_START_CASH = 200;
    public static final int STEP_START_CASH = 400;
    
    public static final int UTILS = 0;
    public static final int TRANSPORTATION = 1;
    public static final int NUM_SIMPLE_ASSETS = 2; // utils and transportation
    
    // move types
    public static final String REGULAR_MOVE = "regular";
    public static final String TELEPORT_MOVE = "teleport";
    
    // pauses
    public static final int START_GAME_PAUSE = 3000;
    public static final int END_GAME_PAUSE = 3000;
    
    // data members
    //------------------------------------------------------
    
    private ArrayList<Event> _events;
    private int _userIDGenerator;
    
    // game info
    private String _name;
    private String _status;
    private int _humanPlayersNum;
    private int _currHumanNum;
    private int _computerPlayersNum;
    private boolean _autoDice;
    private boolean _isGameOver;
    
    // members
    private ArrayList<Player> _players;
    private Square[] _gameBoard;
    private Timer _timer;
    private ArrayList<Country> _countries;
    private SimpleAssetGroup _utils;
    private SimpleAssetGroup _tranportation;
    private ActionSquare _surprise;
    private ActionSquare _warrant;
    private int[] _dice;
    
    
    // c'tor
    //------------------------------------------------------
    
    /**
     * constructs a new game
     * @param XmlFileName the xml file name
     * @param gameName the name of the game
     * @param humanPlayers number of human players in the game
     * @param compPlayers number of computer players in the game
     * @param autoDice 
     * @throws NullPointerException if xmlFileName or gameName are null
     * @throws IllegalArgumentException if humanPlayers or compPlayers are illegal
     */
    public Game(String xmlFileName, String gameName, int humanPlayers, int compPlayers, boolean autoDice)
    {
    
        this.setName(gameName);
        this.setTotalHuman(humanPlayers);
        this.setComputerNumber(compPlayers);
        _autoDice = autoDice;

        // defaults
        _isGameOver = false;
        _currHumanNum = 0;
        _status = WAIT;
        _userIDGenerator = 0;
        ComputerPlayer.resetCompCounter(); // resets the computer player static counter

        _events = new ArrayList<Event>();
        _players = new ArrayList<Player>();
        _gameBoard = new Square[BOARD_SIZE];
        _countries = new ArrayList<Country>();
        _utils = new SimpleAssetGroup(this);
        _tranportation = new SimpleAssetGroup(this);
        _timer = null;
        _dice = new int[2];

        this.addMyChangeListener(GameManager.getInstance());

        // init board
        this.init(xmlFileName);

        // create computer players
        for (int i = 0; i < compPlayers; i++) 
        {
            _players.add(new ComputerPlayer(Game.DEFAULT_CASH));
        }
        
    }
    
    //------------------------------------------------------

    /**
     * gets the name of the game
     * @return the name of the game
     */
    public synchronized String getName(){return _name;}
    
    //------------------------------------------------------
    
    /**
     * gets the total number of human players
     * @return the total number of human players
     */
    public synchronized int getTotalHumanNum(){return _humanPlayersNum;}
    
    //------------------------------------------------------
    
    /**
     * gets the current number of human players
     * @return the current number of human players
     */
    public synchronized int getCurHumanNum(){return _currHumanNum;}
    
    //------------------------------------------------------
    
    /**
     * gets the number of computer players
     * @return the number of computer players
     */
    public synchronized int getCompNum(){return _computerPlayersNum;}
    
    //------------------------------------------------------
    
    /**
     * check if the game played with automatic dice roll
     * @return true if the game played with automatic dice roll
     */
    public synchronized boolean isAutoDice(){return _autoDice;}
    
    //------------------------------------------------------
    
    /**
     * get the game status
     * @return the game status
     */
    public synchronized String getStatus(){return _status;}
    
    //------------------------------------------------------
    
    /**
     * checks if the game is over
     * @return true if the game is over
     */
    public synchronized boolean isGameOver(){return _isGameOver;}
    
    //------------------------------------------------------
    
    /**
     * gets the total number of human players
     * @return the total number of human players
     */
    public synchronized int getTotalNumPlayers(){return _computerPlayersNum + _humanPlayersNum;}
    
    //------------------------------------------------------
    
    /**
     * gets the last event in the event array
     * @return the last event in the event array
     */
    public synchronized Event getLastEvent(){return _events.get(_events.size() - 1);}
    
    //------------------------------------------------------
    
    /**
     * checks if the game is active
     * @return true if the game is active
     */
    public synchronized boolean isActive(){return _status.equals(ACTIVE);}
    
    //------------------------------------------------------
    
    /**
     * check if the game is full (current human players = total human players)
     * @return true if the game is full
     */
    public synchronized boolean isFull(){return _currHumanNum == _humanPlayersNum;}
    
    //------------------------------------------------------
    
    /**
     * gets the players array
     * @return the players array
     */
    public synchronized ArrayList<Player> getPlayers(){return _players;}
    
    //------------------------------------------------------
    
    /**
     * gets the events array
     * @return the events array
     */
    public synchronized ArrayList<Event> getEvents(){return _events;}
    
    //------------------------------------------------------
    
    /**
     * gets the actual number of players
     * @return the actual number of players
     */
    private int getActualNumPlayers() 
    {
        int counter = 0;

        for (int i = 0; i < _players.size(); i++) 
        {
            Player player = _players.get(i);
            if (player != null && player.isInGame()) 
            {
                counter++;
            }
        }
        return counter;
    }
    
    //--------------------------------------------------------------------
    
    /**
     * gets a player by his index
     * @param name the name of the player to get
     * @return the player in the given index, null if not found
     * @throws NullPointerException if name is null
     */
    public synchronized Player getPlayerByName(String name)
    {
        if(name == null)
            throw new NullPointerException("player name is null");
        
        for(int i = 0; i < _players.size(); i++)
        {
            Player curr = _players.get(i);
            if(curr.getName().equals(name))
            {
                return curr;
            }
        }
        return null;
    }
    
    //--------------------------------------------------------------------
    
    /**
     * get a player with the given id
     * @param id the id of the player to get
     * @return the player with the given id, null if not found
     */
    public synchronized HumanPlayer getPlayerByID(int id)
    {
        for(int i = 0; i < _players.size(); i++)
        {
            Player curr = _players.get(i);
            if(curr.isHuman())
            {
                int playerID = ((HumanPlayer)curr).getID();
                if(playerID == id)
                {
                    return (HumanPlayer)curr;
                }
            }
        }
        return null;
    }
    
    //--------------------------------------------------------------------
    
    /**
     * gets the winner of the game
     * @return the winner of the game, null if there is no winner
     */
    private Player getWinner() 
    {
        for (int i = 0; i < _players.size(); i++) 
        {
            Player player = _players.get(i);
            
            if (player != null && player.isInGame()) 
            {
                return player;
            }
        }
        return null;
    }
    
    //--------------------------------------------------------------------
    
     /**
     * gets a square by its index in the squares array
     * @param index the index of the square
     * @return the square in that index 
     * @throws IndexOutOfBoundsException object if index is out of bounds
     */
    public Square getSquareByIndex(int index) 
    {
        if (index >= 0 && index < BOARD_SIZE) 
        {
            return _gameBoard[index];
        } 
        else 
            throw new IndexOutOfBoundsException("illegal square index");
    }
    
    //---------------------------------------------------------------------
    
    /**
     * gets a player by its index in the players array
     * @param index the index of the players
     * @return the player in that index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Player getPlayerByIndex(int index) 
    {
        if (index >= 0 && index < _players.size()) 
        {
            return _players.get(index);
        } 
        else 
            throw new IndexOutOfBoundsException("illegal player index");
    }
    
    //---------------------------------------------------------------------
    
    /**
     * adds a human player with the given name to the players array
     * not checking for game restrictions (duplicate names)
     * @param name the name of the player to add
     * @return a unique id to this player in this specific game
     * @throws NullPointerException if name is null
     */
    public synchronized int addPlayer(String name)
    {
        if(name != null)
        {
            // add to player array and give unique id
            _currHumanNum++;
            int id = _userIDGenerator;
            _players.add(new HumanPlayer(name, id, DEFAULT_CASH, this)); 
            _userIDGenerator++;
            return id;
        }
        else
            throw new NullPointerException("player name is null");
    }
    
    //------------------------------------------------------
        
    /**
     * adds an event the the event array
     * @param e the event to add
     * @throws NullPointerException if the event is null
     */
    public void addEvent(Event e)
    {
        if(e != null)
        {
            _events.add(e);
        }
        else
            throw new NullPointerException("event is null");
    }
    
    //--------------------------------------------------------------------
    
    /**
     * sets the name of the game
     * @param name the name to set
     * @throws NullPointerException if name is null
     */
    public final void setName(String name)
    {
        if(name != null)
        {
            _name = name;
        }
        else
            throw new NullPointerException("name of game is null");
    }
    
    //--------------------------------------------------------------------
    
    /**
     * sets the number of human players
     * @param numHuman the number of human players
     * @throws IllegalArgumentException if the number is illegal (non-positive, or more than MAX_NUM_PLAYERS)
     */
    public final void setTotalHuman(int numHuman)
    {
        if(numHuman > 0 && numHuman <= MAX_NUM_PLAYERS)
        {
            _humanPlayersNum = numHuman;
        }
        else
            throw new IllegalArgumentException("illegal human players number");
    }
    
    //--------------------------------------------------------------------
    
    /**
     * sets the number of computer players
     * @param compNum the number of computer players
     * @throws IllegalArgumentException if the number is illegal (negative or more than MAX_NUM_PLAYERS - 1)
     */
    public final void setComputerNumber(int compNum)
    {
        if(compNum >= 0 && compNum < MAX_NUM_PLAYERS)
        {
            _computerPlayersNum = compNum;
        }
        else
            throw new IllegalArgumentException("illegal computer player number");
    }
    
    //--------------------------------------------------------------------
    
    /**
     * sets the dice
     * @param dice1 the first dice
     * @param dice2 the second dice
     * @throws IllegalArgumentException if dice are illegal
     */
    public synchronized void setDice(int dice1, int dice2)
    {
        if(dice1 > 0 && dice1 <= 6 && dice2 > 0 && dice2 <= 6)
        {
            _dice[0] = dice1;
            _dice[1] = dice2;
        }
        else
            throw new IllegalArgumentException("illegal dice");
    } 
    
    //--------------------------------------------------------------------
    
    /**
     * initializes the board squares according to the demands of the exercise
     */
    private void initBoard() 
    {
        
        _gameBoard[START_SQUARE] = new StartSquare(this);
        _gameBoard[JAIL_OR_FREE_PASS_SQUARE] = new JailOrFreePassSquare(this);
        _gameBoard[PARKING_SQUARE] = new ParkingSquare(this);
        _gameBoard[GO_TO_JAIL_SQUARE] = new GoToJailSquare(this);
        _gameBoard[1] = _countries.get(0).getAssetByIndex(0);
        _gameBoard[2] = _countries.get(0).getAssetByIndex(1);
        _gameBoard[3] = _surprise;
        _gameBoard[4] = _tranportation.getAssetByIndex(0);
        _gameBoard[5] = _countries.get(1).getAssetByIndex(0);
        _gameBoard[6] = _countries.get(1).getAssetByIndex(1);
        _gameBoard[7] = _countries.get(1).getAssetByIndex(2);
        _gameBoard[8] = _warrant;
        _gameBoard[10] = _countries.get(2).getAssetByIndex(0);
        _gameBoard[11] = _countries.get(2).getAssetByIndex(1);
        _gameBoard[12] = _countries.get(2).getAssetByIndex(2);
        _gameBoard[13] = _utils.getAssetByIndex(0);
        _gameBoard[14] = _tranportation.getAssetByIndex(1);
        _gameBoard[15] = _countries.get(3).getAssetByIndex(0);
        _gameBoard[16] = _countries.get(3).getAssetByIndex(1);
        _gameBoard[17] = _countries.get(3).getAssetByIndex(2);
        _gameBoard[19] = _countries.get(4).getAssetByIndex(0);
        _gameBoard[20] = _countries.get(4).getAssetByIndex(1);
        _gameBoard[21] = _countries.get(4).getAssetByIndex(2);
        _gameBoard[22] = _warrant;
        _gameBoard[23] = _tranportation.getAssetByIndex(2);
        _gameBoard[24] = _countries.get(5).getAssetByIndex(0);
        _gameBoard[25] = _countries.get(5).getAssetByIndex(1);
        _gameBoard[26] = _countries.get(5).getAssetByIndex(2);
        _gameBoard[28] = _countries.get(6).getAssetByIndex(0);
        _gameBoard[29] = _countries.get(6).getAssetByIndex(1);
        _gameBoard[30] = _countries.get(6).getAssetByIndex(2);
        _gameBoard[31] = _utils.getAssetByIndex(1);
        _gameBoard[32] = _tranportation.getAssetByIndex(3);
        _gameBoard[33] = _countries.get(7).getAssetByIndex(0);
        _gameBoard[34] = _countries.get(7).getAssetByIndex(1);
        _gameBoard[35] = _countries.get(7).getAssetByIndex(2);

    }
       
    
    //--------------------------------------------------------------------
    
    
    /**
     * inits the game from the XMLgame Monopoly object created from the schema generated class Monopoly
     * @param XMLgame the XMLgame object created from the schema generated class Monopoly
     * @throws NullPointerException if XMLgame is null or if unmarshalling failed
     */
    private void init(String xmlFileName) 
    {
        if(xmlFileName == null)
            throw new NullPointerException("xml file name is null");
        
        generated.Monopoly XMLgame = MonopolyUtils.createObjectFromXml(xmlFileName);
        
        if (XMLgame == null) 
            throw new NullPointerException("XMLgame is null");
        

        _surprise = new ActionSquare(ActionSquare.SURPRISE, this);
        _warrant = new ActionSquare(ActionSquare.WARRANT, this);

        // init countries
        try 
        {
            int numCountries = XMLgame.getCountries().getSize();

            for (int i = 0; i < numCountries; i++) 
            {
                generated.Country XMLcountry = XMLgame.getCountries().getCountry().get(i);
                Country myCountry = new Country(this);
                myCountry.init(XMLcountry);
                _countries.add(myCountry);
            }
        } 
        catch (RuntimeException e) 
        {
            System.out.println("error: theres a problem with the countries format in the xml file");
            e.printStackTrace();
        }

        // init utils and transportation
        try 
        {
            for (int i = 0; i < NUM_SIMPLE_ASSETS; i++) 
            {
                int type = XMLgame.getSimpleAssetGroups().getGroup().get(i).getType().ordinal();
                generated.Group XMLassetGroup = XMLgame.getSimpleAssetGroups().getGroup().get(i);
                
                if (type == UTILS) 
                {
                    _utils.init(XMLassetGroup);
                } 
                else if (type == TRANSPORTATION) 
                {
                    _tranportation.init(XMLassetGroup);
                }
            }
        } 
        catch (RuntimeException e) 
        {
            System.out.println("error: theres a problem with the utils or tranportation format in the xml file");
            e.printStackTrace();
        }

        // init cards
        try 
        {
            generated.Cards XMLcards = XMLgame.getCards();
            _surprise.init(XMLcards);
            _warrant.init(XMLcards);
        } 
        catch (RuntimeException e) 
        {
            System.out.println("error: theres a problem with cards format in the xml file");
            e.printStackTrace();
        }

        this.initBoard();
    }
    
   //--------------------------------------------------------------------
    
    /**
     * runs the game
     */
    @Override
    public void run() 
    {
        _status = ACTIVE;
        
        MonopolyUtils.sleep(START_GAME_PAUSE); // give time to all users to login 
        
        // game start event
        Event gameStartEvent = 
                EventsFactory.createGameStartEvent(this._name);
        this.addEvent(gameStartEvent);

        while (getActualNumPlayers() >= MIN_NUM_PLAYERS && _currHumanNum > 0) // minimum 2 players, at least one human
        {
            // turn
            for (int i = 0; i < _players.size(); i++) 
            {

                Player player = _players.get(i);

                if (player != null && player.isInGame()) 
                {
                    
                    Square square = _gameBoard[player.getPosition()];

                    if (square.shouldPlayerRollDice(player)) 
                    {
                        // automatic roll if the game is auto dice or the player computerized or the human player resigned
                        if(_autoDice || !player.isHuman() || (player.isHuman() && ((HumanPlayer)player).isResign()))
                        {
                            this.rollTheDice();
                        }
                        else // need dice result from client
                        {
                            ((HumanPlayer)player).playerRollDice();                    
                        }
                        
                        Event diceRollEvent
                                = EventsFactory.createDiceRollEvent(_name, player.getName(), _dice[0], _dice[1]); 
                        this.addEvent(diceRollEvent);

                        // check if player allowed to move
                        if (square.shouldPlayerMove(player, _dice[0], _dice[1])) 
                        {
                            this.movePlayer(player, _dice[0], _dice[1]);
                        }
                    }
                    
                    if (player.isBankrupt()) 
                    {
                        this.removePlayerFromGame(player);

                        // lost event
                        Event playerLostEvent = EventsFactory.createPlayerLostEvent(_name, player);
                        this.addEvent(playerLostEvent);
                    } 
                    
                    if (player.isHuman() && ((HumanPlayer)player).isResign()) 
                    {
                        this.removePlayerFromGame(player);

                        // resign event
                        Event playerResignedEvent =
                                EventsFactory.createPlayerResignedEvent(_name, player.getName());
                        this.addEvent(playerResignedEvent);
                    }
                }
            }
        }

        if(_currHumanNum == 1)
        {
            // announce winner
            Player player = this.getWinner();
        
            Event playerWonEvent 
                    = EventsFactory.createGameWinnerEvent(_name, player.getName());
            this.addEvent(playerWonEvent);
            
        
            Event gameOverEvent 
                    = EventsFactory.createGameOverEvent(_name);
            this.addEvent(gameOverEvent);
        }
        
        _isGameOver = true;
        MonopolyUtils.sleep(END_GAME_PAUSE); // time for all clients to take all the last events
        this.fireMyChangeEvent("game over"); // notify the game manager that the game is over
    }
    
    //--------------------------------------------------------------------
    
    /**
     * rolls the dice
     */
    private void rollTheDice()
    {
        Random generator = new Random();
        _dice[0] = generator.nextInt(6) + 1;
        _dice[1] = generator.nextInt(6) + 1;
    }
    
    //--------------------------------------------------------------------
    
    /**
     * moves the player according to the dice result
     * @param player the player to move
     * @param dice1 the first dice
     * @param dice2 the second dice
     * @throws NullPointerException if player is null
     */
    private void movePlayer(Player player, int dice1, int dice2) 
    {
        if (player == null) 
            throw new NullPointerException("player is null");
       
        int oldPos = player.getPosition();
        int newPos = (oldPos + dice1 + dice2) % BOARD_SIZE;

        // create event
        Event playerMovedEvent
                = EventsFactory.createPlayerMovedEvent(_name, player, newPos, REGULAR_MOVE);
        this.addEvent(playerMovedEvent);
        
        // put in new position
        player.setPosition(newPos);

        if (oldPos > newPos && newPos != START_SQUARE) 
        {
            this.passedStartOnTheWay(player);
        }

        _gameBoard[newPos].playerArrived(player);
    }
    
    //--------------------------------------------------------------------
    
    /**
     * adds the given player 200 dollars
     * @param player the player to add money to
     * @throws NullPointerException if player is null
     */
    public void passedStartOnTheWay(Player player) 
    {
        if (player != null) 
        {
            player.addCash(PASSED_START_CASH);
            Event playerPassedOnStartEvent = 
                    EventsFactory.createPassedStartSquareEvent(_name, player.getName()); 
            
            Event paymentEvent =
                    EventsFactory.createPaymentToOrFromTreasuryEvent(_name, player.getName(), PASSED_START_CASH, false);
            
            this.addEvent(playerPassedOnStartEvent);
            this.addEvent(paymentEvent);
        } 
        else 
            throw new NullPointerException("player is null");
    }
    
    //---------------------------------------------------------------------
    	
    /**
     * removes the given player from the game
     * @param player the player to remove
     * @throws NullPointerException if player is null
     */
    private void removePlayerFromGame(Player player) 
    {
        if (player == null) 
            throw new NullPointerException("player is null");
    

        AssetGroupsSet assets = new AssetGroupsSet(_countries, _utils, _tranportation);
        Iterator<AssetGroup> iter = assets.iterator();

        player.setIsInGame(false);

        while (iter.hasNext()) 
        {
            AssetGroup group = iter.next();
            for (int i = 0; i < group.getNumAssets(); i++) 
            {
                SingleAsset asset = group.getAssetByIndex(i);
                if (asset.isOwnedBy(player)) 
                {
                    asset.setOwner(null);
                }
            }
        }
        
        if(player.isHuman())
        {
            _currHumanNum--;
        }
    }
    
    //---------------------------------------------------------------------
    
    /**
     * starts a countdown resign timer with the given delay in seconds
     * @param seconds the delay
     * @throws IllegalArgumentException if seconds is non-positive
     */
    public synchronized void startTimer(int seconds)
    {
        if(seconds <= 0)
            throw new IllegalArgumentException("illegal seconds");
        
        _timer = new Timer(true);
        _timer.schedule(new RemovePlayerTask(), 1000 * seconds);
    }
    
    //---------------------------------------------------------------------
    
    /**
     * stops the timer and proceeds with the game
     */
    public void stopTimer()
    {
        Event e = this.getLastEvent(); // get last event, its a prompt event with a player name
        Player player = this.getPlayerByName(e.getPlayerName());
        
        if(_timer != null)
        {
            _timer.cancel();
            _timer = null;
            
            // continue with the game, the waiting object is the player
            synchronized(player)
            {
                player.notify();
            }
        }
    }
    
   
    // RemovePlayerTask class
   //=======================================================================
    
    /**
     * this class represents a remove player task
     */
    public class RemovePlayerTask extends TimerTask
    {
        
        @Override
        public void run() 
        {
            Event e = getLastEvent();
            Player player = getPlayerByName(e.getPlayerName());
            
            if(player.isHuman())
            {
                ((HumanPlayer)player).setResign(true);
            }
            
            // continue with the game
            synchronized(player)
            {
                player.notify();
            }
            
            stopTimer();
        }
        
    }

}
