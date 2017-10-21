package familyserver.test;

import familyserver.PersonDataAccess;
import familyserver.Person;
import familyserver.InternalServerError;
import familyserver.PersonNotFoundError;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDataAccessTest{


    private PersonDataAccess personDAO;
    private Person person;

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
            PreparedStatement stmt = connection.prepareStatement("DROP TABLE IF EXISTS person");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS person (personId text PRIMARY KEY, descendant text NOT NULL, firstName text NOT NULL, lastName text NOT NULL, gender text NOT NULL, father text, mother text, spouse text)");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("INSERT INTO person VALUES('12345678','cweeks12','Connor','Weeks','M',NULL,NULL,NULL)");
            stmt.executeUpdate();
            stmt.close();

        } 
        catch (SQLException e){
            System.out.println("Error creating database");
            System.out.println(e.getMessage());
        }

        personDAO = new PersonDataAccess("test.db");
        person = new Person("12345678", "cweeks12", "Connor", "Weeks", "M", null, null, "12345678");
    }

    @Test
    public void testFindingPersonInDatabase(){
        // This test finds the person that was added into the database in @Before
        Person foundPerson = null;
        try {
            foundPerson = personDAO.getPersonById(person.getId());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(foundPerson.equals(person));
    }

    @Test 
    public void searchingForNullPersonById(){
        // This test makes sure that if you search for null, you get null back
        Person foundPerson = null;
        try {
            foundPerson = personDAO.getPersonById(null);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertNull(foundPerson);
    }

    @Test
    public void testAddingPersonToDatabase(){
        // This test adds a new person to the database, then tests if it arrived in there by looking for it.
        Person wife = new Person("ABCDEF98", "cweeks12", "Sara", "Weeks", "F", null, null, "12345678");
        try {
            personDAO.createNewPerson(wife);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        Person foundPerson = null;
        try {
            foundPerson = personDAO.getPersonById(wife.getId());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(wife.equals(foundPerson));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddingInvalidPersonToDatabase(){
        // This test adds an invalid person to the database, should throw an exception
        try {
            personDAO.createNewPerson(new Person("ABCDEF98", "cweeks12", "Sara", "Weeks", "Other", null, null, "12345678"));
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testDroppingAllPeople(){ 
        // This test deletes everyone from the database, then checks for the original person from @Before, should return a null person.

        Person foundPerson = null;
        try {
            personDAO.deleteAllPeople();
            foundPerson = personDAO.getPersonById(person.getId());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertNull(foundPerson);
    }

    @Test
    public void testGettingListOfPeople(){
        // This test queries the database for all people belonging to "cweeks12" and makes sure the list is right.
        List<Person> givenPeople = new ArrayList<Person>();
        Person newPerson = new Person("ABCDEF98", "cweeks12", "Sara", "Weeks", "F", null, null, "12345678");
        try {
            personDAO.createNewPerson(newPerson);
            givenPeople = personDAO.getAllPeople("cweeks12");
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
        assertEquals(givenPeople.size(), 2);
        assertEquals(givenPeople.get(0), person);
        assertEquals(givenPeople.get(1), newPerson);
    }

    @Test
    public void testGettingListOfPeopleWithInvalidUsername(){
        // This test queries the database for all people belonging to "target" and makes sure the result is null
        List<Person> givenPeople = new ArrayList<Person>();
        Person newPerson = new Person("ABCDEF98", "cweeks12", "Sara", "Weeks", "F", null, null, "12345678");
        try {
            personDAO.createNewPerson(newPerson);
            givenPeople = personDAO.getAllPeople("target");
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
        assertNull(givenPeople);
    }

    @Test
    public void testGettingListOfPeopleWithNullUsername(){
        // This test queries the database for all people belonging to null and makes sure the result is null
        List<Person> givenPeople = new ArrayList<Person>();
        Person newPerson = new Person("ABCDEF98", "cweeks12", "Sara", "Weeks", "F", null, null, "12345678");
        try {
            personDAO.createNewPerson(newPerson);
            givenPeople = personDAO.getAllPeople(null);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
        assertNull(givenPeople);
    }
}
