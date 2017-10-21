package familyserver.response;

/** Class that holds everything for a login response. It only has setters because you're setting a response.*/

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
     * @param authToken The authorization token generated in the response.
     * @param userName The username used to log in.
     * @param personId The id associated with the user.
     */
    
    public LoginResponse(String authToken, String userName, String personId){
        this.authToken = authToken;
        this.userName = userName;
        this.personId = personId;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPersonId(String personId){
        this.personId = personId;
    }
}
