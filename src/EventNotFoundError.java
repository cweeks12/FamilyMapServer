package familyserver.error;

/** Custom exception thrown with event not found.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class EventNotFoundError extends Exception{

    /** Standard constructor; calls super(). */
    public EventNotFoundError(){
        super();
    }

    /** Calls super() with message. */
    public EventNotFoundError(String message){
        super(message);
    }
}
