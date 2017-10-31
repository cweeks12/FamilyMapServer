package familyserver.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import familyserver.model.*;
import familyserver.request.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Class that decodes JSON into specific classes. */

public class Decoder {
    private Gson gson;

    /** Creates a new decoder. */
    public Decoder(){
        gson = new Gson();
    }


    /** Converts a file to a new LocationGenerator.
    *
    * @param file File that contains the LocationGenerator JSON.
    * @return LocationGenerator represented by the contents of the file.
    */

    public LocationGenerator toLocationGenerator(String file) throws IOException, JsonParseException{
        return gson.fromJson(new String(Files.readAllBytes(Paths.get(file))), LocationGenerator.class);
    }


    /** Converts a file to a new NameGenerator.
    *
    * @param file File that contains the NameGenerator JSON.
    * @return NameGenerator represented by the contents of the file.
    */

    public NameGenerator toNameGenerator(String file) throws IOException, JsonParseException{
        return gson.fromJson(new String(Files.readAllBytes(Paths.get(file))), NameGenerator.class);
    }


    /** Converts a string to a RegisterRequest.
    *
    * @param data String that contains the RegisterRequest object.
    * @return RegisterRequest holding contents of the string.
    */

    public RegisterRequest toRegisterRequest(String data) throws JsonParseException{
        return gson.fromJson(data, RegisterRequest.class);
    }


    /** Converts a string to a LoginRequest.
    *
    * @param data String that contains the LoginRequest object.
    * @return LoginRequest holding contents of the string.
    */

    public LoginRequest toLoginRequest(String data) throws JsonParseException{
        return gson.fromJson(data, LoginRequest.class);
    }


    /** Converts a string to a LoadRequest.
    *
    * @param data String that contains the LoadRequest object.
    * @return LoadRequest holding contents of the string.
    */

    public LoadRequest toLoadRequest(String data) throws JsonParseException{
        return gson.fromJson(data, LoadRequest.class);
    }
}
