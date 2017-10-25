package familyserver.test;

import familyserver.access.UserDataAccess;
import familyserver.model.User;
import familyserver.error.InternalServerError;
import familyserver.request.RegisterRequest;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataAccessTest{

    private UserDataAccess userDAO;
    private User user;
    private RegisterRequest request;

    @Before
    public void setup(){
        try{
            Class.forName("org.sqlite.JDBC");
        } 
        catch (ClassNotFoundException e){
            fail();
        }

        String dbName = "jdbc:sqlite:test.db";

        Connection connection = null;
        try{
            connection = DriverManager.getConnection(dbName);
        }catch(SQLException e){
            
            System.out.println(e.getMessage());
        }

        try{
            PreparedStatement stmt = connection.prepareStatement("DROP TABLE IF EXISTS user");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS user (username TEXT NOT NULL PRIMARY KEY, password TEXT NOT NULL, email TEXT NOT NULL, firstName TEXT NOT NULL, lastName TEXT NOT NULL, gender TEXT NOT NULL, personId TEXT NOT NULL)");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("INSERT INTO user VALUES('cweeks12','pa$$word','connorweeks1@gmail.com','Connor','Weeks','M','ABCDEF12')");
            stmt.executeUpdate();
            stmt.close();

        } 
        catch (SQLException e){
            System.out.println("Error creating database");
            System.out.println(e.getMessage());
        }

        userDAO = new UserDataAccess("test.db");
        user = new User("cweeks12","pa$$word","connorweeks1@gmail.com","Connor","Weeks","M","ABCDEF12");
    }

    @Test
    public void testFindingUserInDatabase(){
        // This test finds the user that was added into the database in @Before
        User foundUser = null;
        try {
            foundUser = userDAO.getUserByUsername(user.getUsername());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(foundUser.equals(user));
    }

    @Test 
    public void searchingForNullUserById(){
        // This test makes sure that if you search for null, you get null back
        User foundUser = null;
        try {
            foundUser = userDAO.getUserByUsername(null);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertNull(foundUser);
    }

    @Test
    public void testCorrectPassword(){
        boolean correct = false;
        try {
            correct = userDAO.checkLogin("cweeks12", "pa$$word");
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(correct);
    }

    @Test
    public void testIncorrectPassword(){
        boolean correct = false;
        try {
            correct = userDAO.checkLogin("cweeks12", "p@$$word");
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertFalse(correct);
    }

    @Test
    public void testNullPassword(){
        boolean correct = false;
        try {
            correct = userDAO.checkLogin("cweeks12", null);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertFalse(correct);
    }

    @Test
    public void testAddingUserToDatabase(){
        // This test adds a new user to the database, then tests if it arrived in there by looking for it.
        RegisterRequest newUser = new RegisterRequest("janna", "pass", "janna@edu.edu","Janna","Arlington","F");
        try {
            userDAO.createNewUser(newUser);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        User foundUser = null;
        try {
            foundUser = userDAO.getUserByUsername("janna");
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(newUser.getUserName().equals(foundUser.getUsername()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddingInvalidUserToDatabase(){
        // This test adds an invalid user to the database, should throw an exception
        try {
            userDAO.createNewUser(new RegisterRequest(null, "pass", "janna@edu.edu","Janna","Arlington","R"));
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testDroppingAllPeople(){ 
        // This test deletes everyone from the database, then checks for the original user from @Before, should return a null user.

        User foundUser = null;
        try {
            userDAO.deleteAllUsers();
            foundUser = userDAO.getUserByUsername(user.getUsername());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertNull(foundUser);
    }

}
