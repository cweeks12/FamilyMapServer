package familyserver.test;

import familyserver.access.AuthTokenDataAccess;
import familyserver.model.AuthToken;
import familyserver.error.InternalServerError;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthTokenDataAccessTest{

    private AuthTokenDataAccess tokenDAO;
    private AuthToken token;

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
        }
        catch(SQLException e){
            
            System.out.println(e.getMessage());
        }

        try{
            PreparedStatement stmt = connection.prepareStatement("DROP TABLE IF EXISTS authToken");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS authToken (authToken TEXT NOT NULL PRIMARY KEY, username TEXT NOT NULL)");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("INSERT INTO authToken VALUES('12345678','cweeks12')");
            stmt.executeUpdate();
            stmt.close();

        } 
        catch (SQLException e){
            System.out.println("Error creating database");
            System.out.println(e.getMessage());
        }
        tokenDAO = new AuthTokenDataAccess("test.db");
        token = new AuthToken("cweeks12","12345678");
    }

    @Test
    public void testFindingTokenInDatabase(){
        // This test finds the token that was added into the database in @Before
        AuthToken foundToken = null;
        try {
            foundToken = tokenDAO.getTokenByTokenValue(token.getToken());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(foundToken.equals(token));
    }

    @Test 
    public void searchingForNullTokenById(){
        // This test makes sure that if you search for null, you get null back
        AuthToken foundToken = null;
        try {
            foundToken = tokenDAO.getTokenByTokenValue(null);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertNull(foundToken);
    }

    @Test
    public void testAddingTokenToDatabase(){
        // This test adds a new token to the database, then tests if it arrived in there by looking for it.
        AuthToken bunny = null;
        try {
            String newToken = tokenDAO.newAuthToken("bunny");
            bunny = new AuthToken("bunny", newToken);
            
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        AuthToken foundToken = null;
        try {
            foundToken = tokenDAO.getTokenByTokenValue(bunny.getToken());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(bunny.getUsername().equals(foundToken.getUsername()));
        assertTrue(bunny.getToken().equals(foundToken.getToken()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddingInvalidTokenToDatabase(){
        // This test adds an invalid token to the database, should throw an exception
        try {
            tokenDAO.newAuthToken(null);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testDroppingAllTokens(){ 
        // This test deletes everyone from the database, then checks for the original token from @Before, should return a null token.

        AuthToken foundToken = null;
        try {
            tokenDAO.deleteAllAuthTokens();
            foundToken = tokenDAO.getTokenByTokenValue(token.getToken());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertNull(foundToken);
    }

}
