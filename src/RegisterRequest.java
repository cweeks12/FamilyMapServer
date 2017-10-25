package familyserver.request;

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

    /** User's gender. Must be "M" or "F". */
    public String gender;

    /**
     * Constructor that builds the object from individual parts.
     */
    
    public RegisterRequest(
        String userName,
        String password,
        String email,
        String firstName,
        String lastName,
        String gender) throws IllegalArgumentException{

        if (userName == null || password == null
            || email == null || firstName == null
            || lastName == null || gender == null){
            throw new IllegalArgumentException();
        }
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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
