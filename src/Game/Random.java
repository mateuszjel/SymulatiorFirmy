package Game;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
    public static Integer randInt(Integer min, Integer max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static boolean randBool(Integer probability){
        return ThreadLocalRandom.current().nextInt(0, 100) < probability;
    }
}
