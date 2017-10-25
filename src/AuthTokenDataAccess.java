package familyserver.access;

import familyserver.error.InvalidAuthTokenError;
import familyserver.model.AuthToken;
import familyserver.error.InternalServerError;
import familyserver.util.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the database for AuthToken objects.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class AuthTokenDataAccess{

    // Driver string for the class
    private final String driver = "org.sqlite.JDBC";

    // Holds the name of the database
    private String dbName;
    private int expiry;

    /** Builds a new auth token data access object to interact with user database.
     *
     * @param databasePath Path to the database.
     */
    public AuthTokenDataAccess(String databasePath){

        try{
            Class.forName(driver);
        }
        catch (ClassNotFoundException e){
            System.out.println("Error finding the SQLite driver.");
        }

        dbName = "jdbc:sqlite:"+databasePath;
    }

    /** Generates a new authorization token for the given user. This function validates the username and password,
     * then generates a new token for them. It adds it to the Auth Token database.
     *
     * @param userName The username of the person making the request.
     * @return The new authorization token generated.
     */
 
    public String newAuthToken(String userName) throws InternalServerError{

        AuthToken newToken = new AuthToken(userName);

        try (Connection connection = DriverManager.getConnection(dbName)){

            String insert = "INSERT INTO authToken VALUES (?, ?)";

            try{
                PreparedStatement stmt = connection.prepareStatement(insert);
                stmt.setString(1, newToken.getToken());
                stmt.setString(2, newToken.getUsername());

                stmt.executeUpdate();
                stmt.close();
            }
            catch(SQLException e){
                 throw new InternalServerError("Error Updating the fields and doing the update.");
            }

        }

        catch(SQLException e){
             throw new InternalServerError("The connection to database failed.");
        }

        return newToken.getToken();
    }


    /** Finds the user associated with the given token. This function will try to find the user who owns the given token.
     *
     * @param authToken The authentication token you're finding the user for.
     * @return The username of the user who owns the token. Or null if the token is invalid.
     */

    public String userForToken(String authToken) throws InternalServerError{

        if (authToken == null){
            return null;
        }

        ResultSet queryResult = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        String user = null;

        try {
            connection = DriverManager.getConnection(dbName);
        }
        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }

        String query = "SELECT * FROM authToken WHERE authToken = ? ";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, authToken);

            queryResult = stmt.executeQuery();
        }
        catch(SQLException e){
             throw new InternalServerError("Error querying the database." + e.getMessage());
        }

        try{
            user = queryResult.getString("username");
        }
        catch (SQLException e){
            // If there is an exception here, it's because there are no results in the set.
            user = null;
        }

        try{
            queryResult.close();
            stmt.close();
            connection.close();
            connection = null;
        }
        catch (SQLException e){
             throw new InternalServerError("Error closing connection." + e.getMessage());
        }

        return user;
    }


    /** Gets the AuthToken object associated with the given token string.
     *
     * @param authToken The token ID you're looking for.
     * @return The AuthToken object that has that string.
     */

    public AuthToken getTokenByTokenValue(String authToken) throws InternalServerError{

        if (authToken == null){
            return null;
        }

        ResultSet queryResult = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        String user = null;

        try {
            connection = DriverManager.getConnection(dbName);
        }
        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }

        String query = "SELECT * FROM authToken WHERE authToken = ? ";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, authToken);

            queryResult = stmt.executeQuery();
        }
        catch(SQLException e){
             throw new InternalServerError("Error querying the database." + e.getMessage());
        }

        try{
            user = queryResult.getString("username");
        }
        catch (SQLException e){
            // If there is an exception here, it's because there are no results in the set.
            return null;
        }

        try{
            queryResult.close();
            stmt.close();
            connection.close();
            connection = null;
        }
        catch (SQLException e){
             throw new InternalServerError("Error closing connection." + e.getMessage());
        }

        return new AuthToken(user, authToken);
    }

    /**
     * Drops all tokens in the database. This is called when /clear is requested.
     */

    public void deleteAllAuthTokens() throws InternalServerError{
        try (Connection connection = DriverManager.getConnection(dbName)){

            String delete = "DELETE FROM authToken";

            try{
                PreparedStatement stmt = connection.prepareStatement(delete);

                stmt.executeUpdate();
                stmt.close();
            }
            catch(SQLException e){
                throw new InternalServerError("Error querying the database.");
            }

        }

        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }
    }
} 
