package familyserver;

import java.util.List;
import java.util.ArrayList;

/** Class that holds everything for a people response. */

public class PeopleResponse{

    /** Array of Person objects to load in. */
    List<Person> data;

    /**
     * Constructor that creates the object to return in response to the GET request.
     *
     * @param people A list of people to return in the response.
     */
    
    public PeopleResponse(List<Person> people){
    }
}
