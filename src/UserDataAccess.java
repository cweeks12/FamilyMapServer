package familyserver.access;

import familyserver.error.InternalServerError;
import familyserver.model.User;
import familyserver.request.RegisterRequest;
import familyserver.util.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the database for User objects.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class UserDataAccess{

    /** Driver string for the class. */
    private final String driver = "org.sqlite.JDBC";

    /** Holds the name of the database. */
    private String dbName;


    /** Builds a new user data access object to interact with user database.
     *
     * @param databasePath Path to the database.
     */

    public UserDataAccess(String databasePath){

        try{
            Class.forName(driver);
        }
        catch (ClassNotFoundException e){
            System.out.println("Error finding the SQLite driver.");
        }

        dbName = "jdbc:sqlite:"+databasePath;

        String createTable = "CREATE TABLE IF NOT EXISTS user " +
                                "(username TEXT NOT NULL PRIMARY KEY, " +
                                "password TEXT NOT NULL, " +
                                "email TEXT NOT NULL, " +
                                "firstName TEXT NOT NULL, " +
                                "lastName TEXT NOT NULL, " +
                                "gender TEXT NOT NULL, " +
                                "personId TEXT NOT NULL)";

        Connection connection = null;
        try{
            connection = DriverManager.getConnection(dbName);

            PreparedStatement stmt = connection.prepareStatement(createTable);
            stmt.executeUpdate();
            stmt.close();

        }
        catch (SQLException e){
            System.out.println("Error creating database");
            System.out.println(e.getMessage());
        }
    }


    /**
     * Adds a new user to the database. Also assigns a unique ID to the user created.
     *
     * @param newUser The request with the information to create a user.
     * @return The ID generated for the new user in the database.
     */

    public String createNewUser(RegisterRequest newUser) throws InternalServerError{

        String newId = Utils.generateId();

        try (Connection connection = DriverManager.getConnection(dbName)){

            String insert = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";

            try{
                PreparedStatement stmt = connection.prepareStatement(insert);
                stmt.setString(1, newUser.getUserName());
                stmt.setString(2, newUser.getPassword());
                stmt.setString(3, newUser.getEmail());
                stmt.setString(4, newUser.getFirstName());
                stmt.setString(5, newUser.getLastName());
                stmt.setString(6, newUser.getGender());
                stmt.setString(7, newId);

                stmt.executeUpdate();
                stmt.close();
            }
            catch(SQLException e){
                String error = "Error updating the fields and doing the update." + e.getMessage();
                throw new InternalServerError(error);
            }
        }
        catch(SQLException e){
             throw new InternalServerError("The connection to database failed.");
        }

        return newId;
    }

    /**
     * Adds a new user to the database. Also assigns a unique ID to the user created.
     *
     * @param newUser The user object with the information to create a user.
     * @return The ID generated for the new user in the database.
     */
    public String createNewUser(User newUser) throws InternalServerError{

        try (Connection connection = DriverManager.getConnection(dbName)){

            String insert = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";

            try{
                PreparedStatement stmt = connection.prepareStatement(insert);
                stmt.setString(1, newUser.getUsername());
                stmt.setString(2, newUser.getPassword());
                stmt.setString(3, newUser.getEmail());
                stmt.setString(4, newUser.getFirstName());
                stmt.setString(5, newUser.getLastName());
                stmt.setString(6, newUser.getGender());
                stmt.setString(7, newUser.getId());

                stmt.executeUpdate();
                stmt.close();
            }
            catch(SQLException e){
                String error = "Error updating the fields and doing the update." + e.getMessage();
                throw new InternalServerError(error);
            }

        }
        catch(SQLException e){
             throw new InternalServerError("The connection to database failed.");
        }

        return newUser.getId();
    }


    /**
     * Queries the database and returns the User object form the username.
     *
     * @param userName The username to look up in the database.
     * @return The user that corresponds to the given username
     */

    public User getUserByUsername(String userName) throws InternalServerError{

        if (userName == null){
            return null;
        }

        ResultSet queryResult = null;
        Connection connection = null;
        PreparedStatement stmt = null;

        String username;
        String password;
        String email;
        String firstName;
        String lastName;
        String gender;
        String id;

        try {
            connection = DriverManager.getConnection(dbName);
        }
        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }

        String query = "SELECT * FROM user WHERE username = ? ";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, userName);

            queryResult = stmt.executeQuery();
        }
        catch(SQLException e){
             throw new InternalServerError("Error querying the database." + e.getMessage());
        }

        try{
            username = queryResult.getString("username");
            password = queryResult.getString("password");
            email = queryResult.getString("email");
            firstName = queryResult.getString("firstName");
            lastName = queryResult.getString("lastName");
            gender = queryResult.getString("gender");
            id = queryResult.getString("personId");
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

        return new User(username, password, email, firstName, lastName, gender, id);
    }


    /**
     * Decides whether the username and password are correct.
     *
     * @param username The username used for login.
     * @param password The password used for login.
     * @return True if valid combination, false if invalid.
     */

    public boolean checkLogin(String username, String password) throws InternalServerError{
        
        if (username == null || password == null){
            return false;
        }

        ResultSet queryResult = null;
        Connection connection = null;
        PreparedStatement stmt = null;

        String foundPassword;

        try {
            connection = DriverManager.getConnection(dbName);
        }
        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }

        String query = "SELECT * FROM user WHERE username = ? ";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, username);

            queryResult = stmt.executeQuery();
        }
        catch(SQLException e){
             throw new InternalServerError("Error querying the database." + e.getMessage());
        }

        try{
            foundPassword = queryResult.getString("password");
        }
        catch (SQLException e){
            // If there is an exception here, it's because there are no results in the set.
            return false;
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

        return foundPassword.equals(password);
    }


    /**
     * Drops all users in the database. This is called when /clear is requested.
     */

    public void deleteAllUsers() throws InternalServerError{
        try (Connection connection = DriverManager.getConnection(dbName)){

            String delete = "DELETE FROM user";

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
