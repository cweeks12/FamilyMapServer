package familyserver.util;

import java.lang.StringBuilder;
import java.util.Random;

public class Utils{

    public static void main(String[] args){
        for (int i = 0; i < 20; i++){
            System.out.println(generateId());
        }
    }

    public static String generateId(){
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            token.append(Integer.toHexString(random.nextInt(16)));
        }
        return token.toString();
    }
}
