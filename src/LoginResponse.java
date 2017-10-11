package familyserver;

/** Class that holds everything for a login response. */

public class LoginResponse{

    /** User's newly generated auth token. */
    public String authToken;
    /** User's username. */
    public String userName;
    /** User's unique person ID. */
    public String personId;

    /**
     * Constructor that builds the object from a json string.
     *
     * @param jsonString String holding json contents of object.
     */
    
    public LoginResponse(String jsonString){
    }
}
