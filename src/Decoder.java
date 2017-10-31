package familyserver.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import familyserver.model.*;
import familyserver.request.*;
import familyserver.response.*;

public class Decoder {
    private Gson gson;

    public Decoder(){
        gson = new Gson();
    }

    public LocationGenerator toLocationGenerator(String file) throws IOException, JsonParseException{
        return gson.fromJson(new String(Files.readAllBytes(Paths.get(file))), LocationGenerator.class);
    }

    public NameGenerator toNameGenerator(String file) throws IOException, JsonParseException{
        return gson.fromJson(new String(Files.readAllBytes(Paths.get(file))), NameGenerator.class);
    }

    public RegisterRequest toRegisterRequest(String data) throws JsonParseException{
        return gson.fromJson(data, RegisterRequest.class);
    }

    public LoginRequest toLoginRequest(String data) throws JsonParseException{
        return gson.fromJson(data, LoginRequest.class);
    }

    public LoadRequest toLoadRequest(String data) throws JsonParseException{
        return gson.fromJson(data, LoadRequest.class);
    }
}
