package familyserver.test;

import familyserver.model.Event;
import familyserver.model.Location;
import org.junit.*;
import static org.junit.Assert.*;

public class EventTest{

    Event event;
    Event secondEvent;

    @Before
    public void setup(){
        event = new Event("ABCDE431",
                            "cweeks12",
                            "54382BAC",
                            new Location("Provo", "United States", 40.2338, -111.6585),
                            "Marriage",
                            "2016");

        secondEvent = new Event("A2918C31",
                                "cweeks12",
                                "8192837C",
                                new Location("Kansas City", "United States", 40.7281, -97.2817),
                                "Birth",
                                "1997");
    }

    @Test
    public void testEquals(){
        assertFalse(event.equals(secondEvent));

        assertTrue(event.equals(event));

        assertFalse(event.equals(new Event("ABCDE431", "cweeks12", "54382BAC", new Location("Albany", "United States", 40.2338, -111.6585), "Marriage", "2016")));
    }

    @Test
    public void testGettersSetters(){

        event.setId("ABCEDF23");
        assertEquals("ABCEDF23", event.getId());

        event.setUsername("byuowns");
        assertEquals("byuowns", event.getUsername());

        event.setPersonId("12354672");
        assertEquals("12354672", event.getPersonId());

        Location venice = new Location("Venice", "Italy", 32.0192, 9.2819);
        event.setEventLocation(venice);
        assertEquals(venice, event.getEventLocation());

        event.setEventType("Baptism");
        assertEquals("Baptism", event.getEventType());

        event.setYear("1902");
        assertEquals("1902", event.getYear());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSetterError(){
        event.setEventType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArgumentErrorInConstructor(){
        event = new Event(null, "cweeks12", "54382BAC", new Location("Provo", "United States", 40.2338, -111.6585), "Marriage", "2016");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidYearError(){
        event.setYear(null);
    }

}
