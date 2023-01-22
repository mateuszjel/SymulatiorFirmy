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
    private Integer accountingDays = 0;
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
        if (this.money >= Game.SEARCH_NEW_EMPLOYEE_COST){
            this.money -= Game.SEARCH_NEW_EMPLOYEE_COST;
            this.searchEmployee = true;
        }
    }

    public void addAccountingDay(){
        this.accountingDays += 1;
    }

    public void addProject(Project project){
        this.projects.add(project);
        project.setPlayer(this);
    }
    public void addEmployee(Employee employee){
        this.employees.add(employee);
        employee.setPlayer(this);
    }

    public void fireEmployee(Employee employee){
        if (this.employees.contains(employee)){
            this.employees.remove(employee);
            employee.setPlayer(null);
        }
    }

    public void addMoney(Integer money){
        this.money += money;
    }
    public void removeMoney(Integer money){
        if(this.money >= money) {
            this.money -= money;
        }
    }

    public void finishProject(Project project){

    }

    public ArrayList<Employee> getAccountingDays(){ return new ArrayList<>(this.employees); }
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
