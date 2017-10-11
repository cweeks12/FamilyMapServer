package familyserver;

/** Custom exception thrown with internal server error.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class InternalServerError extends Exception{

    /** Standard constructor; calls super(). */
    public InternalServerError(){
        super();
    }

    /** Calls super() with message. */
    public InternalServerError(String message){
        super(message);
    }
}
