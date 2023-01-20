import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public enum Technology{
        FRONTEND, BACKEND, DATABASE, MOBILE, WORDPRESS, PRESTASHOP;
        
        public static Technology random()  {
            Technology[] technologies = values();
            return technologies[ThreadLocalRandom.current().nextInt(technologies.length)];
        }
    }
    private ArrayList<User> users = new ArrayList<User>();

    public Game(Integer userCount) {
        for(int i = 0; i < userCount; i++){
            users.add(new User());
        }
    }

}
