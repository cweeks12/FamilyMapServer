package familyserver.model;

import familyserver.util.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;


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
    private long expireTime;


    /** Constructs an AuthToken based on an sql result set.*/
    public AuthToken(ResultSet rs){
        try {
            this.token = rs.getString("authToken");
            this.username = rs.getString("username");
            this.expireTime = rs.getLong("expiryTime");
        }
        catch (SQLException e){
            System.out.println("There was an error creating the AuthToken");
        }
    }

    /** Constructs an AuthToken given a username and a time until it expires. */
    public AuthToken(String username, long timeUntilExpiry){
        this.token = Utils.generateId();
        this.username = username;

        // Create the timestamp when the token should expire.
        this.expireTime = (System.currentTimeMillis()/1000) + timeUntilExpiry;
    }

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

    public long getExpireTime(){
        return expireTime;
    }

    public void setExpireTime(long expireTime){
        this.expireTime = expireTime;
    }
}
