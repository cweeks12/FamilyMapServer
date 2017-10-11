package familyserver;

/** Custom exception thrown when requesting data not owned by the requestee.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class NotYourDataError extends Exception{

    /** Standard constructor; calls super(). */
    public NotYourDataError(){
        super();
    }

    /** Calls super() with message. */
    public NotYourDataError(String message){
        super(message);
    }
}
