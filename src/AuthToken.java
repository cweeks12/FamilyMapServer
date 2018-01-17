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

    /** Constructs an AuthToken given a username and a time until it expires. */
    public AuthToken(String username){
        this.token = Utils.generateId();
        this.username = username;
    }

    /** Constructs an AuthToken given all of the parameters. */
    public AuthToken(String username, String token){
        this.token = token;
        this.username = username;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (o.getClass() != this.getClass()){
            return false;
        }
        AuthToken a = (AuthToken) o;

        return this.token.equals(a.getToken()) && this.username.equals(a.getUsername());
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    public String getToken(){
        return token;
    }

    public void setToken(String token) throws IllegalArgumentException{
        if (token == null){
            throw new IllegalArgumentException();
        }

        this.token = token;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) throws IllegalArgumentException{
        if (username == null){
            throw new IllegalArgumentException();
        }
        this.username = username;
    }

}
