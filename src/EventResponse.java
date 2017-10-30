package familyserver.response;

import familyserver.model.AuthToken;
import familyserver.model.Event;
import familyserver.model.Location;

/** Class that holds everything for an individual event response. */

public class EventResponse{


    /** Descendant this event belongs to. */
    private String descendant;
    /** Unique ID for the event. */
    private String eventId;
    /** ID of the person that this event is for.*/
    private String personId;
    /** Latitude of the event. */
    private float latitude;
    /** Longitude of the event. */
    private float longitude;
    /** Country it occurred in. */
    private String country;
    /** City it occurred in. */
    private String city;
    /** Type of event (marriage, baptism, birth, death). */
    private String eventType;
    /** Year it occurred. */
    private int year;

    /**
     * Constructor that creates the object to return in response to the GET request.
     *
     * @param event The event you're returning in response to the GET request
     */

    public EventResponse(Event event){
        Location loc = event.getEventLocation();

        descendant = event.getUsername();
        eventId = event.getId();
        personId = event.getPersonId();
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
    public void setEventId(String eventId){
        this.eventId = eventId;
    }
    public void setPersonId(String personId){
        this.personId = personId;
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
    public void setYear(int year){
        this.year = year;
    }
}
