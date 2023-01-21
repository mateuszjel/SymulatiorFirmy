package People;

import Game.Game;
import Game.Project;

import java.util.ArrayList;

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
