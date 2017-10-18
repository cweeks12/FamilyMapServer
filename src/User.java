package familyserver;

import java.sql.ResultSet;
import java.sql.SQLException;

/** 
 * Contains all information about one user.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class User{

    /** The user's username. */
    private String username;

    /** The user's password. */
    private String password;

    /** The user's email. */
    private String email;

    /** The user's first name. */
    private String firstName;

    /** The user's last name. */
    private String lastName;

    /** The user's gender. */
    private String gender;

    /** The user's unique id number. */
    private String id;


    /** 
     * Creates a user based on an sql query.
     *
     * @param rs An sql result set that you create the user from.
     */

    public User(ResultSet rs){
        try{
            this.username = rs.getString("username");
            this.password = rs.getString("password");
            this.email = rs.getString("email");
            this.firstName = rs.getString("firstName");
            this.lastName = rs.getString("lastName");
            this.gender = rs.getString("gender");
            this.id = rs.getString("id");
        }
        catch(SQLException e){
            System.out.println("Error creating User from SQL.");
        }
    }

    public User( String username,
                    String password,
                    String email,
                    String firstName,
                    String lastName,
                    String gender,
                    String id){

        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.id = id;
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}
