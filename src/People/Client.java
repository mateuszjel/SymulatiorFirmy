package People;

import ConsoleGame.Random;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Client  extends Person {
    public enum ClientType {
        EASY, MEDIUM, HARD;

        public static Client.ClientType random()  {
            Client.ClientType[] clientTypes = values();
            return clientTypes[ThreadLocalRandom.current().nextInt(clientTypes.length)];
        }
    }
    private ClientType clientType;
    public Client(){
        this.clientType = ClientType.random();
    }

    public Boolean projectNotWorking(){
        switch (this.clientType){
            case EASY -> { return true; }
            case MEDIUM -> { return Random.randBool(50); }
            case HARD -> { return false; }
        }
        return null;
    }

    public Boolean projectLate(Integer days){
        switch (this.clientType){
            case EASY -> {
                if(days <= 7){
                    return Random.randBool(20);
                }else{
                    return false;
                }
            }
            case MEDIUM, HARD -> { return false; }
        }
        return null;
    }

    public Integer payment(){
        switch (this.clientType){
            case EASY -> {
                if(Random.randBool(30)){
                    return 7;
                }
            }
            case MEDIUM -> { return 0; }
            case HARD -> {
                if(Random.randBool(5)){
                    return 30;
                }else if(Random.randBool(30)){
                    return 7;
                }
            }
        }
        return 0;
    }

}
