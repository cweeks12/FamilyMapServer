package familyserver.test;

import familyserver.access.EventDataAccess;
import familyserver.model.Event;
import familyserver.model.Location;
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

public class EventDataAccessTest{

    private EventDataAccess eventDAO;
    private Event event;

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
            PreparedStatement stmt = connection.prepareStatement("DROP TABLE IF EXISTS event");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS event (eventId TEXT NOT NULL PRIMARY KEY, descendant TEXT NOT NULL, person TEXT NOT NULL, latitude REAL NOT NULL, longitude REAL NOT NULL, country TEXT NOT NULL, city TEXT NOT NULL, eventType TEXT NOT NULL, year TEXT NOT NULL)");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("INSERT INTO event VALUES('12345678','cweeks12','ABCDEF98', 2.3456, 3.4567, 'United States', 'Provo', 'Marriage', 2016)");
            stmt.executeUpdate();
            stmt.close();

        }
        catch (SQLException e){
            System.out.println("Error creating database");
            System.out.println(e.getMessage());
        }

        eventDAO = new EventDataAccess("test.db");
        event = new Event("12345678","cweeks12","ABCDEF98", new Location("United States", "Provo", 2.3456, 3.4567), "Marriage", 2016);
    }

    @Test
    public void testFindingEventInDatabase(){
        // This test finds the event that was added into the database in @Before
        Event foundEvent = null;
        try {
            foundEvent = eventDAO.getEventById(event.getId());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(foundEvent.equals(event));
    }

    @Test
    public void searchingForNullEventById(){
        // This test makes sure that if you search for null, you get null back
        Event foundEvent = null;
        try {
            foundEvent = eventDAO.getEventById(null);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertNull(foundEvent);
    }

    @Test
    public void testAddingEventToDatabase(){
        // This test adds a new event to the database, then tests if it arrived in there by looking for it.
        Event secondEvent = new Event("1BE34F22", "cweeks12", "34E5F291", new Location("Kansas City", "United States", 40.5839, -111.9283), "Birth", 1987);
        try {
            eventDAO.createNewEvent(secondEvent);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        Event foundEvent = null;
        try {
            foundEvent = eventDAO.getEventById(secondEvent.getId());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertTrue(secondEvent.equals(foundEvent));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddingInvalidEventToDatabase(){
        // This test adds an invalid event to the database, should throw an exception
        try {
            eventDAO.createNewEvent(new Event("1BE34F22", "cweeks12", null, new Location("Kansas City", "United States", 40.5839, -111.9283), "Birth", 1987));
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testDroppingAllEvents(){
        // This test deletes everyone from the database, then checks for the original event from @Before, should return a null event.

        Event foundEvent = null;
        try {
            eventDAO.deleteAllEvents();
            foundEvent = eventDAO.getEventById(event.getId());
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }

        assertNull(foundEvent);
    }

    @Test
    public void testGettingListOfEvents(){
        // This test queries the database for all people belonging to "cweeks12" and makes sure the list is right.
        List<Event> givenEvents = new ArrayList<Event>();
        Event newEvent = new Event("1BE34F22", "cweeks12", "34E5F291", new Location("Kansas City", "United States", 40.5839, -111.9283), "Birth", 1987);
        try {
            eventDAO.createNewEvent(newEvent);
            givenEvents = eventDAO.getAllEvents("cweeks12");
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
        assertEquals(givenEvents.size(), 2);
        assertEquals(givenEvents.get(0), event);
        assertEquals(givenEvents.get(1), newEvent);
    }

    @Test
    public void testGettingListOfEventsWithInvalidUsername(){
        // This test queries the database for all people belonging to "target" and makes sure the result is null
        List<Event> givenEvents = new ArrayList<Event>();
        Event newEvent = new Event("1BE34F22", "cweeks12", "34E5F291", new Location("Kansas City", "United States", 40.5839, -111.9283), "Birth", 1987);
        try {
            eventDAO.createNewEvent(newEvent);
            givenEvents = eventDAO.getAllEvents("target");
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
        assertNull(givenEvents);
    }

    @Test
    public void testGettingListOfEventsWithNullUsername(){
        // This test queries the database for all people belonging to null and makes sure the result is null
        List<Event> givenEvents = new ArrayList<Event>();
        Event newEvent = new Event("1BE34F22", "cweeks12", "34E5F291", new Location("Kansas City", "United States", 40.5839, -111.9283), "Birth", 1987);
        try {
            eventDAO.createNewEvent(newEvent);
            givenEvents = eventDAO.getAllEvents(null);
        }
        catch (InternalServerError e){
            fail(e.getMessage());
        }
        assertNull(givenEvents);
    }
}
