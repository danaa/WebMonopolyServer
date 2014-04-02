package gameLogic.groups;


import gameLogic.Game;
import gameLogic.squares.SimpleAsset;


/**
 * this class represent a simple asset group(utils, transportation) in a monopoly game
 * @author Dana Akerman
 */
public class SimpleAssetGroup extends AssetGroup 
{
    
    // data members
    //---------------------------------------------------------------

    private int _rentForGroup;

    // c'tor
    //---------------------------------------------------------------
    
    /**
     * costructs a simple asset group
     * @param game the game this group belongs to
     * @throws NullPointerException if game is null
     */
    public SimpleAssetGroup(Game game) 
    {
        super(game);
    }

    // methods
    //---------------------------------------------------------------
    
    /**
     * initializes the simple asset group from the XMLgroup Group object created from the 
     * schema generated class 'Group'
     * @param XMLgroup the XMLgroup object created from the schema generated class 'Group'
     * @throws NullPointerException if 'XMLgroup' is null
     */
    public void init(generated.Group XMLgroup) 
    {
        if (XMLgroup == null)
            throw new NullPointerException("XMLgroup is null");

        int numAssets = XMLgroup.getSize();

        this.setName(XMLgroup.getName());
        this.setSpecialRentPriceForGroup(XMLgroup.getCost());

        for (int i = 0; i < numAssets; i++) 
        {
            generated.Asset XMLAsset = XMLgroup.getAsset().get(i);
            SimpleAsset myAsset = new SimpleAsset(this, _game);
            myAsset.init(XMLAsset);
            this.addAsset(myAsset);
        }
    }

    //---------------------------------------------------------
    
    /**
     * returns the rent in a situation that one players owns all the assets in the group
     * @return the rent in a situation that one players owns all the assets in the group
     */
    public int getSpecialRentPriceForGroup(){return _rentForGroup;}

    //---------------------------------------------------------
    
    /**
     * sets the special rent price
     * @param rent the rent price for whole group
     * @throws IllegalArgumentException if 'rent' is none-positive
     */
    public void setSpecialRentPriceForGroup(int rent) 
    {
        if (rent > 0) 
        {
            _rentForGroup = rent;
        }
        else 
            throw new IllegalArgumentException("illegal rent input");
    }

    //---------------------------------------------------------
    
    @Override
    public String toString(){return ("group: " + _name);}
}
