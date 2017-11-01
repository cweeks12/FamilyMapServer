package familyserver.test;

import familyserver.model.User;
import org.junit.*;
import static org.junit.Assert.*;

public class UserTest{

    private User user;
    private User secondUser;

    @Before
    public void setup(){
        user = new User("cweeks12",
                        "pa$$word",
                        "connorweeks1@gmail.com",
                        "Connor",
                        "Weeks",
                        "M",
                        "DEADBEEF");

        secondUser = new User("gloria",
                                "pa$$w0rd",
                                "gl000000ria@gmail.com",
                                "Gloria",
                                "Glory",
                                "F",
                                "12345ABC");

    }

    @Test
    public void testEquals(){
        assertFalse(user.equals(secondUser));

        assertTrue(user.equals(user));

        assertFalse(user.equals(new User("cweeks13",
                                            "pa$$word",
                                            "connorweeks1@gmail.com",
                                            "Connor",
                                            "Weeks",
                                            "M",
                                            "DEADBEEF")));
    }

    @Test
    public void testGettersSetters(){

        user.setId("ABCEDF23");
        assertEquals("ABCEDF23", user.getId());

        user.setUsername("byuowns");
        assertEquals("byuowns", user.getUsername());

        user.setFirstName("Charles");
        assertEquals("Charles", user.getFirstName());

        user.setLastName("Chaplin");
        assertEquals("Chaplin", user.getLastName());

        user.setGender("m");
        assertEquals("M", user.getGender());

        user.setEmail("test@bbc.com");
        assertEquals("test@bbc.com", user.getEmail());

        user.setPassword("youtube");
        assertEquals("youtube", user.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenderSetterError(){
        user.setGender(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenderInvalidGenderError(){
        user.setGender("Other");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail(){
        user.setEmail("loppp");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullInputSetterError(){
        user.setFirstName(null);
    }

}
