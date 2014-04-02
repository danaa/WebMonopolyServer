package gameLogic.squares;


import gameLogic.Game;
import gameLogic.groups.AssetGroup;
import gameLogic.groups.SimpleAssetGroup;
import gameLogic.players.Player;
import monopoly.Event;
import monopoly.EventsFactory;


/**
 * this abstract class represents a single asset square in a monopoly game
 * @author Dana Akerman
 */
public abstract class SingleAsset extends Square 
{

    // constants
    //----------------------------------------------------------------
    
    public static final int ASSET = 0;
    public static final int HOUSE = 1;
    
    // data members
    //----------------------------------------------------------------
    
    protected String _name;
    protected Player _owner;
    protected AssetGroup _group;

    // c'tor
    //----------------------------------------------------------------
    
    /**
     * constructs a new single asset. called only by derived classes
     * @param group the group this single asset belongs to
     * @param game the game this asset belongs to
     * @throws NullPointerException if game or group are null
     */
    protected SingleAsset(AssetGroup group, Game game) 
    {
        super(game);
        _owner = null;
        this.setGroup(group);
    }

    // methods
    //----------------------------------------------------------------
    
    /**
     * returns the owner of the asset
     * @return the owner of the asset
     */
    public Player getOwner(){return _owner;}

    //---------------------------------------------------------
    
    /**
     * returns the name of the asset
     * @return the name of the asset
     */
    public String getName(){return _name;}

    //---------------------------------------------------------
    
    /**
     * returns the group this asset belongs to
     * @return the group this asset belongs to
     */
    public AssetGroup getGroup(){return _group;}

    //---------------------------------------------------------
    
    /**
     * returns the price of the asset
     * @return the price of the asset
     */
    public abstract int getCostPrice();

    //---------------------------------------------------------
    
    /**
     * returns the rent of the asset
     * @return the rent of the asset
     */
    public abstract int getRentPrice();

    //---------------------------------------------------------
    
    /**
     * returns true if this asset is a city, false otherwise
     * @return true if this asset is a city, false otherwise
     */
    public abstract boolean isCity();

    //---------------------------------------------------------
    
    @Override
    public void playerArrived(Player player) 
    {
        if (player == null) 
            throw new NullPointerException("player is null");
        

        int cost = this.getCostPrice();
        if (_owner == null) // player can buy this asset
        {

            if (player.buyDecision(this, ASSET)) 
            {
                player.reduceCash(cost);
                this.setOwner(player);

                // payment and asset bought events
                Event paymentEvent = EventsFactory.createPaymentToOrFromTreasuryEvent(_game.getName(), player.getName(), cost, true);
                Event assetBoughtEvent = EventsFactory.createAssetBoughtEvent(_game.getName(), player);
                _game.addEvent(paymentEvent);
                _game.addEvent(assetBoughtEvent);

            }
        } 
        
        else if (_owner == player) // if the asset owned by player he can buy houses
        {
            
            if (this.isCity() && _group.areAllAssetsOwnedBySamePlayer() && ((CityAsset) this).getNumHouses() < CityAsset.MAX_HOUSES) 
            {
                
                if (player.buyDecision(this, HOUSE)) 
                {
                    player.reduceCash(cost);
                    ((CityAsset) this).addHouse();

                    // payment and house bought events
                    Event paymentEvent = EventsFactory.createPaymentToOrFromTreasuryEvent(_game.getName(), player.getName(), cost, true);
                    Event houseBoughtEvent = EventsFactory.createHouseBoughtEvent(_game.getName(), player);
                    _game.addEvent(paymentEvent);
                    _game.addEvent(houseBoughtEvent);

                }
            }
        } 
        
        else // player has to pay rent
        {
            int rent;

            // pay a special rent for the whole group
            if ((!isCity()) && (_group.areAllAssetsOwnedBySamePlayer())) 
            {
                rent = ((SimpleAssetGroup) _group).getSpecialRentPriceForGroup();
            } 
            else 
            {
                rent = this.getRentPrice();
            }

            int payment = player.reduceCash(rent);

            if (payment > 0) 
            {
                this.getOwner().addCash(payment);
                Event paymentEvent = EventsFactory.createPaymentToOrFromPlayerEvent(_game.getName(), player.getName(), this.getOwnerName(), payment, true);
                _game.addEvent(paymentEvent);
            }
        }
    }

    //---------------------------------------------------------
    
    /**
     * sets the group
     * @param group the group this asset belongs to
     * @throws NullPointerException if 'group' is null
     */
    public final void setGroup(AssetGroup group) 
    {
        if (group != null) 
        {
            _group = group;
        } 
        else 
            throw new NullPointerException("group is null");
    }

    //---------------------------------------------------------
    
    /**
     * sets the owner of the asset
     * @param owner the owner of the asset
     */
    public void setOwner(Player owner) 
    {
        // can be null if no owner
        _owner = owner;
    }

    //---------------------------------------------------------
    
    /**
     * sets the name of the asset
     * @param name the name of the asset
     * @throws NullPointerException if 'name' is null
     */
    public void setName(String name) 
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
     * checks if the asset is owned by 'player'
     * @param player the player to check
     * @return true if the asset is owned by 'player', false otherwise
     */
    public boolean isOwnedBy(Player player) 
    {
        return (_owner == player);
    }

    //---------------------------------------------------------
    
    /**
     * returns the name of the owner
     * @return the name of the owner
     */
    public String getOwnerName() 
    {
        String owner;

        if (_owner != null) 
        {
            owner = _owner.getName();
        } 
        else 
        {
            owner = "none";
        }

        return owner;
    }
}
