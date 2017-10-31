package familyserver.util;

import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Class that encodes object to their JSON representation. */

public class Encoder {
    private static Gson gson;

    /** Creates a new encoder object. */
    public Encoder(){
        gson = new Gson();
    }


    /** Changes the object to its JSON representation.
    *
    * @param o Object to serialize.
    * @return String representation of the object.
    */

    public static String toJson(Object o){
        return gson.toJson(o);
    }
}
