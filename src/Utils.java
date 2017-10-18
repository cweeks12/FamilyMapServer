package familyserver;

import java.lang.StringBuilder;
import java.util.Random;

public class Utils{

    public static String generateId(){
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            token.append(Integer.toHexString(random.nextInt(16)));
        }
        return token.toString();
    }
}
