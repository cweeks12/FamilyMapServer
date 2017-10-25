package familyserver.test;

import org.junit.runner.*;

public class TestDriver{

    public static void main(String[] args){

        JUnitCore.main(
                "familyserver.test.PersonTest",
                "familyserver.test.PersonDataAccessTest",
                "familyserver.test.EventTest",
                "familyserver.test.EventDataAccessTest",
                "familyserver.test.UserTest",
                "familyserver.test.UserDataAccessTest"
                );
    }
}
