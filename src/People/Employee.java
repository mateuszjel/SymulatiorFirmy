package People;

import Game.Project;

import java.util.concurrent.ThreadLocalRandom;

public class Employee extends  Worker{

    private Player employer;

    public Employee (Player employer){
        super();
        this.employer = employer;
    }

    public Player getPlayer(){
        return this.employer;
    }

}
