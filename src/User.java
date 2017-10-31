package familyserver.model;

import java.util.regex.*;
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
    private String userName;

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
    private String personID;

    /** Constructor that builds the user based on the parts given. */
    public User( String username,
                    String password,
                    String email,
                    String firstName,
                    String lastName,
                    String gender,
                    String id) throws IllegalArgumentException{

        if (username == null
            || password == null
            || email == null
            || firstName == null
            || lastName == null
            || gender == null
            || id == null){
                throw new IllegalArgumentException();
        }

        gender = gender.toUpperCase();
        if (!gender.equals("F") && !gender.equals("M")){
            throw new IllegalArgumentException();
        }

        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = id;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }

        if (o.getClass() != this.getClass()){
            return false;
        }

        User u = (User) o;

        return this.userName.equals(u.getUsername())
            && this.password.equals(u.getPassword())
            && this.email.equals(u.getEmail())
            && this.firstName.equals(u.getFirstName())
            && this.lastName.equals(u.getLastName())
            && this.gender.equals(u.getGender())
            && this.personID.equals(u.getId());
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    public String getUsername(){
        return userName;
    }

    public void setUsername(String username) throws IllegalArgumentException{
        if (username == null){
            throw new IllegalArgumentException();
        }
        this.userName = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) throws IllegalArgumentException{
        if (password == null){
            throw new IllegalArgumentException();
        }
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException{
        if (email == null){
            throw new IllegalArgumentException();
        }
        if (!Pattern.matches("\\p{Alpha}\\w*@\\w+\\.\\w{3,}", email)){
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) throws IllegalArgumentException{
        if (firstName == null){
            throw new IllegalArgumentException();
        }
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName) throws IllegalArgumentException{
        if (lastName == null){
            throw new IllegalArgumentException();
        }
        this.lastName = lastName;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender) throws IllegalArgumentException{
        if (gender == null){
            throw new IllegalArgumentException();
        }

        gender = gender.toUpperCase();
        if (!gender.equals("F") && !gender.equals("M")){
            throw new IllegalArgumentException();
        }

        this.gender = gender;
    }

    public String getId(){
        return personID;
    }

    public void setId(String id) throws IllegalArgumentException{
        if (id == null){
            throw new IllegalArgumentException();
        }
        this.personID = id;
    }

}
