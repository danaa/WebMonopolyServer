package monopoly;


import gameLogic.GameManager;
import gameLogic.Game;
import gameLogic.players.HumanPlayer;
import gameLogic.players.Player;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import monopoly.results.EventArrayResult;
import monopoly.results.GameDetailsResult;
import monopoly.results.IDResult;
import monopoly.results.MonopolyResult;
import monopoly.results.PlayerDetailsResult;


/**
 * this class represents a monopoly game web service
 * @author Dana Akerman
 */
public class MonopolyGame 
{
    
    // data members
    //---------------------------------------------------------------------------
    
    private static GameManager _gameManager = GameManager.getInstance();
    
    // methods
    //---------------------------------------------------------------------------

    /**
     * gets the game board schema
     * @return the game board schema as string
     */
    public String getGameBoardSchema() 
    {
        try
        {
            return MonopolyUtils.textFileToString("MySchema.xsd");
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in get game board schema", e);
            return "";
        }
    }
    
    //---------------------------------------------------------------------------

    /**
     * gets the game board xml
     * @return the game board xml as string
     */
    public String getGameBoardXML() 
    {
        try
        {
            return MonopolyUtils.textFileToString("monopoly.xml");
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in get game board xml", e);
            return "";
        }
    }
    
    //---------------------------------------------------------------------------

    /**
     * creates a new game with the given parameters
     * @param gameName the name of the game
     * @param humanPlayers the number of human players
     * @param computerizedPlayers the number of computerized players
     * @param useAutomaticDiceRoll true if use automatic dice roll
     * @return a Monopoly result
     */
    public MonopolyResult startGame (String gameName, int humanPlayers, int computerizedPlayers, boolean useAutomaticDiceRoll) 
    {
        try
        {
            if(_gameManager.isGameExists())
            {
                return new MonopolyResult("only one game allowed");
            }
        
            if(gameName == null || gameName.isEmpty())
            {
                return new MonopolyResult("illegal game name");
            }
        
            if(humanPlayers < 1)
            {
                return new MonopolyResult("must have at least one human player!");
            }
        
            if(computerizedPlayers < 0)
            {
                return new MonopolyResult("illegal number of computer players");
            }
        
            int numPlayers = humanPlayers + computerizedPlayers;
            if(numPlayers < Game.MIN_NUM_PLAYERS || numPlayers > Game.MAX_NUM_PLAYERS)
            {
                return new MonopolyResult("illegal number of players (2-6)");
            }
        
            _gameManager.startGame("monopoly.xml", gameName, humanPlayers, computerizedPlayers, useAutomaticDiceRoll);
        
            return new MonopolyResult();
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error start game", e);
            return new MonopolyResult("unknown error");
        }
    }
    
    //---------------------------------------------------------------------------

    /**
     * gets the details of the game with the given name
     * @param gameName the name of the game
     * @return a GameDetailsResult
     */
    public GameDetailsResult getGameDetails (String gameName)
    {
        try
        {
            if(gameName == null)
            {
                return new GameDetailsResult("illegal game name");
            }
        
            // if there is no game, or the name is wrong
            if(!_gameManager.isGameExists() || !(_gameManager.isGameSameName(gameName)))
            {
                return new GameDetailsResult("this game does not exist");
            }
        
            String status = _gameManager.getGameStatus();
            int totalHuman = _gameManager.getGameTotalHumanNum();
            int comp = _gameManager.getGameCompNum();
            int curHuman = _gameManager.getGameCurHumanNum();
            boolean autoDice = _gameManager.getGameAutoDice();
            return new GameDetailsResult(status, totalHuman, comp, curHuman, autoDice);
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in get game details", e);
            return new GameDetailsResult("unknown error");
        }
    }
    
    //---------------------------------------------------------------------------

