package familyserver.util;

import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import familyserver.model.*;

public class Decoder {
    private Gson gson;

    public Decoder(){
        gson = new Gson();
    }

    public LocationGenerator toLocationGenerator(String file) throws IOException{
        return gson.fromJson(new String(Files.readAllBytes(Paths.get(file))), LocationGenerator.class);
    }

    public NameGenerator toNameGenerator(String file) throws IOException{
        return gson.fromJson(new String(Files.readAllBytes(Paths.get(file))), NameGenerator.class);
    }
}
