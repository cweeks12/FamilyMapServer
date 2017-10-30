package familyserver.access;

import familyserver.error.InternalServerError;
import familyserver.model.Person;
import familyserver.util.Utils;
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
    private String dbName;

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

        dbName = "jdbc:sqlite:"+databasePath;
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(dbName);

            PreparedStatement stmt = connection.prepareStatement("DROP TABLE IF EXISTS person");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS person (personId text PRIMARY KEY, descendant text NOT NULL, firstName text NOT NULL, lastName text NOT NULL, gender text NOT NULL, father text, mother text, spouse text)");
            stmt.executeUpdate();
            stmt.close();

        }
        catch (SQLException e){
            System.out.println("Error creating database");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new person to the database.
     *
     * @param personToCreate the new person to add to the database.
     * @return The ID of the new person added.
     */

    public String createNewPerson(Person personToCreate) throws InternalServerError{

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
                 throw new InternalServerError("Error Updating the fields and doing the update." + e.getMessage());
            }

        }

        catch(SQLException e){
             throw new InternalServerError("The connection to database failed.");
        }

        return personToCreate.getId();
    }


    /**
     * Queries the database and returns the Person object from the username.
     *
     * @param personID The unique person ID to get the person from.
     * @return The person that responds to the given ID number.
     */

    public Person getPersonById(String personID) throws InternalServerError{

        if (personID == null){
            return null;
        }

        ResultSet queryResult = null;
        Connection connection = null;
        PreparedStatement stmt = null;

        String id;
        String descendant;
        String firstName;
        String lastName;
        String gender;
        String father;
        String mother;
        String spouse;

        try {
            connection = DriverManager.getConnection(dbName);
        }
        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }

        String query = "SELECT * FROM person WHERE personId = ? ";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, personID);

            queryResult = stmt.executeQuery();
        }
        catch(SQLException e){
             throw new InternalServerError("Error querying the database." + e.getMessage());
        }

        try{
            id = queryResult.getString("personId");
            descendant = queryResult.getString("descendant");
            firstName = queryResult.getString("firstName");
            lastName = queryResult.getString("lastName");
            gender = queryResult.getString("gender");
            father = queryResult.getString("father");
            mother = queryResult.getString("mother");
            spouse = queryResult.getString("spouse");
        }
        catch (SQLException e){
            // If there is an exception here, it's because there are no results in the set.
            return null;
        }

        try{
            queryResult.close();
            stmt.close();
            connection.close();
            connection = null;
        }
        catch (SQLException e){
             throw new InternalServerError("Error closing connection." + e.getMessage());
        }

        return new Person(id, descendant, firstName, lastName, gender, father, mother, spouse);
    }


    /**
     * Queries the database and returns all Persons that belong to the given user.
     *
     * @param descendant The username that's requesting all their ancestors.
     * @return A list of all people that belong to them.
     */

    public List<Person> getAllPeople(String personQuerying) throws InternalServerError{

        if (personQuerying == null){
            return null;
        }

        Connection connection = null;
        ResultSet queryResult = null;
        PreparedStatement stmt = null;
        ArrayList<Person> listOfPeople = new ArrayList<Person>();

        String id;
        String descendant;
        String firstName;
        String lastName;
        String gender;
        String father;
        String mother;
        String spouse;

        try{
            connection = DriverManager.getConnection(dbName);
        }
        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }

        String query = "SELECT * FROM person WHERE descendant = ? ";

        try{
            stmt = connection.prepareStatement(query);
            stmt.setString(1, personQuerying);

            queryResult = stmt.executeQuery();
        }
        catch(SQLException e){
            throw new InternalServerError("Error querying the database.");
        }

        try{

            while (queryResult.next()){
                id = queryResult.getString("personId");
                descendant = queryResult.getString("descendant");
                firstName = queryResult.getString("firstName");
                lastName = queryResult.getString("lastName");
                gender = queryResult.getString("gender");
                father = queryResult.getString("father");
                mother = queryResult.getString("mother");
                spouse = queryResult.getString("spouse");
                listOfPeople.add(new Person(id, descendant, firstName, lastName, gender, father, mother, spouse));
            }
        }
        catch (SQLException e){
            // If there is an exception here, it's because there are no results in the set.
            return null;
        }

        try {
            queryResult.close();
            stmt.close();
            connection.close();
            connection = null;
        }
        catch (SQLException e){
            throw new InternalServerError("Error closing the connection to database." + e.getMessage());
        }

        // If nobody was added to the list, return a null object
        if (listOfPeople.size() == 0){
            return null;
        }

        return listOfPeople;
    }


    /**
     * Drops all people in the database. This is called when /clear is requested.
     */

    public void deleteAllPeople() throws InternalServerError{
        try (Connection connection = DriverManager.getConnection(dbName)){

            String delete = "DELETE FROM person";

            try{
                PreparedStatement stmt = connection.prepareStatement(delete);

                stmt.executeUpdate();
                stmt.close();
            }
            catch(SQLException e){
                throw new InternalServerError("Error querying the database.");
            }

        }

        catch(SQLException e){
            throw new InternalServerError("The connection to database failed.");
        }
    }
}
