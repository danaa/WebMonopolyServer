package gameLogic.players;


import gameLogic.Game;
import gameLogic.cards.PardonCard;
import gameLogic.squares.SingleAsset;


/**
 * this abstract class represents a player in a monopoly game
 * @author Dana Akerman
 */
public abstract class Player 
{
    
    // statics
    //---------------------------------------------------------

    private static int counter = 0;
    
    // data members
    //---------------------------------------------------------
    
    protected String _name;
    private int _num;
    private int _position;
    private boolean _canMoveNextTurn;
    private boolean _isInGame;
    private PardonCard _pardonCard;
    protected int _cash;
    private boolean _bankrupt;

    // c'tor
    //---------------------------------------------------------
    
    /**
     * constructs a new player. called only by the derived classes
     * @param name the name of the player
     * @param cash the amount of cash the player has
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if 'cash' is non-positive
     */
    protected Player(String name, int cash) 
    {
        
        this.setName(name);
        this.setPosition(Game.START_SQUARE);
        this.setCash(cash);
        this.setPardonCard(null);
        _canMoveNextTurn = true;
        _isInGame = true;
        _bankrupt = false;
        _num = ++counter;
    }

    // methods
    //---------------------------------------------------------
    
    /**
     * returns the player's number
     * @return the player's number
     */
    public int getNum(){return _num;}

    //----------------------------------------------------------------
    
    /**
     * returns the player's name
     * @return the player's name
     */
    public String getName(){return _name;}

    //----------------------------------------------------------------
    
    /**
     * returns the player's position
     * @return the player's position
     */
    public int getPosition(){return _position;}

    //----------------------------------------------------------------
    
    /**
     * returns the player's amount of cash
     * @return the player's amount of cash
     */
    public int getCash(){return _cash;}

    //----------------------------------------------------------------
    
    /**
     * returns true if the player can move, false otherwise
     * @return true if the player can move, false otherwise
     */
    public boolean canPlayerMove(){return _canMoveNextTurn;}

    //----------------------------------------------------------------
    
    /**
     * returns true if the player is in game, false otherwise
     * @return true if the player is in game, false otherwise
     */
    public synchronized boolean isInGame(){return _isInGame;}

    //----------------------------------------------------------------
    
    /**
     * return trues if the player is bankrupt, false otherwise
     * @return true if the player is bankrupt, false otherwise
     */
    public boolean isBankrupt(){return _bankrupt;}

    //----------------------------------------------------------------
    
    /**
     * returns true if the player has a pardon card, false otherwise
     * @return true if the player has a pardon card, false otherwise
     */
    public PardonCard getPardonCard(){return _pardonCard;}

    //----------------------------------------------------------------
    
    /**
     * gets the player decision about buying the given asset
     * @param asset the asset to buy
     * @param what what to buy
     * @return true if player decides to buy the asset, false otherwise
     */
    public abstract boolean buyDecision(SingleAsset asset, int what);

    //----------------------------------------------------------------
    
    /**
     * gets the player decision about using his pardon card to get out of jail
     * @return true if player decides to use it, false otherwise
     */
    public boolean pardonCardDecision(){return true;}

    //---------------------------------------------------------
    
    /**
     * checks if the player is human
     * @return true if the player is human, false otherwise
     */
    public abstract boolean isHuman();

    //---------------------------------------------------------
    
    /**
     * checks if the player has a pardon card
     * @return true if the player has a pardon card, false otherwise
     */
    public boolean hasPardonCard() 
    {
        return (_pardonCard != null);
    }

    //---------------------------------------------------------
   
    /**
     * sets the name of the player to the given name
     * @param name the name of the player
     * @throws NullPointerException if 'name' is null
     */
    public final void setName(String name) 
    {
        if (name != null) 
        {
            _name = name;
        } 
        else 
            throw new NullPointerException("name is null");
    }

    //---------------------------------------------------------
    
    /**
     * sets the position of the player to the given position
     * @param position the position of the player
     * @throws IllegalArgumentException if position is out of the game board
     */
    public final void setPosition(int position) 
    {
        if (position >= 0 && position <= Game.BOARD_SIZE) 
        {
            _position = position;
        } 
        else 
            throw new IllegalArgumentException("illegal position input");
    }

    //---------------------------------------------------------
    
    /**
     * sets the cash of the player to the given cash
     * @param cash the cash amount of the player
     * @throws IllegalArgumentException if 'cash' is non-positive
     */
    public final void setCash(int cash) 
    {
        if (cash >= 0) 
        {
            _cash = cash;
        } 
        else 
            throw new IllegalArgumentException("illegal cash input");
    }

    //---------------------------------------------------------
    
    /**
     * sets the 'bankrupt' flag of the player
     * @param val the boolean value 
     */
    public void setBankrupt(boolean val) 
    {
        _bankrupt = val;
    }

    //---------------------------------------------------------
    
    /**
     * sets the 'canMoveNextTurn' flag of the player
     * @param val - the boolean value
     */
    public void setCanMove(boolean val) 
    {
        _canMoveNextTurn = val;
    }

    //---------------------------------------------------------
    
    /**
     * sets the 'isInGame' flag of the player
     * @param val the boolean value
     */
    public void setIsInGame(boolean val) 
    {
        _isInGame = val;
    }

    //---------------------------------------------------------
    
    /**
     * sets the pardon card of the player to the given pardon card
     * @param card the pardon card
     */
    public final void setPardonCard(PardonCard card) 
    {
        _pardonCard = card; // can be null
    }

    //---------------------------------------------------------
    
    /**
     * adds cash to the player
     * @param cash the amount of cash to add
     * @throws IllegalArgumentException if 'cash' is none-positive
     */
    public void addCash(int cash) 
    {
        this.setCash(_cash + cash);
    }

    //---------------------------------------------------------
    
    /**
     * reduces cash from the player
     * @param cash the amount of cash to reduce
     * @throws IllegalArgumentException if 'cash' is non-positive
     */
    public int reduceCash(int cash) 
    {
        if (cash > 0) 
        {
            int payment;

            if (cash > _cash) 
            {
                payment = _cash;
                _bankrupt = true;
            } 
            else 
            {
                payment = cash;
            }
            
            this.setCash(_cash - payment);
            return payment;
        } 
        else 
            throw new IllegalArgumentException("illegal cash input");
    }

    //---------------------------------------------------------
    
    @Override
    public String toString() 
    {
        return "Name: " + _name + ", Cash: " + _cash + "$";
    }
}
