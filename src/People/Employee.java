package People;

import ConsoleGame.Random;

public class Employee extends  Worker{

    private Player employer;

    public Employee (){
        super();
//        this.employer = employer;
    }

    public void setPlayer(Player player){
        this.employer = player;
    }
    public Player getPlayer(){
        return this.employer;
    }

    public void work(){
        this.workingProject.workOnProject(this.workingTechnology);
    }
}
