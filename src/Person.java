package familyserver;

import java.sql.ResultSet;

/**
 * Contains all information about one person.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */
public class Person{

    /** Unique identifier for this person */
    private String id;

    /** User (username) to which this person belongs */
    private String descendant;

    /** Person's first name */
    private String firstName;

    /** Person's first name */
    private String lastName;

    /** Person's gender (must be "f" or "m") */
    private String gender;

    /** ID of person's father */
    private String father;

    /** ID of person's mother */
    private String mother;

    /** ID of person's spouse */
    private String spouse;


    /** Constructs a Person based on a database query.
     *
     * @param rs SQL result set to build the person from.
     */

    public Person(ResultSet rs){
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
        this.id = id;
    }

    public String getDescendant(){
        return descendant;
    }

    public void setDescendant(String descendant){
        this.descendant = descendant;
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
    public String getFather(){
        return father;
    }

    public void setFather(String father){
        this.father = father;
    }
    public String getMother(){
        return mother;
    }

    public void setMother(String mother){
        this.mother = mother;
    }
    public String getSpouse(){
        return spouse;
    }

    public void setSpouse(String spouse){
        this.spouse = spouse;
    }
}
