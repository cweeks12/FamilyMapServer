package familyserver;

import java.util.List;
import java.util.ArrayList;

/**
 * Stores and generates locations for family generation
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
    

    /** Gets a random location to use. This is to help generate random family data 
     *
     * @return Location randomly selected.
     */

    Location getRandomLocation(){
        return new Location();
    }
    
}
