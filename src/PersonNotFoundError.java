package familyserver.error;

/** Custom exception thrown with internal server error.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class PersonNotFoundError extends Exception{

    /** Standard constructor; calls super(). */
    public PersonNotFoundError(){
        super();
    }

    /** Calls super() with message. */
    public PersonNotFoundError(String message){
        super(message);
    }
}
