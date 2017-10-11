package familyserver;

import java.util.List;
import java.util.ArrayList;

/**
 * Stores and generates locations for family generation.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class LocationGenerator{

    /** Holds the locations to choose from. */
    private List<Location> data;


    /**
     * Fills the LocationGenerator with the data from the JSON.
     *
     * @param inputJson A string holding all of the json to be decoded.
     */

    public LocationGenerator(String inputJson){
    }
    

    /** Gets a random location to use. This is to help generate random family data.
     *
     * @return Location randomly selected.
     */

    public Location getRandomLocation(){
        return null;
    }

    /** Gets a nearby location to use. This helps generate realistic family data.
     *
     * @param loc A location that you want to find a nearby location to.
     * @return Location near or equal to the location passed in.
     */

    public Location getNearbyLocation(Location loc){
        return null;
    }
    
}
