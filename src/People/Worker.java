package People;


import Game.Game;
import Game.Project;

import java.util.ArrayList;

public abstract class Worker extends Person{
    private ArrayList<Game.Technology> availableTechnologies;
    private Project workingProject;
    private Game.Game.Technology workingTechnology;

    public Worker(){
        super();
        this.availableTechnologies = Game.Technology.randomListProbability(30);
    }

    public ArrayList<Game.Technology> getTechnologies(){
        return this.availableTechnologies;
    }
}
