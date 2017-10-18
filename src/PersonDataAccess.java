package familyserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the database for Person objects.
 *
 * @author Connor Weeks <connorweeks1@gmail.com>
 * @version 1.0
 */

public class PersonDataAccess{

    // Driver string for the class
    private final String driver = "org.sqlite.JDBC";

    // Holds the name of the database
    private String dbName = null;

    /** Builds a new user data access object to interact with user database.
     *
     * @param databasePath Path to the database.
     */
    public PersonDataAccess(String databasePath){

        try{
            Class.forName(driver);
        }
        catch (ClassNotFoundException e){
            System.out.println("Error finding the SQLite driver.");
        }

        String dbName = "jdbc:sqlite:"+databasePath;
    }

    /** 
     * Adds a new person to the database.
     *
     * @param personToCreate the new person to add to the database.
     * @return The ID of the new person added.
     */

    public String createNewPerson(Person personToCreate){

        try (Connection connection = DriverManager.getConnection(dbName)){

            String insert = "INSERT INTO person VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // If the person you're given doesn't have an ID yet, assign one
            if (personToCreate.getId() == null){
                personToCreate.setId(Utils.generateId());
            }

            try{
                PreparedStatement stmt = connection.prepareStatement(insert);
                stmt.setString(1, personToCreate.getId());
                stmt.setString(2, personToCreate.getDescendant());
                stmt.setString(3, personToCreate.getFirstName());
                stmt.setString(4, personToCreate.getLastName());
                stmt.setString(5, personToCreate.getGender());
                stmt.setString(6, personToCreate.getFather());
                stmt.setString(7, personToCreate.getMother());
                stmt.setString(8, personToCreate.getSpouse());

                stmt.executeUpdate();
                stmt.close();
            }
            catch(SQLException e){
                 System.out.println("Error Updating the fields and doing the update.");
            }

        }

        catch(SQLException e){
            System.out.println("The connection to database failed.");
        }

        return personToCreate.getId();
    }


    /** 
     * Queries the database and returns the Person object from the username.
     *
     * @param personID The unique person ID to get the person from.
     * @return The person that responds to the given ID number.
     */

    public Person getPersonById(String personID){

        ResultSet queryResult = null;
        try (Connection connection = DriverManager.getConnection(dbName)){

            String query = "SELECT * FROM person WHERE personId = ? ";

            try{
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, personID);

                queryResult = stmt.executeQuery();
                stmt.close();
            }
            catch(SQLException e){
                 System.out.println("Error querying the database.");
            }

            // Checking how many rows there are.
            queryResult.last();
            if (queryResult.getRow() > 1){
                System.out.println("There is more than one user that has that ID number. Uh oh.");
            }
            queryResult.beforeFirst();
            queryResult.next();

        }

        catch(SQLException e){
            System.out.println("The connection to database failed.");
        }


        return new Person(queryResult);
    }


    /** 
     * Queries the database and returns all Persons that belong to the given user.
     *
     * @param descendant The username that's requesting all their ancestors.
     * @return A list of all people that belong to them.
     */

    public List<Person> getAllPeople(String descendant){

        ResultSet queryResult = null;
        PreparedStatement stmt = null;
        ArrayList<Person> listOfPeople = new ArrayList<Person>();

        try (Connection connection = DriverManager.getConnection(dbName)){

            String query = "SELECT * FROM person WHERE descendant = ? ";

            try{
                stmt = connection.prepareStatement(query);
                stmt.setString(1, descendant);

                queryResult = stmt.executeQuery();
                stmt.close();
            }
            catch(SQLException e){
                 System.out.println("Error querying the database.");
            }

            while (queryResult.next()){
                listOfPeople.add(new Person(queryResult));
            }
        }

        catch(SQLException e){
            System.out.println("The connection to database failed.");
        }

        return listOfPeople;
    }


    /**
     * Drops all people in the database. This is called when /clear is requested.
     */

    public void deleteAllPeople(){
        try (Connection connection = DriverManager.getConnection(dbName)){

            String delete = "DELETE FROM person";

            try{
                PreparedStatement stmt = connection.prepareStatement(delete);

                stmt.executeUpdate();
                stmt.close();
            }
            catch(SQLException e){
                 System.out.println("Error querying the database.");
            }

        }

        catch(SQLException e){
            System.out.println("The connection to database failed.");
        }
    }
} 
