package People;

import ConsoleGame.Random;

public class Employee extends  Worker{

    private Player employer;
    private Integer daysWorked;

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
    public Integer getDaysWorked(){
        return this.daysWorked;
    }
    public void resetDaysWorked(){
        this.daysWorked = 0;
    }

    public Boolean work(){
        if(Random.randBool(95)) {
            if (this.workingProject.workOnProject(this.workingTechnology)){
                this.daysWorked += 1;
                return true;
            }
            return false;
        }
        return true;
    }
    public void test(){
        if(Random.randBool(95)) {
            this.daysWorked += 1;
            this.workingProject.testProject();
        }
    }
    public void searchNewProjects(){
        if(Random.randBool(95)) {
            this.employer.bumpSearchNewProjectSince();
        }
    }
}
