package familyserver;

import java.sql.ResultSet;

/**
 * Contains all information about events
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class Event{

    /** The event's id */
    private String id;
    /** The event's username */
    private String username;
    /** The event's personId */
    private String personId;
    /** The event's eventLocation */
    private Location eventLocation;
    /** The event's type. (Baptism, birth, christening, marriage) */
    private String eventType;
    /** The event's year */
    private int year;


    /**
     * Constructs an event from an SQL query.
     *
     * @param rs The SQL query that contains information about the Event.
     */

    public Event(ResultSet rs){
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPersonId(){
        return personId;
    }

    public void setPersonId(String personId){
        this.personId = personId;
    }

    public Location getEventLocation(){
        return eventLocation;
    }

    public void setEventLocation(Location eventLocation){
        this.eventLocation = eventLocation;
    }

    public String getEventType(){
        return eventType;
    }

    public void setEventType(String eventType){
        this.eventType = eventType;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }
}
