package familyserver;

/** Class that holds everything for a registration request. */

public class RegisterRequest{

    /** Requested username. */
    public String userName;

    /** Requested password. */
    public String password;

    /** User's email address. */
    public String email;

    /** User's first name. */
    public String firstName;

    /** User's last name. */
    public String lastName;

    /** User's gender. Must be "m" or "f". */
    public String gender;

    /**
     * Constructor that builds the object from a json string.
     *
     * @param jsonString String holding json contents of object.
     */
    
    public RegisterRequest(String jsonString){
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getGender(){
        return gender;
    }
}
