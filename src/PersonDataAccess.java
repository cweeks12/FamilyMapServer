package familyserver;

import java.util.List;

/**
 * Accesses the database for Person objects.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class PersonDataAccess{

    /** Builds a new user data access object to interact with user database.*/
    public PersonDataAccess(){
    }

    /** 
     * Adds a new person to the database.
     *
     * @param personToCreate the new person to add to the database.
     * @return The ID of the new person added.
     */

    public String createNewPerson(Person personToCreate){
        return null;
    }


    /** 
     * Queries the database and returns the Person object from the username.
     *
     * @param personID The unique person ID to get the person from.
     * @return The person that responds to the given ID number.
     */

    public Person getPersonById(String personID){
        return null;
    }


    /** 
     * Queries the database and returns all Persons that belong to the given user.
     *
     * @param descendant The username that's requesting all their ancestors.
     * @return A list of all people that belong to them.
     */

    public List<Person> getAllPeople(String descendant){
        return null;
    }


    /**
     * Drops all people in the database. This is called when /clear is requested.
     */

    public void deleteAllPeople(){
    }
} 
