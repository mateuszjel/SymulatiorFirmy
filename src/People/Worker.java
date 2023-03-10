package People;


import ConsoleGame.Game;
import ConsoleGame.Random;
import ConsoleGame.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Worker extends Person{
    public enum WorkerType {
        PROGRAMMER, SELLER, TESTER;

        public static WorkerType random()  {
            WorkerType[] workerTypes = values();
            return workerTypes[ThreadLocalRandom.current().nextInt(workerTypes.length)];
        }
    }
    private Map<WorkerType,String> workerTypeText = new HashMap<>(){{
        put(WorkerType.PROGRAMMER, "programista");
        put(WorkerType.SELLER, "sprzedawca");
        put(WorkerType.TESTER, "tester");
    }};

    private WorkerType workerType;
    private ArrayList<Game.Technology> availableTechnologies;
    protected Project workingProject;
    protected Game.Technology workingTechnology;
    private Integer dailySalary;

    public Worker(){
        this(WorkerType.random(), null);
    }

    public Worker(WorkerType workerType, Integer dailySalary){
        super();
        this.availableTechnologies = Game.Technology.randomListProbability(30);
        this.workerType = workerType;
        this.dailySalary = Objects.requireNonNullElseGet(dailySalary, () -> 320 + (150 * Random.randInt(80, 100) / 100 * availableTechnologies.size()));
    }

    public void setWorkingProject(Project project, Game.Technology technology){
        if (this.workerType == WorkerType.TESTER){
            this.workingProject = project;
        }else if (this.workerType == WorkerType.PROGRAMMER){
            if (project.getTechnologies().containsKey(technology)) {
                this.workingProject = project;
                this.workingTechnology = technology;
            }
        }
    }

    public Integer getDailySalary() { return this.dailySalary; }
    public Project getProject() { return this.workingProject; }
    public Game.Technology getWorkingTechnology() { return this.workingTechnology; }
    public ArrayList<Game.Technology> getTechnologies(){
        return this.availableTechnologies;
    }
    public WorkerType getWorkerType(){ return this.workerType; }
    public String getWorkerTypeText(){
        return this.workerTypeText.get(this.workerType);
    }
    public Project getWorkingProject() { return this.workingProject;};

    public String toString(){
        StringBuilder result = new StringBuilder(this.firstName + " " + this.lastName);
        if (this.workerType == WorkerType.PROGRAMMER){
            result.append(", ").append(this.workerTypeText.get(this.workerType)).append(" (");
            for(Game.Technology technology: this.availableTechnologies ){
                result.append(Game.technologiesText.get(technology)).append(",");
            }
            result = new StringBuilder(result.substring(0, result.length()-2));
            result.append(")");
        }else{
            result.append(", ").append(this.workerTypeText.get(this.workerType));
        }
        return result.toString();
    }

    public abstract Boolean work();
}
