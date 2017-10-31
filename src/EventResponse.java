package familyserver.response;

import familyserver.model.AuthToken;
import familyserver.model.Event;
import familyserver.model.Location;

/** Class that holds everything for an individual event response. */

public class EventResponse{


    /** Descendant this event belongs to. */
    public String descendant;
    /** Unique ID for the event. */
    public String eventID;
    /** ID of the person that this event is for.*/
    public String personID;
    /** Latitude of the event. */
    public float latitude;
    /** Longitude of the event. */
    public float longitude;
    /** Country it occurred in. */
    public String country;
    /** City it occurred in. */
    public String city;
    /** Type of event (marriage, baptism, birth, death). */
    public String eventType;
    /** Year it occurred. */
    public String year;

    /**
     * Constructor that creates the object to return in response to the GET request.
     *
     * @param event The event you're returning in response to the GET request
     */

    public EventResponse(Event event){
        Location loc = event.getEventLocation();

        descendant = event.getUsername();
        eventID = event.getId();
        personID = event.getPersonId();
        eventType = event.getEventType();
        year = event.getYear();

        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        country = loc.getCountry();
        city = loc.getCity();
    }

    public void setDescendant(String descendant){
        this.descendant = descendant;
    }
    public void seteventID(String eventID){
        this.eventID = eventID;
    }
    public void setpersonID(String personID){
        this.personID = personID;
    }
    public void setLatitude(float latitude){
        this.latitude = latitude;
    }
    public void setLongitude(float longitude){
        this.longitude = longitude;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setEventType(String eventType){
        this.eventType = eventType;
    }
    public void setYear(String year){
        this.year = year;
    }
}
