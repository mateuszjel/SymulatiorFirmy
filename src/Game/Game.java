package Game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public enum Technology{
        FRONTEND, BACKEND, DATABASE, MOBILE, WORDPRESS, PRESTASHOP;
        
        public static Technology random()  {
            Technology[] technologies = values();
            return technologies[ThreadLocalRandom.current().nextInt(technologies.length)];
        }

        public static ArrayList<Technology> randomList(Integer count){
            ArrayList<Technology> technologies = new ArrayList<Technology>();
            for (int i = 0; i < count; i++) {
                Game.Technology technology = Game.Technology.random();
                if (!technologies.contains(technology)) {
                    technologies.add(technology);
                }else{
                    i--;
                }
            }
            return technologies;
        }

        public static ArrayList<Technology> randomListProbability(Integer probability){
            ArrayList<Technology> technologies = new ArrayList<Technology>();
            Integer reducedProbability = 100;
            while (Random.randBool(probability)){
                Game.Technology technology = Game.Technology.random();
                if (!technologies.contains(technology)) {
                    technologies.add(technology);
                    reducedProbability -= probability;
                }
            }
            return technologies;
        }
    }
    private ArrayList<User> users = new ArrayList<User>();

    public Game(Integer userCount) {
        for(int i = 0; i < userCount; i++){
            users.add(new User());
        }
    }

}
