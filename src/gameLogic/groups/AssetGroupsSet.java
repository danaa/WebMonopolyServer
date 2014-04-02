package gameLogic.groups;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * this class represents an iterable set of asset groups
 * @author Dana Akerman
 */
public class AssetGroupsSet implements Iterable<AssetGroup> 
{
    // data members are References
    //-------------------------------------------------------------------

    private ArrayList<Country> _countries;
    private AssetGroup _utils;
    private AssetGroup _transportation;

    //c'tor
    //-------------------------------------------------------------------
    
    /**
     * constructs a new asset group set
     * @param countries the countries array list of the monopoly game
     * @param utils the utils of the monopoly game
     * @param trans the transportation of the monopoly game
     * @throws NullPointerException if any of the parameters is null
     */
    public AssetGroupsSet(ArrayList<Country> countries, AssetGroup utils, AssetGroup trans) 
    {
        this.setCountries(countries);
        this.setUtils(utils);
        this.setTransportation(trans);
    }

    // methods
    //-------------------------------------------------------------------
    
    /**
     * sets the countries
     * @param countries the countries of the monopoly game
     * @throws NullPointerException if 'countries' is null
     */
    public final void setCountries(ArrayList<Country> countries) 
    {
        if (countries != null) 
        {
            _countries = countries;
        } 
        else 
            throw new NullPointerException("countries is null");
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the utils
     * @param utils the utils of the monopoly game
     * @throws NullPointerException if 'utils' is null
     */
    public final void setUtils(AssetGroup utils) 
    {
        if (utils != null) 
        {
            _utils = utils;
        } 
        else
            throw new NullPointerException("utils is null");
    }

    //-------------------------------------------------------------------
    
    /**
     * sets the transportation
     * @param trans the transportation of the monopoly game
     * @throws NullPointerException if 'trans' is null
     */
    public final void setTransportation(AssetGroup trans) 
    {
        if (trans != null) 
        {
            _transportation = trans;
        } 
        else 
            throw new NullPointerException("trans is null");
    }

    //-------------------------------------------------------------------
   
    @Override
    public Iterator<AssetGroup> iterator() 
    {
        return new Iter();
    }

    // iterator class
    //===================================================================
    
    /**
     * this class represents an iterator of the asset groups set
     * @author Dana Akerman
     */
    private class Iter implements Iterator<AssetGroup> 
    {
        
        // data members
        //---------------------------------------------------------------

        private AssetGroup _curr;
        private Iterator<Country> _iter;

        // c'tor
        //---------------------------------------------------------------
        
        /**
         * constructs a new iterator
         */
        private Iter() 
        {
            _iter = _countries.iterator();
            _curr = null;
        }

        // methods
        //---------------------------------------------------------------
        
        @Override
        public boolean hasNext() 
        {
            if (_curr == _transportation) 
            {
                return false;
            }
            return true;
        }

        //-------------------------------------------------------------------
        
        @Override
        public AssetGroup next() 
        {
            if (!_iter.hasNext() && _curr == null) 
            {
                _curr = _utils;
            } 
            else if (_curr == _utils) 
            {
                _curr = _transportation;
            } 
            else if (_curr == _transportation) 
            {
                _curr = null;
            } 
            else 
            {
                return _iter.next();
            }
            return _curr;
        }

        //-------------------------------------------------------------------
        
        @Override
        public void remove(){} // not implemented
    }
}
