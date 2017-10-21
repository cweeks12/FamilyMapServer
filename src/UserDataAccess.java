package familyserver.access;

import familyserver.model.User;
import familyserver.request.RegisterRequest;

/**
 * Accesses the database for User objects.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class UserDataAccess{

    /** Builds a new user data access object to interact with user database.*/
    public UserDataAccess(){
    }

    /** 
     * Adds a new user to the database. Also assigns a unique ID to the user created.
     *
     * @param newUser The request with the information to create a user.
     * @return The ID generated for the new user in the database.
     */

    public String createNewUser(RegisterRequest newUser){
        return null;
    }


    /** 
     * Queries the database and returns the User object form the username.
     *
     * @param userName The username to look up in the database.
     * @return The user that corresponds to the given username
     */

    public User getUserByUsername(String userName){
        return null;
    }


    /**
     * Drops all users in the database. This is called when /clear is requested.
     */

    public void deleteAllUsers(){
    }

} 