    /**
     * gets the waiting games
     * @return the waiting games or empty array if there are no waiting games
     * or if there's an exception
     */
    public String[] getWaitingGames() 
    {
        try
        {
            if(!_gameManager.isGameExists())
            {
                return new String[0]; // empty string array
            }
            if(!(_gameManager.isGameActive()))
            {
                String[] waitingGame = new String[1];
                waitingGame[0] = _gameManager.getGameName();
                return waitingGame;
            }
            return new String[0];
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in get waiting games", e);
            return new String[0];
        }
    }
    
    //---------------------------------------------------------------------------
    
    /**
     * gets the active games
     * @return the active games or empty array if there are no active games
     * or if there's an exception
     */
    public String[] getActiveGames() 
    {
        try
        {
            if(!_gameManager.isGameExists())
            {
                return new String[0]; // empty string array
            }
            if(_gameManager.isGameActive())
            {
                String[] activeGame = new String[1];
                activeGame[0] = _gameManager.getGameName();
                return activeGame;
            }
            return new String[0];
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in get active games", e);
            return new String[0];
        }
    }
    
    //---------------------------------------------------------------------------

    /**
     * joins the player with the given name to the game with the given name
     * gives the player a unique id to this specific game
     * @param gameName the name of the game
     * @param playerName the name of the player
     * @return IDResult
     */
    public IDResult joinGame (String gameName, String playerName) 
    {
        
        try
        {
            if(gameName == null)
            {
                return new IDResult("illegal game name");
            }   
            if((!_gameManager.isGameExists()) || !(_gameManager.isGameSameName(gameName)))
            {
                return new IDResult("this game does not exist");
            }
            if(_gameManager.isGameActive())
            {
                return new IDResult("cannot join an active game");
            }
            if(_gameManager.getPlayerByName(playerName) != null || playerName == null || playerName.equals(""))
            {
                return new IDResult("illegal player name");
            }
        
            int id = _gameManager.addPlayerToGame(playerName);
            if(_gameManager.isGameFull())
            {
                _gameManager.runGame();
            }
            return new IDResult(id);
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in join game", e);
            return new IDResult("unknown error");
        }
               
    }
    
    //---------------------------------------------------------------------------

    /**
     * gets the players details
     * @param gameName the name of the game
     * @return a PlayerDetailsResult
     */
    public PlayerDetailsResult getPlayersDetails(String gameName) 
    {
        try
        {
            if(gameName == null)
            {
                return new PlayerDetailsResult("illegal game name");
            }
        
            if(!_gameManager.isGameExists() || !(_gameManager.isGameSameName(gameName)))
            {
                return new PlayerDetailsResult("this game does not exist");
            }
        
            ArrayList<Player> players = _gameManager.getGamePlayers();
            String names[] = new String[players.size()];
            boolean isHuman[] = new boolean[players.size()];
            boolean isActive[] = new boolean[players.size()];
            int money[] = new int[players.size()];
        
            for (int i = 0; i < players.size(); i++)
            {
                Player curr = players.get(i);
                names[i] = curr.getName();
                isHuman[i] = curr.isHuman();
                isActive[i] = curr.isInGame();
                money[i] = curr.getCash();
            }
        
            return new PlayerDetailsResult(names, isHuman, isActive, money);
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in get players details", e);
            return new PlayerDetailsResult("unknown error");
        }
    }
    
    //---------------------------------------------------------------------------

    /**
     * gets the events of the game form the given eventID
     * @param eventID the last event id of the client
     * @return EventArrayResult
     */
    public EventArrayResult getAllEvents (int eventID)
    {
        
        try
        {
        
            if(!_gameManager.isGameExists() || !_gameManager.isGameActive())
            {
                return new EventArrayResult("no active game");
            }
        
            ArrayList<Event> events = _gameManager.getGameEvents();
        
            if(eventID < 0 || eventID > events.size())
            {
                return new EventArrayResult("illegal event id");
            }
       
            Event[] toSend = new Event[events.size() - eventID];
        
            for(int i = 0; i < toSend.length; i++)
            {
                toSend[i] = events.get(i + eventID);
            }
        
            return new EventArrayResult(toSend); 
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in get all events", e);
            return new EventArrayResult("unknown error");
        }
       
    }
    
