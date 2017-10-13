package familyserver;

/**
 * Accesses the database for AuthToken objects.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class AuthTokenDataAccess{

    private int expiry;

    /** Default constructor. Defaults seconds to 3600. */
    public AuthTokenDataAccess(){
        this.expiry = 3600;
    }


    /** Builds a data access object that generates tokens with a set time to expire
     *
     * @param expiry The number of seconds an auth token should stay valid.
     */

    public AuthTokenDataAccess(int expiry){
        this.expiry = expiry;
    }


    /** Generates a new authorization token for the given user. This function validates the username and password,
     * then generates a new token for them. It adds it to the Auth Token database.
     *
     * @param userName The username of the person making the request.
     * @param password The password of the person making the request.
     * @return The new authorization token generated.
     */
 
    public String newAuthToken(String userName, String password){
        return null;
    }


    /** Checks the given token against the database to see if it is valid. This function goes into the database
     * and checks if the given auth token is there and not expired. Throws an exception if it's an invalid token.
     *
     * @param authToken The token to check.
     * @return True if the authorization token is valid.
     */ 

    public boolean validateAuthToken(String authToken) throws InvalidAuthTokenError{
        return false;
    }

    /** Finds the user associated with the given token. This function will try to find the user who owns the given token.
     *
     * @param authToken The authentication token you're finding the user for.
     * @return The username of the user who owns the token.
     */

    public String userForToken(String authToken){
        return null;
    }


    /** Gets the AuthToken object associated with the given token string.
     *
     * @param authToken The token ID you're looking for.
     * @return The AuthToken object that has that string.
     */

    public AuthToken getTokenByTokenValue(String authToken){
        return null;
    }

    /**
     * Drops all tokens in the database. This is called when /clear is requested.
     */

    public void deleteAllAuthTokens(){
    }
} 
