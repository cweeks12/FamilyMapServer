package familyserver.handler;

import familyserver.access.*;
import familyserver.error.*;
import familyserver.model.*;
import familyserver.request.*;
import familyserver.response.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Interface for the server to interact with everything in the
 * server and database.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */
public class ServerFacade{

    AuthTokenDataAccess authDAO;
    EventDataAccess eventDAO;
    PersonDataAccess personDAO;
    UserDataAccess userDAO;

    ServerFacade(String databasePath){
        authDAO = new AuthTokenDataAccess(databasePath);
        eventDAO = new EventDataAccess(databasePath);
        personDAO = new PersonDataAccess(databasePath);
        userDAO = new UserDataAccess(databasePath);
    }


    /**
     * Registers a new user in the database.
     *
     * @param request Contains the information needed to register a new user.
     * @return A login response with the new auth token.
     */

    public LoginResponse register(RegisterRequest request) throws UsernameAlreadyTakenError{
        return null;
    }


    /**
     * Performs a login and returns a response.
     *
     * @param request Contains the information needed to login.
     * @return A login response with the new auth token.
     */

    public LoginResponse login(LoginRequest request) throws InternalServerError, NoResultsFoundError{

        User loggingInUser = null;
        String newToken = null;
        try {
            loggingInUser = userDAO.getUserByUsername(request.getUsername());
            if (!loggingInUser.getPassword().equals(request.getPassword())){
                throw new NoResultsFoundError();
            }
            newToken = authDAO.newAuthToken(loggingInUser.getUsername());
        }
        catch(IllegalArgumentException e){
            throw new InternalServerError(e.getMessage());
        }
        return new LoginResponse(newToken, loggingInUser.getUsername(), loggingInUser.getId());
    }


    /**
     * Clears all of the data out of the database.
     *
     * @return A message whether or not the request worked.
     */

    public MessageResponse clear(){
        String message = "Clear succeeded.";
        try{
            authDAO.deleteAllAuthTokens();
            eventDAO.deleteAllEvents();
            personDAO.deleteAllPeople();
            userDAO.deleteAllUsers();
        }
        catch(InternalServerError e){
            message = "FAIL. " + e.getMessage();
        }
        catch(IllegalArgumentException e){
            message = "FAIL. Illegal argument passed in.";
        }

        return new MessageResponse(message);
    }


    /**
     * Fills the database with random family information for the given user.
     *
     * @param username The username whose family you are filling.
     * @param generations The number of generations to fill.
     * @return A message response telling whether or not the request worked.
     */

    public MessageResponse fill(String username, Integer generations){
        User userMakingRequest = null;
        String message = null;
        if (generations == null){
            generations = 4;
        }

        try {
            if (generations < 1){
                throw new IllegalArgumentException();
            }

            int personsMade = 0;
            int eventsMade = 0;

            userMakingRequest = userDAO.getUserByUsername(username);
            if (userMakingRequest == null){
                throw new IllegalArgumentException();
            }
            message = "Successfully added " + personsMade + " persons and " + eventsMade + " events to the database.";
        }
        catch(InternalServerError e){
            message = "FAIL. " + e.getMessage();
        }
        catch(IllegalArgumentException e){
            message = "FAIL. Illegal value passed in.";
        }
        return new MessageResponse(message);
    }


    /**
     * Fills the database with the given family information.
     *
     * @param request A LoadRequest with all the data to add into the database.
     * @return A message response telling whether or not the request worked.
     */

    public MessageResponse load(LoadRequest request){
        String message = null;

        int userCount = 0;
        int personCount = 0;
        int eventCount = 0;

        try {
            for (User u : request.getUsers()){
                userDAO.createNewUser(u);
            }
            for (Person p : request.getPersons()){
                personDAO.createNewPerson(p);
            }
            for (Event e : request.getEvents()){
                eventDAO.createNewEvent(e);
            }
            message = "Successfully added " + userCount + " users, " + personCount + " persons, and " + eventCount + " events into the database.";
        }

        catch(InternalServerError e){
            message = "FAIL. " + e.getMessage();
        }
        catch(IllegalArgumentException e){
            message = "FAIL. Illegal value passed in.";
        }

        return new MessageResponse(message);
    }


    /**
     * Finds the person associated with the personId and sends the information back.
     *
     * @param token The authorization token used to request the data.
     * @param personId The ID of the person you're searching for.
     * @return A PersonResponse object with the requested person's information in it.
     */

    public PersonResponse person(AuthToken token, String personId) throws InvalidAuthTokenError, NoResultsFoundError, InternalServerError{
        String user = null;
        Person personToReturn = null;
        try {
            user = authDAO.userForToken(token.getToken());
            if (user == null){
                throw new InvalidAuthTokenError();
            }
            personToReturn = personDAO.getPersonById(personId);
            if (personToReturn == null){
                throw new NoResultsFoundError();
            }
        }
        catch(IllegalArgumentException e){
            throw new InternalServerError(e.getMessage());
        }
        return new PersonResponse(personToReturn);
    }


    /**
     * Finds all people associated with the user indicated by the authentication token.
     *
     * @param token The authentication token from the user requesting.
     * @return A PeopleResponse object with all of the data of all of the people associated with the requesting user.
     */


    public PeopleResponse people(AuthToken token) throws InvalidAuthTokenError, NoResultsFoundError, InternalServerError{
        String user;
        List<Person> returnedPeople = null;
        try {
            user = authDAO.userForToken(token.getToken());
            if (user == null){
                throw new InvalidAuthTokenError();
            }

            returnedPeople = personDAO.getAllPeople(user);
            if (returnedPeople == null){
                throw new NoResultsFoundError();
            }
        }
        catch(IllegalArgumentException e){
            throw new InternalServerError(e.getMessage());
        }
        return new PeopleResponse(returnedPeople);
    }


    /**
     * Finds the event associated with the eventId and sends the information back.
     *
     * @param token The authorization token used to request the data.
     * @param eventId The ID of the event you're searching for.
     * @return An EventResponse object with the requested event's information in it.
     */

    public EventResponse event(AuthToken token, String eventId) throws InvalidAuthTokenError, NoResultsFoundError, InternalServerError{

        String user = null;
        Event eventToReturn = null;
        try {
            user = authDAO.userForToken(token.getToken());
            if (user == null){
                throw new InvalidAuthTokenError();
            }
            eventToReturn = eventDAO.getEventById(eventId);
            if (eventToReturn == null){
                throw new NoResultsFoundError();
            }
        }
        catch(IllegalArgumentException e){
            throw new InternalServerError(e.getMessage());
        }
        return new EventResponse(eventToReturn);
    }

    /**
     * Finds all events associated with the user indicated by the authentication token.
     *
     * @param token The authentication token from the user requesting.
     * @return An EventsResponse object with all of the data of all of the events associated with the requesting user.
     */

    public EventsResponse events(AuthToken token) throws InvalidAuthTokenError, NoResultsFoundError, InternalServerError{
        String user;
        List<Event> returnedEvents = null;
        try {
            user = authDAO.userForToken(token.getToken());
            if (user == null){
                throw new InvalidAuthTokenError();
            }

            returnedEvents = eventDAO.getAllEvents(user);
            if (returnedEvents == null){
                throw new NoResultsFoundError();
            }
        }
        catch(IllegalArgumentException e){
            throw new InternalServerError(e.getMessage());
        }
        return new EventsResponse(returnedEvents);
    }

}
