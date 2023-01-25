package People;

import ConsoleGame.Project;
import ConsoleGame.Random;

public class Subcontractor extends Worker {
    public enum Experience {
        LOW, MEDIUM, HIGH,
    }

    public static Integer getDailySalary(Experience experience){
        switch (experience){
            case HIGH -> { return 1200; }
            case MEDIUM -> { return 1000; }
            case LOW -> { return 800; }
        }
        return null;
    }

    private Experience experience;

    public Subcontractor (Experience experience){
        super(WorkerType.PROGRAMMER, getDailySalary(experience));
        this.experience = experience;
    }

    public Boolean work(){
        switch (this.experience){
            case HIGH -> { return this.workingProject.workOnProject(this.workingTechnology); }
            case MEDIUM -> {
                if(Random.randBool(10)){
                    this.workingProject.requireFix(1);
                }
                return this.workingProject.workOnProject(this.workingTechnology);
            }
            case LOW -> {
                if(Random.randBool(80)){
                    return this.workingProject.workOnProject(this.workingTechnology);
                }
                if(Random.randBool(20)){
                    this.workingProject.requireFix(1);
                }
                return true;
            }
        }
        return null;
    }
}
