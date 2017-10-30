package familyserver.error;

/** Custom exception thrown with no results found.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class NoResultsFoundError extends Exception{

    /** Standard constructor; calls super(). */
    public NoResultsFoundError(){
        super();
    }

    /** Calls super() with message. */
    public NoResultsFoundError(String message){
        super(message);
    }
}
