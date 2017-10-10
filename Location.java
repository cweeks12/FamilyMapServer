package familyserver;

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
    private String latitude;

    /** Longitude of the location */
    private String longitude;

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

    public String getLatitude(){
        return latitude;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

}
