package familyserver;

import java.util.List;

/**
 * Accesses the database for Event objects.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class EventDataAccess{
    /** Builds a new user data access object to interact with user database.*/
    public EventDataAccess(){
    }

    /** 
     * Adds a new event to the database.
     *
     * @param eventToCreate the new event to add to the database.
     * @return The ID of the new event added.
     */

    public String createNewEvent(Event eventToCreate){
        return null;
    }


    /** 
     * Queries the database and returns the Event object from the username.
     *
     * @param eventId The unique evvent ID to get the event for.
     * @return The event that responds to the given ID number.
     */

    public Event getEventById(String eventId){
        return null;
    }


    /** 
     * Queries the database and returns all Events that belong to the given user.
     *
     * @param owner The username that's requesting all their events.
     * @return A list of all people that belong to them.
     */

    public List<Event> getAllEvents(String owner){
        return null;
    }


    /**
     * Drops all events in the database. This is called when /clear is requested.
     */

    public void deleteAllEvents(){
    }
} 
