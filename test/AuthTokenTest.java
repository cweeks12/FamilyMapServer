package familyserver.test;

import familyserver.model.AuthToken;
import org.junit.*;
import static org.junit.Assert.*;

public class AuthTokenTest{

    private AuthToken token;
    private AuthToken secondToken;
    /** Creates two Token objects and opens a connection to database */
    @Before
    public void setup(){
        token = new AuthToken("cweeks12", "ABBD1829");
        secondToken = new AuthToken("abbymama", "BDD892A3");

    }

    @Test
    public void testEquals(){
        assertFalse(token.equals(secondToken));

        assertTrue(token.equals(token));

        assertFalse(token.equals(new AuthToken("cweeks12", "ABBD1830")));
    }

    @Test
    public void testGettersSetters(){

        token.setToken("ABCEDF23");
        assertEquals("ABCEDF23", token.getToken());

        token.setUsername("byuowns");
        assertEquals("byuowns", token.getUsername());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsernameSetterNullError(){
        token.setUsername(null);
    }

}
