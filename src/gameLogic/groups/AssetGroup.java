package gameLogic.groups;


import gameLogic.Game;
import java.util.ArrayList;
import gameLogic.players.Player;
import gameLogic.squares.SingleAsset;


/**
 * this abstract class represents a group of assets in a monopoly game
 * @author Dana Akerman
 */
public abstract class AssetGroup 
{
    // data members
    //-------------------------------------------------------

    protected String _name;
    protected ArrayList<SingleAsset> _assets;
    protected Game _game;

    // c'tor
    //-------------------------------------------------------
    
    /**
     * constructs a new asset group. called only by the derived classes
     * @param game the game this asset group belongs to
     * @throws NullPointerException if game is null
     */
    protected AssetGroup(Game game) 
    {
        _assets = new ArrayList<SingleAsset>();
        this.setGame(game);
    }

    // methods
    //-------------------------------------------------------
    /**
     * returns the name of the asset group
     * @return the name of the asset group
     */
    public String getName(){return _name;}

    //-------------------------------------------------------------------
    /**
     * returns the number of assets in this group
     * @return the number of assets in this group
     */
    public int getNumAssets(){return _assets.size();}

    //-------------------------------------------------------------------
    /**
     * gets an asset by the given index
     * @param index the index of the asset
     * @return the asset in the index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public SingleAsset getAssetByIndex(int index) 
    {
        if (index >= 0 || index < _assets.size()) 
        {
            return _assets.get(index);
        } 
        else 
            throw new IndexOutOfBoundsException("asset index is out of bounds");
    }

    //-------------------------------------------------------------------
    
    /**
     * adds an asset to the group
     * @param asset the asset to add
     * @throws NullPointerException if 'asset' is null
     */
    public void addAsset(SingleAsset asset) 
    {
        if (asset != null) 
        {
            _assets.add(asset);
        } 
        else 
            throw new NullPointerException("asset is null");
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the game
     * @param game the game the asset group belongs to
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
    
    //-------------------------------------------------------------------
    
    /**
     * sets the name of the group
     * @param name the name of the group
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

    //-------------------------------------------------------------------
    
    /**
     * checks if all the assets in the group belongs to the same player
     * @return true if all the assets in the group belongs to the same player, false otherwise
     */
    public boolean areAllAssetsOwnedBySamePlayer() 
    {
        if (_assets.size() > 0) 
        {
            Player player = _assets.get(0).getOwner();

            if (player == null) 
            {
                return false;
            }

            for (int i = 0; i < _assets.size(); i++) 
            {
                if (_assets.get(i).getOwner() != player) 
                {
                    return false;
                }
            }
            return true;
        } 
        else 
        {
            return false;
        }
    }
}
