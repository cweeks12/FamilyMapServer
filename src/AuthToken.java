package familyserver;

/**
 * Contains all information about one authentication token.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class AuthToken{

    /** The unique token generated with login. */
    private String token;

    /** User the token belongs to. */
    private String username;

    /** Time the token expires. Should be one hour after initial login */
    private int expireTime;

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getExpireTime(){
        return expireTime;
    }

    public void setExpireTime(int expireTime){
        this.expireTime = expireTime;
    }
}