    //---------------------------------------------------------------------------

    /**
     * sets the dice results
     * @param playerID the id of the player
     * @param eventID the current event id
     * @param dice1 the first dice
     * @param dice2 the second dice
     * @return MonopolyResult
     */
    public MonopolyResult setDiceRollResults (int playerID, int eventID, int dice1, int dice2) 
    {
      
        try
        {
            if(!_gameManager.isGameExists() || !_gameManager.isGameActive())
            {
                return new MonopolyResult("no active game");
            }
        
            Event last = _gameManager.getLastGameEvent();
        
            if(last.getEventID() != eventID)
            {
                return new MonopolyResult("illegal event id");
            }
        
            HumanPlayer player =  _gameManager.getGamePlayerById(playerID);
        
            // invalid player id
            if(player == null)
            {
                return new MonopolyResult("illegal player id");
            }
        
            // last event player and requasting player are not the same
            if(_gameManager.getPlayerByName(last.getPlayerName()) != player)
            {
                return new MonopolyResult("illegal player id");
            }
        
            if(!player.isInGame())
            {
                return new MonopolyResult("player not in game, can't set dice");
            }
        
            // illegal dice
            if(dice1 < 1 || dice1 > 6 || dice2 < 1 || dice2 > 6)
            {
                return new MonopolyResult("illegal dice value");
            }
        
            _gameManager.setGameDice(dice1, dice2);
            _gameManager.stopGameTimer();
            return new MonopolyResult();
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in set dice roll results", e);
            return new MonopolyResult("unknown error");
        }
        
    }
    
    //---------------------------------------------------------------------------

    /**
     * resigns from the game
     * @param playerID the player id
     * @return MonopolyResult
     */
    public MonopolyResult resign (int playerID) 
    {
        
        try
        {
            if(!_gameManager.isGameExists() || !_gameManager.isGameActive())
            {
                return new MonopolyResult("no active game");
            }
        
            HumanPlayer player = _gameManager.getGamePlayerById(playerID);
        
            if(player == null)
            {
                return new MonopolyResult("illegal player id");
            }
            if(!player.isInGame())
            {
                return new MonopolyResult("player not in game, cannot resign");
            }
        
            player.setResign(true);
        
            return new MonopolyResult();
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in resign", e);
            return new MonopolyResult("unknown error");
        }
    }
    
    //---------------------------------------------------------------------------

    /**
     * buys an asset or a house
     * @param playerID the player's id
     * @param eventID the current event id
     * @param buy the buy decision
     * @return MonopolyResult
     */
    public MonopolyResult buy (int playerID, int eventID, boolean buy) 
    {
        
        try
        {
            if(!_gameManager.isGameExists() || !_gameManager.isGameActive())
            {
                return new MonopolyResult("no active game");
            }
        
            Event last = _gameManager.getLastGameEvent();
        
            if(last.getEventID() != eventID)
            {
                return new MonopolyResult("illegal event id");
            }
        
            HumanPlayer player =  _gameManager.getGamePlayerById(playerID);
        
            // invalid player id
            if(player == null)
            {
                return new MonopolyResult("illegal player id");
            }
        
            // last event player and requasting player are not the same
            if(_gameManager.getPlayerByName(last.getPlayerName()) != player)
            {
                return new MonopolyResult("illegal player id");
            }
        
            if(!player.isInGame())
            {
                return new MonopolyResult("player not in game, can't buy");
            }
        
        
            player.setDesicion(buy);
            _gameManager.stopGameTimer();
        
            return new MonopolyResult();
        }
        catch(Exception e)
        {
            Logger.getLogger(MonopolyGame.class.getName()).log(Level.SEVERE, "error in buy", e);
            return new MonopolyResult("unknown error");
        }
    }
    
}