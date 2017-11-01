package familyserver.test;

import familyserver.model.Person;
import org.junit.*;
import static org.junit.Assert.*;

public class PersonTest{

    private Person person;
    private Person secondPerson;


    /** Creates two Person objects and opens a connection to database */
    @Before
    public void setup(){
        person = new Person("DEADBEEF",
                            "cweeks12",
                            "Abby",
                            "Grayson",
                            "F",
                            null,
                            null,
                            "CA43291F");

        secondPerson = new Person("CA8F191F",
                                    "cweeks12",
                                    "Amy",
                                    "Grayson" ,
                                    "F",
                                    null,
                                    null,
                                    "E4827190");

    }

    @Test
    public void testEquals(){
        assertFalse(person.equals(secondPerson));

        assertTrue(person.equals(person));

        assertFalse(person.equals(new Person("DEADBEEF",
                                                "cweeks12",
                                                "Sarah",
                                                "Grayson",
                                                "F",
                                                null,
                                                null,
                                                null)));
    }

    @Test
    public void testGettersSetters(){

        person.setId("ABCEDF23");
        assertEquals("ABCEDF23", person.getId());

        person.setDescendant("byuowns");
        assertEquals("byuowns", person.getDescendant());

        person.setFirstName("Charles");
        assertEquals("Charles", person.getFirstName());

        person.setLastName("Chaplin");
        assertEquals("Chaplin", person.getLastName());

        person.setGender("M");
        assertEquals("M", person.getGender());

        person.setFather("2819D920");
        assertEquals("2819D920", person.getFather());

        person.setMother("516A8292");
        assertEquals("516A8292", person.getMother());

        person.setSpouse("8192AC8E");
        assertEquals("8192AC8E", person.getSpouse());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenderSetterError(){
        person.setGender(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenderInvalidGenderError(){
        person.setGender("Other");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNullInputSetterError(){
        person.setFirstName(null);
    }

}
