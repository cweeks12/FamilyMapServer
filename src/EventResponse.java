package familyserver;

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
    private String year;

    /**
     * Constructor that creates the object to return in response to the GET request.
     *
     * @param event The event you're returning in response to the GET request
     */
    
    public EventResponse(Event event){
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
    public void setYear(String year){
        this.year = year;
    }
}
