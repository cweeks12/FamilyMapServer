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
     * @param req Contains the information needed to register a new user.
     * @return A login response with the new auth token.
     */

    public LoginResponse register(RegisterRequest req){
    }


    /**
     * Performs a login and returns a response.
     *
     * @param req Contains the information needed to login.
     * @return A login response with the new auth token.
     */

    public LoginResponse login(LoginRequest req){
    }


    /**
     * Clears all of the data out of the database.
     *
     * @return A message whether or not the request worked.
     */

    public MessageResponse clear(){
    }


    /**
     * Fills the database with random family information for the given user.
     *
     * @param username The username whose family you are filling.
     * @param generations The number of generations to fill.
     * @return A message response telling whether or not the request worked.
     */

    public MessageResponse fill(String username, int generations){
    }


    /**
     * Fills the database with the given family information.
     *
     * @param req A LoadRequest with all the data to add into the database.
     * @return A message response telling whether or not the request worked.
     */

    public MessageResponse load(LoadRequest req){
    }

    public PersonResponse person(AuthToken token, String personId){
    }

    public PeopleResponse people(AuthToken token){
    }

    public EventResponse event(AuthToken token, String eventId){
    }

    public EventsResponse events(AuthToken token){
    }

} 
