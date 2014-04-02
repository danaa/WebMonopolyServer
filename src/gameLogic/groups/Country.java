package gameLogic.groups;


import gameLogic.Game;
import gameLogic.squares.CityAsset;


/**
 * this class represents a country in a monopoly game
 * @author Dana Akerman
 */
public class Country extends AssetGroup 
{

    // c'tor
    //-------------------------------------------------------------------
    
    /**
     * construct a country
     * @param game the game this country belongs to
     * @throws NullPointerException if game is null
     */
    public Country(Game game) 
    {
        super(game);
    }

    // methods
    //-------------------------------------------------------------------
    
    /**
     * initializes the country from the XMLcountry Country object created from the 
     * schema generated class 'Country'
     * @param XMLcountry the XMLcountry Country object created from the schema generated class 'Country'
     * @throws NullPointerException if 'XMLcountry' is null
     */
    public void init(generated.Country XMLcountry) 
    {
        if (XMLcountry == null) 
            throw new NullPointerException("XMLcountry is null");

        int numCities = XMLcountry.getSize();
        this.setName(XMLcountry.getName());

        // init cities
        for (int j = 0; j < numCities; j++) 
        {
            generated.City XMLcity = XMLcountry.getCity().get(j);
            CityAsset myCity = new CityAsset(this, _game);
            myCity.init(XMLcity);
            this.addAsset(myCity);
        }
    }

    //-------------------------------------------------------------------
    
    /**
     * returns true if this country has houses, otherwise return false
     * @return true if this country has houses, otherwise return false
     */
    public boolean hasHouses() 
    {
        int size = _assets.size();

        for (int i = 0; i < size; i++) 
        {
            if (((CityAsset) _assets.get(i)).getNumHouses() > 0) 
            {
                return true;
            }
        }
        return false;
    }

    //-------------------------------------------------------------------
    
    @Override
    public String toString(){return ("country: " + _name);}
}
