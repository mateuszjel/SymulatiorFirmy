import java.util.concurrent.ThreadLocalRandom;

public class Random {
    public static Integer randInt(Integer min, Integer max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
