package familyserver.access;

import familyserver.error.InternalServerError;
import familyserver.model.Event;
import familyserver.model.Location;
import familyserver.util.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the database for Event objects.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class EventDataAccess{

    // Driver string for the class
    private final String driver = "org.sqlite.JDBC";

    // Holds the name of the database
    private String dbName;

    /** Builds a new user data access object to interact with user database.
     *
     * @param databasePath Path to the database.
     */
    public EventDataAccess(String databasePath){

        try{
            Class.forName(driver);
        }
        catch (ClassNotFoundException e){
            System.out.println("Error finding the SQLite driver.");
        }

        dbName = "jdbc:sqlite:"+databasePath;
    }

    /** 
     * Adds a new event to the database.
     *
     * @param eventToCreate the new event to add to the database.
     * @return The ID of the new event added.
     */

    public String createNewEvent(Event eventToCreate) throws InternalServerError{

        try (Connection connection = DriverManager.getConnection(dbName)){

            String insert = "INSERT INTO event VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // If the event you're given doesn't have an ID yet, assign one
            if (eventToCreate.getId() == null){
                eventToCreate.setId(Utils.generateId());
            }

            try{
                PreparedStatement stmt = connection.prepareStatement(insert);
                Location eventLocation = eventToCreate.getEventLocation();
                stmt.setString(1, eventToCreate.getId());
                stmt.setString(2, eventToCreate.getUsername());
                stmt.setString(3, eventToCreate.getPersonId());
                stmt.setFloat(4, eventLocation.getLatitude());
                stmt.setFloat(5, eventLocation.getLongitude());
                stmt.setString(6, eventLocation.getCountry());
                stmt.setString(7, eventLocation.getCity());
                stmt.setString(8, eventToCreate.getEventType());
                stmt.setInt(9, eventToCreate.getYear());

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

        return eventToCreate.getId();
    }

    /** 
     * Queries the database and returns the Event object from the username.
     *
     * @param eventId The unique event ID to get the event for.
     * @return The event that responds to the given ID number.
     */

    public Event getEventById(String eventID) throws InternalServerError{

        if (eventID == null){
            return null;
        }

        ResultSet queryResult = null;
        Connection connection = null;
        PreparedStatement stmt = null;

        String id;
        String descendant;
        String person;
        float latitude;
        float longitude;
        String country;
        String city;
        String eventType;
        int year;

        try {
            connection = DriverManager.getConnection(dbName);
        }
        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }

        String query = "SELECT * FROM event WHERE eventId = ? ";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, eventID);

            queryResult = stmt.executeQuery();
        }
        catch(SQLException e){
             throw new InternalServerError("Error querying the database." + e.getMessage());
        }

        try{
            id = queryResult.getString("eventId");
            descendant = queryResult.getString("descendant");
            person = queryResult.getString("person");
            latitude = queryResult.getFloat("latitude");
            longitude = queryResult.getFloat("longitude");
            country = queryResult.getString("country");
            city = queryResult.getString("city");
            eventType = queryResult.getString("eventType");
            year = queryResult.getInt("year");
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

        return new Event(id, descendant, person, new Location(country, city, latitude, longitude), eventType, year);
    }


    /** 
     * Queries the database and returns all Events that belong to the given user.
     *
     * @param owner The username that's requesting all their events.
     * @return A list of all people that belong to them.
     */

    public List<Event> getAllEvents(String owner) throws InternalServerError{
        if (owner == null){
            return null;
        }

        Connection connection = null;
        ResultSet queryResult = null;
        PreparedStatement stmt = null;
        ArrayList<Event> listOfPeople = new ArrayList<Event>();

        String id;
        String descendant;
        String person;
        float latitude;
        float longitude;
        String country;
        String city;
        String eventType;
        int year;

        try{
            connection = DriverManager.getConnection(dbName);
        }
        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }

        String query = "SELECT * FROM event WHERE descendant = ? ";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, owner);

            queryResult = stmt.executeQuery();
        }
        catch(SQLException e){
            throw new InternalServerError("Error querying the database.");
        }

        try{

            while (queryResult.next()){
                id = queryResult.getString("eventId");
                descendant = queryResult.getString("descendant");
                person = queryResult.getString("person");
                latitude = queryResult.getFloat("latitude");
                longitude = queryResult.getFloat("longitude");
                country = queryResult.getString("country");
                city = queryResult.getString("city");
                eventType = queryResult.getString("eventType");
                year = queryResult.getInt("year");
                listOfPeople.add(new Event(id, descendant, person, new Location(country, city, latitude, longitude), eventType, year));
            }
        }
        catch (SQLException e){
            // If there is an exception here, it's because there are no results in the set.
            return null;
        }

        try {
            queryResult.close();
            stmt.close();
            connection.close();
            connection = null;
        }
        catch (SQLException e){
            throw new InternalServerError("Error closing the connection to database." + e.getMessage());
        }

        // If nobody was added to the list, return a null object
        if (listOfPeople.size() == 0){
            return null;
        }

        return listOfPeople;
    }


    /**
     * Drops all events in the database. This is called when /clear is requested.
     */

    public void deleteAllEvents() throws InternalServerError{
        try (Connection connection = DriverManager.getConnection(dbName)){

            String delete = "DELETE FROM event";

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
