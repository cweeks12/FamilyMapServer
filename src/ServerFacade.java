package familyserver;


/** 
 * Interface for the server to interact with everything in the 
 * server and database.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */
public class ServerFacade{


    /**
     * Registers a new user in the database.
     *
     * @param request Contains the information needed to register a new user.
     * @return A login response with the new auth token.
     */

    public LoginResponse register(RegisterRequest request){
        return null;
    }


    /**
     * Performs a login and returns a response.
     *
     * @param request Contains the information needed to login.
     * @return A login response with the new auth token.
     */

    public LoginResponse login(LoginRequest request){
        return null;
    }


    /**
     * Clears all of the data out of the database.
     *
     * @return A message whether or not the request worked.
     */

    public MessageResponse clear(){
        return null;
    }


    /**
     * Fills the database with random family information for the given user.
     *
     * @param username The username whose family you are filling.
     * @param generations The number of generations to fill.
     * @return A message response telling whether or not the request worked.
     */

    public MessageResponse fill(String username, int generations){
        return null;
    }


    /**
     * Fills the database with the given family information.
     *
     * @param request A LoadRequest with all the data to add into the database.
     * @return A message response telling whether or not the request worked.
     */

    public MessageResponse load(LoadRequest request){
        return null;
    }


    /**
     * Finds the person associated with the personId and sends the information back.
     *
     * @param token The authorization token used to request the data.
     * @param personId The ID of the person you're searching for.
     * @return A PersonResponse object with the requested person's information in it.
     */

    public PersonResponse person(AuthToken token, String personId){
        return null;
    }


    /**
     * Finds all people associated with the user indicated by the authentication token.
     *
     * @param token The authentication token from the user requesting.
     * @return A PeopleResponse object with all of the data of all of the people associated with the requesting user.
     */

    public PeopleResponse people(AuthToken token){
        return null;
    }


    /**
     * Finds the event associated with the eventId and sends the information back.
     *
     * @param token The authorization token used to request the data.
     * @param eventId The ID of the event you're searching for.
     * @return An EventResponse object with the requested event's information in it.
     */

    public EventResponse event(AuthToken token, String eventId){
        return null;
    }


-    /**
-     * Finds all events associated with the user indicated by the authentication token.
-     *
-     * @param token The authentication token from the user requesting.
-     * @return An EventsResponse object with all of the data of all of the events associated with the requesting user.
-     */

    public EventsResponse events(AuthToken token){
        return null;
    }

} 
