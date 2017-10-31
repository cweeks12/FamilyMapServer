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


    /** Constructor that takes all necessary members as parameters. */
    public Location(String country, String city, float latitude, float longitude){
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /** Constructor that casts doubles to floats in its construction. */
    public Location(String country, String city, double latitude, double longitude){
        this.country = country;
        this.city = city;
        this.latitude = (float)latitude;
        this.longitude = (float)longitude;
    }

    @Override
    public String toString(){
        return "Location: " + city + ", " + country + " (" + Float.toString(latitude) + ", " + Float.toString(longitude) + ")";
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }

        if (o.getClass() != this.getClass()){
            return false;
        }

        Location loc = (Location) o;

        return this.city.equals(loc.getCity()) && this.country.equals(loc.getCountry()) && this.latitude == loc.getLatitude() && this.longitude == loc.getLongitude();

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
