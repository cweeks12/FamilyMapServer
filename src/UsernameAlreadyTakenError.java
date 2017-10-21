package familyserver.error;

/** Custom exception thrown with username already taken.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class UsernameAlreadyTakenError extends Exception{

    /** Standard constructor; calls super(). */
    public UsernameAlreadyTakenError(){
        super();
    }

    /** Calls super() with message. */
    public UsernameAlreadyTakenError(String message){
        super(message);
    }
}
