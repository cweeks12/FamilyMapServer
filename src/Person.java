package familyserver;

import java.lang.StringBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;

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


    /** Override equals method.
     *
     * @param o Object that is being compared.
     * @return Whether or not the person is the same, based on ID
     */

    @Override 
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (this.getClass() != o.getClass()){
            return false;
        }

        Person p = (Person) o;

        return this.id == p.getId() && this.firstName.equals(p.getFirstName()) && this.lastName.equals(p.getLastName());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("ID: " + id);
        sb.append(" Desc: " + descendant);
        sb.append(" Name: " + firstName + " " + lastName);
        sb.append(" Gender: " + gender);

        return sb.toString();
    }


    /** Constructs a Person based on a database query.
     *
     * @param rs SQL result set to build the person from.
     */

    public Person(ResultSet rs){
        try{
            this.id = rs.getString("personId");
            this.descendant = rs.getString("descendant");
            this.firstName = rs.getString("firstName");
            this.lastName = rs.getString("lastName");
            this.gender = rs.getString("gender");
            this.father = rs.getString("father");
            this.mother = rs.getString("mother");
            this.spouse = rs.getString("spouse");
        }
        catch (SQLException e){
            System.out.println("Error building Person object.");
        }
    }

    public Person(String id, 
                    String descendant, 
                    String firstName, 
                    String lastName, 
                    String gender, 
                    String father, 
                    String mother, 
                    String spouse) throws IllegalArgumentException{

        if (!(gender.equals("F") || gender.equals("M"))){
            throw new IllegalArgumentException();
        }

        if (id == null || descendant == null || firstName == null || lastName == null || gender == null){
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    } 

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    public String getId(){
        return id;
    }

    public void setId(String id) throws IllegalArgumentException{
        if (id == null){
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public String getDescendant(){
        return descendant;
    }

    public void setDescendant(String descendant) throws IllegalArgumentException{
        if (descendant == null){
            throw new IllegalArgumentException();
        }
        this.descendant = descendant;
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
        if (gender == null || !(gender.equals("F") || gender.equals("M"))){
            throw new IllegalArgumentException();
        }
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
