package gameLogic.squares;


import gameLogic.Game;
import java.math.BigInteger;
import java.util.List;
import gameLogic.groups.AssetGroup;


/**
 * this class represents a city in a monopoly game
 * @author Dana Akerman
 */
public class CityAsset extends SingleAsset 
{
    
    // constants
    //------------------------------------------------------------

    static final int MAX_HOUSES = 3;
    
    // data members
    //------------------------------------------------------------
    
    private int _cityCostPrice;
    private int _houseCostPrice;
    private int[] _rentPrice; // 0 = no houses, 1 = one house....
    private int _numHouses;

    // c'tor
    //------------------------------------------------------------
    
    /**
     * creates a new city asset
     * @param group the group of the asset
     * @param game the game this city belongs to
     * @throws NullPointerException if group or game are null
     */
    
    public CityAsset(AssetGroup group, Game game) 
    {
        super(group, game);
        _rentPrice = new int[MAX_HOUSES + 1];
        _numHouses = 0;
    }

    // methods
    //------------------------------------------------------------
    
    /**
     * initializes the city from the XMLcity City object created from the 
     * schema generated class 'City'
     * @param XMLcity the XMLcity City object created from the schema generated class 'City'
     * @throws NullPointerException if 'XMLcity' is null
     */
    public void init(generated.City XMLcity) 
    {
        if (XMLcity != null) 
        {
            this.setName(XMLcity.getName());
            this.setCosts(XMLcity.getCosts());
        } 
        else 
            throw new NullPointerException("XMLcity is null");
    }

    //-------------------------------------------------------------------
    
    @Override
    public int getCostPrice() 
    {
        if (_owner == null) 
        {
            return _cityCostPrice;
        } 
        else 
        {
            return _houseCostPrice;
        }
    }

    //-------------------------------------------------------------------
    
    @Override
    public int getRentPrice(){return _rentPrice[_numHouses];}

    //-------------------------------------------------------------------
    
    /**
     * returns the number of houses in the city
     * @return the number of houses in the city
     */
    public int getNumHouses(){return _numHouses;}

    //-------------------------------------------------------------------
    
    @Override
    public boolean isCity(){return true;}

    //-------------------------------------------------------------------
    
    /**
     * sets the cost of the city
     * @param cost the cost of the city
     * @throws IllegalArgumentException if 'cost' is non-positive
     */
    public void setCityCostPrice(int cost) 
    {
        if (cost > 0) 
        {
            _cityCostPrice = cost;
        } 
        else 
            throw new IllegalArgumentException("illegal cost input");
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the cost of a house in the city
     * @param cost the cost of a house in the city
     * @throws IllegalArgumentException if 'cost' is non-positive
     */
    public void setHouseCostPrice(int cost) 
    {
        if (cost > 0) 
        {
            _houseCostPrice = cost;
        } 
        else 
            throw new IllegalArgumentException("illegal cost input");
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the rents of the city according to the number of houses
     * @param rent a list of BigInteger from the City object created from the class City
     * generated from the XSD schema
     * @throws IllegalArgumentException if the rent prices are illegal
     * @throws NullPointerException if rent is null
     */
    public void setCosts(List<BigInteger> rent) 
    {
        if (rent == null) 
            throw new NullPointerException("rent is null");

        this.setCityCostPrice(rent.get(0).intValue());
        this.setHouseCostPrice(rent.get(1).intValue());
        
        for (int i = 0; i < MAX_HOUSES + 1; i++) 
        {
            if (rent.get(i).intValue() > 0) 
            {
                _rentPrice[i] = rent.get(i + 2).intValue();
            } 
            else 
                throw new IllegalArgumentException("illegal rent input");
        }
    }

    //-------------------------------------------------------------------
    
    /**
     * increases the number of houses by one
     */
    public void addHouse() 
    {
        _numHouses++;
    }

    //-------------------------------------------------------------------
    
    @Override
    public String toString() 
    {
        return (_name + ", " + _group.getName() + "\nowner: "
                + getOwnerName());
    }
}
