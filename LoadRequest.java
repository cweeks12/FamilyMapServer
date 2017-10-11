package familyserver;

import java.util.List;
import java.util.ArrayList;

/** Class that holds everything for a load request. */

public class LoadRequest{

    /** Array of User objects to load in. */
    List<User> users;

    /** Array of Person objects to load in. */
    List<Person> persons;

    /** Array of Event objects to load in. */
    List<Event> events;

    /**
     * Constructor that builds the object from a json string.
     *
     * @param jsonString String holding json contents of object.
     */
    
    public LoadRequest(String jsonString){
    }
}
