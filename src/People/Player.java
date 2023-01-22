package People;

import Game.Game;
import Game.Project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Player extends Person {
    private ArrayList<Subcontractor> subcontractors = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();
    private boolean searchEmployee = false;
    private Integer searchProject = 0;
    private Integer money = 0;
    final private ArrayList<Game.Technology> technologies = new ArrayList<Game.Technology>(
            Arrays.asList(
                    Game.Technology.BACKEND,
                    Game.Technology.DATABASE,
                    Game.Technology.FRONTEND,
                    Game.Technology.PRESTASHOP,
                    Game.Technology.WORDPRESS
            )
    );

    public Player(String userName){
        this.firstName = userName;
        this.lastName = "";
        subcontractors.add(new Subcontractor(Subcontractor.Experience.LOW));
        subcontractors.add(new Subcontractor(Subcontractor.Experience.MEDIUM));
        subcontractors.add(new Subcontractor(Subcontractor.Experience.HIGH));
    }


    public void searchEmployees(){
        if (this.money >= 5000){
            this.money -= 5000;
            this.searchEmployee = true;
        }
    }

    public void addProject(Project project){
        this.projects.add(project);
        project.setPlayer(this);
    }

    public ArrayList<Employee> getEmployees(){ return new ArrayList<>(this.employees); }
    public ArrayList<Subcontractor> getSubcontractors(){ return new ArrayList<>(this.subcontractors); }
    public ArrayList<Game.Technology> getTechnologies(){ return new ArrayList<>(this.technologies); }
    public ArrayList<Project> getProjects(){ return new ArrayList<>(this.projects); }
    public Integer getMoney(){
        return money;
    }
    public Integer getSearchNewProjectSince(){ return this.searchProject; }
    public void bumpSearchNewProjectSince(){ this.searchProject += 1; }

//    public Employee resetFindEmployees(){
//        if (this.findEmployees){
//            this.findEmployees = false;
//            return new Employee();
//        }
//        return null;
//    }

//    public Project resetSearchNewProjectSince(Date currentDate){
//        if (this.searchNewProjectSince == 5){
//            this.searchNewProjectSince -= 5;
//            return new Project(currentDate);
//        }
//        return null;
//    }

}
