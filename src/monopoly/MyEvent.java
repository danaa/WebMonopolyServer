package monopoly;


import gameLogic.Game;


/**
 * This class implements the Event interface
 * @author Dana Akerman
 */
public class MyEvent implements Event
{
 
    // constants
    //-------------------------------------------------------
    
    public static final int GAME_START = 1;
    public static final int GAME_OVER = 2;
    public static final int GAME_WINNER = 3;
    public static final int PLAYER_RESIGNED = 4;
    public static final int PLAYER_LOST = 5;
    public static final int PROMPT_ROLL_DICE = 6;
    public static final int DICE_ROLL = 7;
    public static final int MOVE = 8;
    public static final int PASSED_START = 9;
    public static final int LANDED_ON_START = 10;
    public static final int GO_TO_JAIL = 11;
    public static final int PROMPT_BUY_ASSET = 12;
    public static final int PROMPT_BUY_HOUSE = 13;
    public static final int ASSET_BOUGHT = 14;
    public static final int HOUSE_BOUGHT = 15;
    public static final int SURPRISE_CARD = 16;
    public static final int WARRANT_CARD = 17;
    public static final int GET_OUT_OF_JAIL = 18;
    public static final int PAYMENT = 19;
    public static final int USED_JAIL_CARD = 20;
 
    // data members
    //-------------------------------------------------------
    
    private String gameName = "";
    private int eventID = 0;
    private int timeoutCount = 0;
    private int eventType = 0;
    private String playerName = "";
    private String eventMessage = "";
    private int boardSquareID = 0;
    private int firstDiceResult = 0;
    private int secondDiceResult = 0;
    private boolean playerMoved = false;
    private int nextBoardSquareID = 0;
    private boolean paymentToOrFromTreasury = false;
    private boolean paymentFromUser = false;
    private String paymentToPlayerName = "";
    private int paymentAmount = 0;
    
    // getters & setters
    //-------------------------------------------------------
     
    @Override
    public String getGameName(){return gameName;}
    
    //-------------------------------------------------------

    @Override
    public int getEventID(){return eventID;}
    
    //-------------------------------------------------------

    @Override
    public int getTimeoutCount(){return timeoutCount;}
    
    //-------------------------------------------------------

    @Override
    public int getEventType(){return eventType;}
    
    //-------------------------------------------------------

    @Override
    public String getPlayerName(){return playerName;}
    
    //-------------------------------------------------------

    @Override
    public String getEventMessage(){return eventMessage;}
    
    //-------------------------------------------------------

    @Override
    public int getBoardSquareID(){return boardSquareID;}
    
    //-------------------------------------------------------

    @Override
    public int getFirstDiceResult(){return firstDiceResult;}
    
    //-------------------------------------------------------

    @Override
    public int getSecondDiceResult(){return secondDiceResult;}
    
    //-------------------------------------------------------

    @Override
    public boolean isPlayerMoved(){return playerMoved;}
    
    //-------------------------------------------------------

    @Override
    public int getNextBoardSquareID(){return nextBoardSquareID;}
    
    //-------------------------------------------------------

    @Override
    public boolean isPaymentToOrFromTreasury(){return paymentToOrFromTreasury;}
    
    //-------------------------------------------------------

    @Override
    public boolean isPaymemtFromUser(){return paymentFromUser;}
    
    //-------------------------------------------------------

    @Override
    public String getPaymentToPlayerName(){return paymentToPlayerName;}
    
    //-------------------------------------------------------

    @Override
    public int getPaymentAmount(){return paymentAmount;}
    
    //-------------------------------------------------------
    
    /**
     * sets the name of the game
     * @param name the game name
     * @throws NullPointerException if name is null
     */
    public void setGameName(String name)
    {
        if(name != null)
        {
            gameName = name;
        }
        else
            throw new NullPointerException("game name is null");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the event id
     * @param id the event id
     */
    public void setEventID (int id)
    {
        eventID = id;
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the timeout
     * @param count the timeout count
     * @throws IllegalArgumentException if count is negative
     */
    public void setTimeoutCount(int count)
    {
        if(count >= 0)
        {
            timeoutCount = count;
        }
        else
            throw new IllegalArgumentException("illegal time out count");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the event type
     * @param type the type of the event
     * @throws IllegalArgumentException if type is illegal
     */
    public void setEventType(int type)
    {
        if(type >= 1 && type <= 20)
        {
            eventType = type;
        }
        else
            throw new IllegalArgumentException("illegal type");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the player name
     * @param name the player name
     * @throws NullPointerException if player name is null
     */
    public void setPlayerName(String name)
    {
        if(name != null)
        {
            playerName = name;
        }
        else
            throw new NullPointerException("player name is null");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the event message
     * @param message the event message
     * @throws NullPointerException if message is null
     */
    public void setEventMessage(String message)
    {
        if(message != null)
        {
            eventMessage = message;
        }
        else
            throw new NullPointerException("message is null");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the board square id
     * @param squareID the board square id
     * @throws IllegalArgumentException if square id is illegal
     */
    public void setBoardSquareID(int squareID)
    {
        if(squareID >= 0 && squareID < Game.BOARD_SIZE)
        {
            boardSquareID = squareID;
        }
        else 
            throw new IllegalArgumentException("illegal square id");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the first dice result
     * @param dice the first dice
     * @throws IllegalArgumentException if dice is illegal
     */
    public void setFirstDiceResult(int dice)
    {
        if(dice > 0 && dice <= 6)
        {
            firstDiceResult = dice;
        }
        else 
            throw new IllegalArgumentException("illegal first dice value");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the second dice result
     * @param dice the second dice
     * @throws IllegalArgumentException if dice is illegal
     */
    public void setSecondDiceResult(int dice)
    {
        if(dice > 0 && dice <= 6)
        {
            secondDiceResult = dice;
        }
        else 
            throw new IllegalArgumentException("illegal second dice value");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the playerMoved flag
     * @param val the boolean val to set
     */
    public void setPlayerMoved(boolean val)
    {
        playerMoved = val;
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the next square id
     * @param squareID the next square id
     * @throws IllegalArgumentException if square id is illegal
     */
    public void setNextBoardSquareID(int squareID)
    {
        if(squareID >= 0 && squareID < Game.BOARD_SIZE)
        {
            nextBoardSquareID = squareID;
        }
        else 
            throw new IllegalArgumentException("illegal next square id");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the paymentToOrFromTreasury flag
     * @param val the boolean val to set
     */
    public void setPaymentToOrFromTreasury(boolean val)
    {
        paymentToOrFromTreasury = val;
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the paymentFromUser flag
     * @param val the boolean val to set
     */
    public void setPaymemtFromUser(boolean val)
    {
        paymentFromUser = val;
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the payment to player name
     * @param name the name of the player to recieve/pay to
     * @throws NullPointerException if name is null
     */
    public void setPaymentToPlayerName(String name)
    {
        if(name != null)
        {
            paymentToPlayerName = name;
        }
        else
            throw new NumberFormatException("payment to player name is null");
    }
    
    //-------------------------------------------------------
    
    /**
     * sets the payment amount
     * @param payment the payment amount
     * @throws IllegalArgumentException if payment is non-positive
     */
    public void setPaymentAmount(int payment)
    {
        if(payment > 0)
        {
            paymentAmount = payment;
        }
        else
            throw new IllegalArgumentException("illegal payment value");
    }
    
    
}
