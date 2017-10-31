package familyserver.handler;

import familyserver.access.*;
import familyserver.error.*;
import familyserver.model.*;
import familyserver.request.*;
import familyserver.response.*;
import familyserver.util.*;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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

    public ServerFacade(String databasePath){
        authDAO = new AuthTokenDataAccess(databasePath);
        eventDAO = new EventDataAccess(databasePath);
        personDAO = new PersonDataAccess(databasePath);
        userDAO = new UserDataAccess(databasePath);
    }

    public static void main(String[] args) throws Exception{
        ServerFacade sf = new ServerFacade("hello.db");
        sf.register(new RegisterRequest("cweeks12", "hi", "connorweeks1@gmail.com", "Connor", "Weeks", "M"));
        sf.fill("cweeks12",9);
    }

    /**
     * Registers a new user in the database.
     *
     * @param request Contains the information needed to register a new user.
     * @return A login response with the new auth token.
     */

    public LoginResponse register(RegisterRequest request) throws InternalServerError{

        String newUserId = null;
        String newToken = null;
        try{
            newUserId = userDAO.createNewUser(request);
            newToken = authDAO.newAuthToken(request.getUserName());
        }

        catch(IllegalArgumentException e){
            throw new InternalServerError("Included a bad argument. " + e.getMessage());
        }

        this.fill(request.getUserName(), 4);


        return new LoginResponse(newToken, request.getUserName(), newUserId);
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
            if (!userDAO.checkLogin(request.getUsername(), request.getPassword())){
                throw new NoResultsFoundError();
            }
            loggingInUser = userDAO.getUserByUsername(request.getUsername());
            newToken = authDAO.newAuthToken(request.getUsername());
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

        Decoder decoder = new Decoder();
        String message = null;
        int newPeopleQuantity = 0;



        if (generations == null){
            generations = 4;
        }


        try {
            if (generations < 1){
                throw new IllegalArgumentException();
            }
            User requestingUser = userDAO.getUserByUsername(username);
            if (requestingUser == null){
                throw new IllegalArgumentException();
            }
             // FIRST DELETE ALL THE PEOPLE BELONGING TO THE PERSON requesting
             personDAO.deleteUserPeople(username);
             eventDAO.deleteUserEvents(username);

            LocationGenerator locations = decoder.toLocationGenerator("data/json/locations.json");
            NameGenerator boyNames = decoder.toNameGenerator("data/json/mnames.json");
            NameGenerator girlNames = decoder.toNameGenerator("data/json/fnames.json");
            NameGenerator lastNames = decoder.toNameGenerator("data/json/snames.json");

            newPeopleQuantity = (int)java.lang.Math.pow(2,(generations+1))-1;
            Person[] newPeople = new Person[newPeopleQuantity];


            newPeople[0] = new Person(requestingUser.getId(), username, requestingUser.getFirstName(), requestingUser.getLastName(), requestingUser.getGender(), null,null,null);


            String firstName = null;
            String lastName = null;
            String gender = null;
            for (int i = 1; i < newPeople.length; i++){

                if (i % 2 == 0){
                    firstName = boyNames.getRandomName();
                    gender = "M";
                }
                else {
                    firstName = girlNames.getRandomName();
                    gender = "F";
                }

                lastName = lastNames.getRandomName();

                newPeople[i] = new Person(Utils.generateId(), username, firstName, lastName, gender, null,null,null);
            }

            // At this point, you have all of the people you need, you just need to link them together in a family tree
            for (int i = 1; i < newPeople.length; i+=2){
                // A beautiful wedding
                newPeople[i].setSpouse(newPeople[i+1].getId());
                newPeople[i+1].setSpouse(newPeople[i].getId());
            }

            int currentParentIndex = 1;
            for(int i = 0; currentParentIndex < newPeople.length; i++, currentParentIndex+=2){
                // And the hearts shall turn to their fathers
                newPeople[i].setMother(newPeople[currentParentIndex].getId());
                newPeople[i].setFather(newPeople[currentParentIndex+1].getId());
            }

            for (Person p : newPeople){
                personDAO.createNewPerson(p);
            }

            int eventsMade = 0;
            int generationGap = 0;
            int currentGeneration = 0;
            final int baseYear = 1990;
            final int currentYear = 2017;
            final int yearsBetweenGenerations = 30;
            final int baptismAge = 9;
            final int marriageAge = 23;
            final int deathAge = 70;

            for (int i = 0; i < newPeople.length; i++){

                int birthYear = baseYear - (currentGeneration * yearsBetweenGenerations) + randomPlusOrMinus(5);
                eventDAO.createNewEvent(new Event(Utils.generateId(), username, newPeople[i].getId(), locations.getRandomLocation(), "Birth", Integer.toString(birthYear)));
                eventsMade++;

                System.out.println(birthYear);

                int baptismYear = birthYear + baptismAge + randomPlusOrMinus(1);
                if (baptismYear < currentYear){
                    eventDAO.createNewEvent(new Event(Utils.generateId(), username, newPeople[i].getId(), locations.getRandomLocation(), "Baptism", Integer.toString(baptismYear)));
                    eventsMade++;
                }

                if (i == 0){
                    // The first user is not married yet, or dead
                    continue;
                }

                int deathYear = birthYear + deathAge + randomPlusOrMinus(15);
                if (deathYear < currentYear){
                    eventDAO.createNewEvent(new Event(Utils.generateId(), username, newPeople[i].getId(), locations.getRandomLocation(), "Death", Integer.toString(deathYear)));
                    eventsMade++;
                }

                if (i >= generationGap){
                    currentGeneration++;
                    generationGap += 2*currentGeneration;
                }

            }

            generationGap = 0;
            currentGeneration = 0;

            // More beautiful marriages
            for (int i = 1; i < newPeople.length; i+=2){
                int birthYear = baseYear - (currentGeneration * yearsBetweenGenerations);
                int marriageYear = birthYear + marriageAge + randomPlusOrMinus(10);
                if (marriageYear < currentYear){
                    Location loc = locations.getRandomLocation();
                    eventDAO.createNewEvent(new Event(Utils.generateId(), username, newPeople[i].getId(), loc, "Marriage", Integer.toString(marriageYear)));
                    eventDAO.createNewEvent(new Event(Utils.generateId(), username, newPeople[i+1].getId(), loc, "Marriage", Integer.toString(marriageYear)));
                    eventsMade += 2;
                }
                if (i >= generationGap){
                    currentGeneration++;
                    generationGap += 2*currentGeneration;
                }
            }



            message = "Successfully added " + newPeopleQuantity + " persons and " + eventsMade + " events to the database.";
        }
        catch(InternalServerError e){
            message = "FAIL. " + e.getMessage();
            System.out.println(message);
        }
        catch(IllegalArgumentException e){
            message = "FAIL. Illegal value passed in.";
        }
        catch(IOException e){
            message = "Error reading JSON to generate names";
        }
        return new MessageResponse(message);
    }

    public int randomPlusOrMinus(int offset){
        Random random = new Random();
        return random.nextInt(offset*2 + 1) - offset;
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
                userCount++;
            }
            for (Person p : request.getPersons()){
                System.out.println(p);
                personDAO.createNewPerson(p);
                personCount++;
            }
            for (EventResponse e : request.getEvents()){
                eventDAO.createNewEvent(new Event(e));
                eventCount++;
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

    public PersonResponse person(String token, String personId) throws InvalidAuthTokenError, NoResultsFoundError, InternalServerError{
        String user = null;
        Person personToReturn = null;
        try {
            user = authDAO.userForToken(token);
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


    public PeopleResponse people(String token) throws InvalidAuthTokenError, NoResultsFoundError, InternalServerError{
        String user;
        List<Person> returnedPeople = null;
        try {
            user = authDAO.userForToken(token);
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

    public EventResponse event(String token, String eventId) throws InvalidAuthTokenError, NoResultsFoundError, InternalServerError{

        String user = null;
        Event eventToReturn = null;
        try {
            user = authDAO.userForToken(token);
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

    public EventsResponse events(String token) throws InvalidAuthTokenError, NoResultsFoundError, InternalServerError{
        String user;
        List<Event> returnedEvents = null;
        try {
            user = authDAO.userForToken(token);
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
