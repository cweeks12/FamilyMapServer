package familyserver;

/** Custom exception thrown with invalid request parameter.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class InvalidRequestParameterError extends Exception{

    /** Standard constructor; calls super(). */
    public InvalidRequestParameterError(){
        super();
    }

    /** Calls super() with message. */
    public InvalidRequestParameterError(String message){
        super(message);
    }
}
