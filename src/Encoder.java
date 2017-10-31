package familyserver.util;

import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Encoder {
    private static Gson gson;

    public Encoder(){
        gson = new Gson();
    }

    public static String toJson(Object o){
        return gson.toJson(o);
    }
}
