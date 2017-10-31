package familyserver.response;

import familyserver.model.Event;
import java.util.List;
import java.util.ArrayList;

/** Class that holds everything for a events response. */

public class EventsResponse{

    /** Array of Event objects to load in. */
    List<EventResponse> data;

    /**
     * Constructor that creates the object to return in response to the GET request.
     *
     * @param events A list of events to return in the response.
     */

    public EventsResponse(List<Event> events){
        data = new ArrayList<EventResponse>();
        for (Event e : events){
            data.add(new EventResponse(e));
        }
    }
}
