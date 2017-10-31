package familyserver.model;

import familyserver.response.EventResponse;

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
    /** The person this event belongs to */
    private String personId;
    /** The event's eventLocation */
    private Location eventLocation;
    /** The event's type. (Baptism, birth, christening, marriage) */
    private String eventType;
    /** The event's year */
    private String year;


    /** Builds an event given all of the individual pieces.*/
    public Event(String id,
                 String username,
                 String personId,
                 Location eventLocation,
                 String eventType,
                 String year) throws IllegalArgumentException{

        if (id == null
            || username == null
            || personId == null
            || eventLocation == null
            || eventType == null
            || year == null){

            throw new IllegalArgumentException();
        }

        this.id = id;
        this.username = username;
        this.personId = personId;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.year = year;
    }


    /** Converts an EventResponse to an Event.
    *
    * @param response The EventResponse that you want to convert to an event.
    */

    public Event(EventResponse response){
        this.id = response.eventID;
        this.username = response.descendant;
        this.personId = response.personID;
        this.eventType = response.eventType;
        this.year = response.year;

        this.eventLocation = new Location(response.country,
                                          response.city,
                                          response.latitude,
                                          response.longitude);
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }

        if (o.getClass() != this.getClass()){
            return false;
        }

        Event e = (Event) o;

        return this.id.equals(e.getId())
                && this.username.equals(e.getUsername())
                && this.personId.equals(e.getPersonId())
                && this.eventLocation.equals(e.getEventLocation())
                && this.eventType.equals(e.getEventType())
                && this.year.equals(e.getYear());
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

    public String getYear(){
        return year;
    }

    public void setYear(String year){
        if (year == null){
            throw new IllegalArgumentException();
        }
        this.year = year;
    }
}
