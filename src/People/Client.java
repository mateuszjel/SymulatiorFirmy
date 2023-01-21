package People;

import java.util.concurrent.ThreadLocalRandom;

public class Client  extends Person {
    public enum ClientType {
        EASY, MEDIUM, HARD;

        public static Client.ClientType random()  {
            Client.ClientType[] clientTypes = values();
            return clientTypes[ThreadLocalRandom.current().nextInt(clientTypes.length)];
        }
    }

}
