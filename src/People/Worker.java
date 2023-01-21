package People;


import Game.Game;
import Game.Project;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Worker extends Person{
    public enum WorkerType {
        PROGRAMMER, SELLER, TESTER;

        public static WorkerType random()  {
            WorkerType[] workerTypes = values();
            return workerTypes[ThreadLocalRandom.current().nextInt(workerTypes.length)];
        }
    }
    private WorkerType workerType;
    private ArrayList<Game.Technology> availableTechnologies;
    private Project workingProject;
    private Game.Game.Technology workingTechnology;
    private Integer dailySalary;

    public Worker(){
        this(WorkerType.random());
    }
    public Worker(WorkerType workerType){
        super();
        this.availableTechnologies = Game.Technology.randomListProbability(30);
        this.workerType = workerType;
//        this.dailySalary = 320 + (150 +)
    }

    public ArrayList<Game.Technology> getTechnologies(){
        return this.availableTechnologies;
    }

    public String toString(){
        String result = this.firstName + " " + this.lastName;
        switch (this.workerType){
            case PROGRAMMER -> {
                result += ", programista (";
                for(Game.Technology technology: this.availableTechnologies ){
                    result += Game.technologiesText.get(technology) + ",";
                }
                result.substring(0, result.length()-1);
                result += ")";
            }
            case SELLER -> { result += ", sprzedawca"; }
            case TESTER -> { result += ", tester"; }
        }
        return result;
    }
}
