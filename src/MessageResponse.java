package familyserver.response;

/** Class that holds everything for a response containing only a message. */

public class MessageResponse{

    /** Message to respond with. */
    public String message;

    /**
     * Constructor that sets the message to passed string.
     *
     * @param response Message that will be sent back in response. 
     */
    
    public MessageResponse(String response){
        message = response;
    }
}
