package familyserver.util;

import java.lang.StringBuilder;
import java.util.Random;

/** Class that contains any functions needed for the server to perform. */
public class Utils{

    /** Generates random 8-hex-digit ID's for general use in the system. */
    public static String generateId(){
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            token.append(Integer.toHexString(random.nextInt(16)));
        }
        return token.toString();
    }
}
