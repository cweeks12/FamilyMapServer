package familyserver.request;

/** Class that holds everything for a login request. It only has getters because it's reading a request.*/

public class LoginRequest{

    /** User's username. */
    public String userName;
    /** User's password. */
    public String password;

    /**
     * Constructor that builds the object from a json string.
     *
     * @param jsonString String holding json contents of object.
     */

    public LoginRequest(String jsonString){
    }

    public String getUsername(){
        return userName;
    }

    public String getPassword(){
        return password;
    }
}
