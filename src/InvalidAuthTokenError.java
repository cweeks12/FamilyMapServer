package familyserver.error;

/** Custom exception thrown with invalid auth token.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class InvalidAuthTokenError extends Exception{

    /** Standard constructor; calls super(). */
    public InvalidAuthTokenError(){
        super();
    }

    /** Calls super() with message. */
    public InvalidAuthTokenError(String message){
        super(message);
    }
}
