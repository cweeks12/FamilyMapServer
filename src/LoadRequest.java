package familyserver.request;

import familyserver.model.*;
import familyserver.response.*;
import java.util.List;
import java.util.ArrayList;

/** Class that holds everything for a load request. */

public class LoadRequest{

    /** Array of User objects to load in. */
    List<User> users;

    /** Array of Person objects to load in. */
    List<Person> persons;

    /** Array of Event objects to load in. */
    List<EventResponse> events;

    public List<User> getUsers(){
        return users;
    }

    public List<Person> getPersons(){
        return persons;
    }

    public List<EventResponse> getEvents(){
        return events;
    }
}
