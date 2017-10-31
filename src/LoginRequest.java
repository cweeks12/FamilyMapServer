package familyserver.request;

/** Class that holds everything for a login request. It only has getters because it's reading a request.*/

public class LoginRequest{

    /** User's username. */
    public String userName;
    /** User's password. */
    public String password;

    public String getUsername(){
        return userName;
    }

    public String getPassword(){
        return password;
    }
}
