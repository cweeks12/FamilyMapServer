package familyserver.util;

import familyserver.model.Location;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Stores and generates locations for family generation.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class LocationGenerator{

    /** Holds the locations to choose from. */
    private List<Location> data;

    /** Gets a random location to use. This is to help generate random family data.
     *
     * @return Location randomly selected.
     */

    public Location getRandomLocation(){
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }
}
