package familyserver.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import familyserver.util.Decoder;


/**
 * Class that helps with generation of names for family history.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class NameGenerator{

    /** List that holds all the names to choose from. */
    private List<String> data;

    /**
     * Returns a random name from the list of names.
     *
     * @return A random name from the list.
     */

    public String getRandomName(){
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }

}
