package monopoly;


import gameLogic.players.Player;


/**
 * this class generates events according to the requasted type
 * @author Dana Akerman
 */
public class EventsFactory 
{
    // statics
    //---------------------------------------------------------------------
    
    public static int eventIdGenerator = 0;
    
    //---------------------------------------------------------------------
    
    public static void resetEventIdGenerator()
    {
        eventIdGenerator = 0;
    }
    
    /**
     * creats a game start event to a game with the given name
     * @param gameName the name of the game
     * @return a "game start" event
     */
    public static Event createGameStartEvent(String gameName)
    {
        MyEvent gameStartEvent = new MyEvent();
        gameStartEvent.setGameName(gameName);
        gameStartEvent.setEventID(++eventIdGenerator);
        gameStartEvent.setEventType(MyEvent.GAME_START);
        
        return gameStartEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a game over event to a game with the given name
     * @param gameName the name of the game
     * @return a "game over" event
     */
    public static Event createGameOverEvent(String gameName)
    {
        MyEvent gameOverEvent = new MyEvent();
        gameOverEvent.setGameName(gameName);
        gameOverEvent.setEventID(++eventIdGenerator);
        gameOverEvent.setEventType(MyEvent.GAME_OVER);
        
        return gameOverEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a game winner event
     * @param gameName the name of the game
     * @param winner the winner
     * @return a "game winner" event
     */
    public static Event createGameWinnerEvent(String gameName, String winner)
    {
        MyEvent gameWinnerEvent = new MyEvent();
        gameWinnerEvent.setGameName(gameName);
        gameWinnerEvent.setEventID(++eventIdGenerator);
        gameWinnerEvent.setEventType(MyEvent.GAME_WINNER);
        gameWinnerEvent.setPlayerName(winner);
        
        return gameWinnerEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a player resigned event
     * @param gameName the name of the game
     * @param player the player that resigned
     * @return a "player resigned" event
     */
    public static Event createPlayerResignedEvent(String gameName, String playerName)
    {
        MyEvent playerResignedEvent = new MyEvent();
        playerResignedEvent.setGameName(gameName);
        playerResignedEvent.setEventID(++eventIdGenerator);
        playerResignedEvent.setEventType(MyEvent.PLAYER_RESIGNED);
        playerResignedEvent.setPlayerName(playerName);
        
        return playerResignedEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a player lost event
     * @param gameName the name of the game
     * @param loser the player that lost
     * @return a "player lost" event
     */
    public static Event createPlayerLostEvent(String gameName, Player loser)
    {
        MyEvent playerLostEvent = new MyEvent();
        playerLostEvent.setGameName(gameName);
        playerLostEvent.setEventID(++eventIdGenerator);
        playerLostEvent.setEventType(MyEvent.PLAYER_LOST);
        playerLostEvent.setPlayerName(loser.getName());
        playerLostEvent.setBoardSquareID(loser.getPosition());
        
        return playerLostEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates prompt dice roll event
     * @param gameName the name of the game
     * @param playerName the player to prompt
     * @param timerSec the delay in seconds of the countdown timer
     * @return a "prompt dice roll" event
     */
    public static Event createPromptDiceRollEvent(String gameName, String playerName, int timerSec)
    {
        MyEvent promptDiceRollEvent = new MyEvent();
        promptDiceRollEvent.setGameName(gameName);
        promptDiceRollEvent.setEventID(++eventIdGenerator);
        promptDiceRollEvent.setEventType(MyEvent.PROMPT_ROLL_DICE);
        promptDiceRollEvent.setPlayerName(playerName);
        promptDiceRollEvent.setTimeoutCount(timerSec);
        
        return promptDiceRollEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a dice roll event
     * @param gameName the name of the game
     * @param playerName the name of the player that rolled the dice
     * @param dice1 the first dice
     * @param dice2 the second dice
     * @return a "dice roll" event
     */
    public static Event createDiceRollEvent(String gameName, String playerName, int dice1, int dice2)
    {
        MyEvent diceRollEvent = new MyEvent();
        diceRollEvent.setGameName(gameName);
        diceRollEvent.setEventID(++eventIdGenerator);
        diceRollEvent.setEventType(MyEvent.DICE_ROLL);
        diceRollEvent.setPlayerName(playerName);
        diceRollEvent.setFirstDiceResult(dice1);
        diceRollEvent.setSecondDiceResult(dice2);
        
        return diceRollEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a player moved event
     * @param gameName the name of the game
     * @param player the player that moved
     * @param dest the destenation
     * @param moveType the move type, regular or teleport
     * @return a "player moved" event
     */
    public static Event createPlayerMovedEvent(String gameName, Player player, int dest, String moveType)
    {
        MyEvent playerMovedEvent = new MyEvent();
        playerMovedEvent.setGameName(gameName);
        playerMovedEvent.setEventID(++eventIdGenerator);
        playerMovedEvent.setEventType(MyEvent.MOVE);
        playerMovedEvent.setEventMessage(moveType); // tell the client how to move the player
        playerMovedEvent.setPlayerName(player.getName());
        playerMovedEvent.setBoardSquareID(player.getPosition());
        playerMovedEvent.setNextBoardSquareID(dest);
        
        return playerMovedEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a passed start event
     * @param gameName the name of the game
     * @param playerName the name of the player that passed start
     * @return a "passed start square" event
     */
    public static Event createPassedStartSquareEvent(String gameName, String playerName)
    {
        MyEvent passedStartEvent = new MyEvent();
        passedStartEvent.setGameName(gameName);
        passedStartEvent.setEventID(++eventIdGenerator);
        passedStartEvent.setEventType(MyEvent.PASSED_START);
        passedStartEvent.setPlayerName(playerName);
        
        return passedStartEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a landed on start square event
     * @param gameName the name of the game
     * @param playerName the name of the player that landed on start
     * @return a "landed on start square" event
     */
    public static Event createLandedOnStartEvent(String gameName, String playerName)
    {
        MyEvent landedOnStartEvent = new MyEvent();
        landedOnStartEvent.setGameName(gameName);
        landedOnStartEvent.setEventID(++eventIdGenerator);
        landedOnStartEvent.setEventType(MyEvent.LANDED_ON_START);
        landedOnStartEvent.setPlayerName(playerName);
        
        return landedOnStartEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a go to jail event
     * @param gameName the name of the game
     * @param playerName the name of the player that was sent to jail
     * @return a "go to jail" event
     */
    public static Event createGoToJailEvent(String gameName, String playerName)
    {
        MyEvent goToJailEvent = new MyEvent();
        goToJailEvent.setGameName(gameName);
        goToJailEvent.setEventID(++eventIdGenerator);
        goToJailEvent.setEventType(MyEvent.GO_TO_JAIL);
        goToJailEvent.setPlayerName(playerName);
        
        return goToJailEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * create a prompt buy asset event
     * @param gameName the name of the game
     * @param player the player to prompt
     * @param timerSec the delay in seconds of the countdown timer
     * @return a "prompt buy asset" event
     */
    public static Event createPromptBuyAssetEvent(String gameName, Player player, int timerSec)
    {
        MyEvent promptBuyAssetEvent = new MyEvent();
        promptBuyAssetEvent.setGameName(gameName);
        promptBuyAssetEvent.setEventID(++eventIdGenerator);
        promptBuyAssetEvent.setEventType(MyEvent.PROMPT_BUY_ASSET);
        promptBuyAssetEvent.setPlayerName(player.getName());
        promptBuyAssetEvent.setTimeoutCount(timerSec);
        promptBuyAssetEvent.setBoardSquareID(player.getPosition());
        
        return promptBuyAssetEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a prompt buy house event
     * @param gameName the name of the game
     * @param player the player to prompt
     * @param timerSec the delay in seconds of the countdown timer
     * @return a "prompt buy house" event
     */
    public static Event createPromptBuyHouseEvent(String gameName, Player player, int timerSec)
    {
        MyEvent promptBuyHouseEvent = new MyEvent();
        promptBuyHouseEvent.setGameName(gameName);
        promptBuyHouseEvent.setEventID(++eventIdGenerator);
        promptBuyHouseEvent.setEventType(MyEvent.PROMPT_BUY_HOUSE);
        promptBuyHouseEvent.setPlayerName(player.getName());
        promptBuyHouseEvent.setTimeoutCount(timerSec);
        promptBuyHouseEvent.setBoardSquareID(player.getPosition());
        
        return promptBuyHouseEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates an asset bought event
     * @param gameName the name of the game
     * @param player the player that bought the asset
     * @return an "asset bought" event
     */
    public static Event createAssetBoughtEvent(String gameName, Player player)
    {
        MyEvent assetBoughtMessageEvent = new MyEvent();
        assetBoughtMessageEvent.setGameName(gameName);
        assetBoughtMessageEvent.setEventID(++eventIdGenerator);
        assetBoughtMessageEvent.setEventType(MyEvent.ASSET_BOUGHT);
        assetBoughtMessageEvent.setPlayerName(player.getName());
        assetBoughtMessageEvent.setBoardSquareID(player.getPosition());
        
        return assetBoughtMessageEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * create a house bought event
     * @param gameName the name of the game
     * @param player the player that bought a house
     * @return a "house bought" event
     */
    public static Event createHouseBoughtEvent(String gameName, Player player)
    {
        MyEvent houseBoughtMessageEvent = new MyEvent();
        houseBoughtMessageEvent.setGameName(gameName);
        houseBoughtMessageEvent.setEventID(++eventIdGenerator);
        houseBoughtMessageEvent.setEventType(MyEvent.HOUSE_BOUGHT);
        houseBoughtMessageEvent.setPlayerName(player.getName());
        houseBoughtMessageEvent.setBoardSquareID(player.getPosition());
        
        return houseBoughtMessageEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a surprise card event
     * @param gameName the name of the game
     * @param playerName the name of the player that picked the card
     * @param cardText the text of the card
     * @return a "surprise card" event
     */
    public static Event createSurpriseCardEvent(String gameName, String playerName, String cardText)
    {
        MyEvent surpriseCardEvent = new MyEvent();
        surpriseCardEvent.setGameName(gameName);
        surpriseCardEvent.setEventID(++eventIdGenerator);
        surpriseCardEvent.setEventType(MyEvent.SURPRISE_CARD);
        surpriseCardEvent.setPlayerName(playerName);
        surpriseCardEvent.setEventMessage(cardText);
        
        return surpriseCardEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a warrant card event
     * @param gameName the name of the game
     * @param playerName the name of the player that picked the card
     * @param cardText the text of the card
     * @return a "warrant card" event
     */
    public static Event createWarrantCardEvent(String gameName, String playerName, String cardText)
    {
        MyEvent warrantCardEvent = new MyEvent();
        warrantCardEvent.setGameName(gameName);
        warrantCardEvent.setEventID(++eventIdGenerator);
        warrantCardEvent.setEventType(MyEvent.WARRANT_CARD);
        warrantCardEvent.setPlayerName(playerName);
        warrantCardEvent.setEventMessage(cardText);
        
        return warrantCardEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a get out of jail card event
     * @param gameName the name of the game
     * @param playerName the player that picked the card
     * @return a "get out of jail" card event
     */
    public static Event createGetOutOfJailCardEvent(String gameName, String playerName)
    {
        MyEvent getOutOfJailCardEvent = new MyEvent();
        getOutOfJailCardEvent.setGameName(gameName);
        getOutOfJailCardEvent.setEventID(++eventIdGenerator);
        getOutOfJailCardEvent.setEventType(MyEvent.GET_OUT_OF_JAIL);
        getOutOfJailCardEvent.setPlayerName(playerName);
        
        return getOutOfJailCardEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a payment to or from treasury event
     * @param gameName the name of the game
     * @param playerName the player that recieves/pays
     * @param payment the payment amount
     * @param isPaymentFromUser true if the player has to pay
     * @return a "payment to or from treasury" event
     */
    public static Event createPaymentToOrFromTreasuryEvent(String gameName, String playerName, int payment, boolean isPaymentFromUser)
    {
        MyEvent paymentFromTreasuryEvent = new MyEvent();
        paymentFromTreasuryEvent.setGameName(gameName);
        paymentFromTreasuryEvent.setEventID(++eventIdGenerator);
        paymentFromTreasuryEvent.setEventType(MyEvent.PAYMENT);
        paymentFromTreasuryEvent.setPlayerName(playerName);
        paymentFromTreasuryEvent.setPaymentAmount(payment);
        paymentFromTreasuryEvent.setPaymemtFromUser(isPaymentFromUser);
        paymentFromTreasuryEvent.setPaymentToOrFromTreasury(true);
        
        return paymentFromTreasuryEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a payment to or from player event
     * @param gameName the name of the game
     * @param playerName the name of the player that receives/pays
     * @param otherPlayer the other player
     * @param payment the payment amount
     * @param isPaymentFromUser true if the player has to pay
     * @return a "payment to or from player" event
     */
    public static Event createPaymentToOrFromPlayerEvent(String gameName, String playerName, String otherPlayer, int payment, boolean isPaymentFromUser)
    {
        MyEvent givePaymentToPlayerEvent = new MyEvent();
        givePaymentToPlayerEvent.setGameName(gameName);
        givePaymentToPlayerEvent.setEventID(++eventIdGenerator);
        givePaymentToPlayerEvent.setEventType(MyEvent.PAYMENT);
        givePaymentToPlayerEvent.setPlayerName(playerName);
        givePaymentToPlayerEvent.setPaymentToPlayerName(otherPlayer);
        givePaymentToPlayerEvent.setPaymentAmount(payment);
        givePaymentToPlayerEvent.setPaymemtFromUser(isPaymentFromUser);
        givePaymentToPlayerEvent.setPaymentToOrFromTreasury(false);
      
        return givePaymentToPlayerEvent;
    }
    
    //---------------------------------------------------------------------
    
    /**
     * creates a player used pardon card event
     * @param gameName the name of the game
     * @param playerName the name of the player
     * @return a "player used pardon card" event
     */
    public static Event createPlayerUsedPardonCardEvent(String gameName, String playerName)
    {
        MyEvent playerUsedPardonCardEvent = new MyEvent();
        playerUsedPardonCardEvent.setGameName(gameName);
        playerUsedPardonCardEvent.setEventID(++eventIdGenerator);
        playerUsedPardonCardEvent.setEventType(MyEvent.USED_JAIL_CARD);
        playerUsedPardonCardEvent.setPlayerName(playerName);
        
        return playerUsedPardonCardEvent;
    }
}
