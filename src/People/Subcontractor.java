package People;

public class Subcontractor extends Worker {
    public enum Experience {
        LOW, MEDIUM, HIGH,
    }
    private Experience experience;
    public Subcontractor (Experience experience){
        super(WorkerType.PROGRAMMER);
        this.experience = experience;
    }

}
