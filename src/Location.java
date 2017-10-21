package familyserver.model;

/**
 * Holds location information.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */
public class Location{

    /** Country the person is from. */
    private String country;

    /** City the person is from. */
    private String city;

    /** Latitude of the location */
    private float latitude;

    /** Longitude of the location */
    private float longitude;

    /** Constructor that builds a location based on a JSON string.
     *
     * @param jsonString A JSON string that contains the information for Location.
     */

    public Location(String jsonString){
    }

    public Location(String country, String city, float latitude, float longitude){
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return "Location: " + city + ", " + country + " (" + Float.toString(latitude) + ", " + Float.toString(longitude) + ")";
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public float getLatitude(){
        return latitude;
    }

    public void setLatitude(float latitude){
        this.latitude = latitude;
    }

    public float getLongitude(){
        return longitude;
    }

    public void setLongitude(float longitude){
        this.longitude = longitude;
    }

}
