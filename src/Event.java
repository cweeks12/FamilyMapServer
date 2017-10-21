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


    public Event(String id, String username, String personId, Location eventLocation, String eventType, Integer year) throws IllegalArgumentException{

        if (id == null || username == null || personId == null || eventLocation == null || eventType == null || year == null){
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.username = username;
        this.personId = personId;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.year = year;
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
        if (id == null){
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        if (username == null){
            throw new IllegalArgumentException();
        }
        this.username = username;
    }

    public String getPersonId(){
        return personId;
    }

    public void setPersonId(String personId){
        if (personId == null){
            throw new IllegalArgumentException();
        }
        this.personId = personId;
    }

    public Location getEventLocation(){
        return eventLocation;
    }

    public void setEventLocation(Location eventLocation){
        if (eventLocation == null){
            throw new IllegalArgumentException();
        }
        this.eventLocation = eventLocation;
    }

    public String getEventType(){
        return eventType;
    }

    public void setEventType(String eventType){
        if (eventType == null){
            throw new IllegalArgumentException();
        }
        this.eventType = eventType;
    }

    public int getYear(){
        return year;
    }

    public void setYear(Integer year){
        if (year == null || year < 0){
            throw new IllegalArgumentException();
        }
        this.year = year;
    }
}
