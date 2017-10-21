package familyserver.response;

import familyserver.model.AuthToken;
import familyserver.model.Person;

/** Class that holds everything for an individual person response.
 * This class only has setters because it's a response class. You're only setting responses you'll send back. */

public class PersonResponse{

    /** Unique personIdentifier for this person */
    private String personId;

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


    /**
     * Constructor that creates the object to return in response to the GET request.
     *
     * @param person The person you're returning in response to the GET request
     */
    
    public PersonResponse(Person person){
    }

    public void setPersonId(String personId){
        this.personId = personId;
    }

    public void setDescendant(String descendant){
        this.descendant = descendant;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setFather(String father){
        this.father = father;
    }

    public void setMother(String mother){
        this.mother = mother;
    }

    public void setSpouse(String spouse){
        this.spouse = spouse;
    }
}
