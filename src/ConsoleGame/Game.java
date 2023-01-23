package ConsoleGame;

import People.Employee;
import People.Player;
import People.Subcontractor;
import People.Worker;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public static Integer SEARCH_NEW_EMPLOYEE_COST = 1500;
    public static Integer HIRE_EMPLOYEE_COST = 2000;
    public static Integer FIRE_EMPLOYEE_COST = 1000;
    public static Date START_DATE = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
    public enum Technology{
        FRONTEND, BACKEND, DATABASE, MOBILE, WORDPRESS, PRESTASHOP;
        
        public static Technology random()  {
            Technology[] technologies = values();
            return technologies[ThreadLocalRandom.current().nextInt(technologies.length)];
        }

        public static ArrayList<Technology> randomList(Integer count){
            ArrayList<Technology> technologies = new ArrayList<Technology>();
            for (int i = 0; i < count; i++) {
                Game.Technology technology = Game.Technology.random();
                if (!technologies.contains(technology)) {
                    technologies.add(technology);
                }else{
                    i--;
                }
            }
            return technologies;
        }

        public static ArrayList<Technology> randomListProbability(Integer probability){
            ArrayList<Technology> technologies = new ArrayList<>();
            Integer reducedProbability = 100;
            while (Random.randBool(reducedProbability)){
                Game.Technology technology = Game.Technology.random();
                if (!technologies.contains(technology)) {
                    technologies.add(technology);
                    reducedProbability -= probability;
                }
            }
            return technologies;
        }
    }

    public static Map<Technology,String> technologiesText = new HashMap<>(){{
        put(Technology.FRONTEND, "frontend");
        put(Technology.BACKEND, "backend");
        put(Technology.MOBILE, "aplikacje mobilne");
        put(Technology.DATABASE, "bazy danych");
        put(Technology.WORDPRESS, "wordpress");
        put(Technology.PRESTASHOP, "prestashop");
    }};

    private Date currentDate = new Date(String.valueOf(Game.START_DATE));
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();

    public Game() {
//        for(int i = 0; i < userCount; i++){
//            players.add(new Player());
//        }
    }

    public void nextDay(Player player){
        //TODO dodać sprawdzenie księgowości oraz dni wolne
        //TODO dodać choroby i game over
        currentDate = new Date(this.currentDate.getTime() + (1000 * 60 * 60 * 24));
        if (player.findEmployee()) {
            employees.add(new Employee());
        }
        if (player.findNewProject()){
            projects.add(new Project(currentDate));
        }
        for(Project project: player.getProjects()){
            if(project.getStatus() == Project.Status.WAITING_FOR_PAYMENT){
                project.payment(this.currentDate);
                if(project.getStatus() == Project.Status.PAID){
                    player.addMoney(project.getPrice());
                }
            }
        }
        for(Employee employee: player.getEmployees()){
            if(employee.getWorkingProject() != null){
                switch (employee.getWorkerType()){
                    case PROGRAMMER -> employee.work();
                    case TESTER -> employee.getWorkingProject().testProject();
                }
            }else if(employee.getWorkerType() == Worker.WorkerType.SELLER){
                player.bumpSearchNewProjectSince();
            }
        }
        for(Subcontractor subcontractor: player.getSubcontractors()){
            if(subcontractor.getWorkingProject() != null){
                subcontractor.work();
                player.removeMoney(subcontractor.getDailySalary());
            }
        }
    }

    public void addPlayer(Player player){
        players.add(player);
        projects.add(new Project(Project.LevelType.EASY, currentDate));
        projects.add(new Project(Project.LevelType.MEDIUM, currentDate));
        projects.add(new Project(Project.LevelType.HARD, currentDate));
        employees.add(new Employee());
    }

    public void addPlayerProject(Player player, Project project){
        if (this.projects.contains(project) &&
                (project.getLevel() != Project.LevelType.HARD || player.getEmployees().size() > 0)
        ) {
            this.projects.remove(project);
            player.addProject(project);
        }
    }

    public void hireEmployee(Player player, Employee employee){
        if (this.employees.contains(employee) && player.getMoney() >= Game.HIRE_EMPLOYEE_COST) {
            this.employees.remove(employee);
            player.addEmployee(employee);
            player.removeMoney(Game.HIRE_EMPLOYEE_COST);
//            project.setPlayer(player);
        }
    }
    public void fireEmployee(Employee employee){
        if (employee.getPlayer() != null && employee.getPlayer().getMoney() >= Game.FIRE_EMPLOYEE_COST) {
            this.employees.add(employee);
            employee.getPlayer().fireEmployee(employee);
            employee.getPlayer().removeMoney(Game.FIRE_EMPLOYEE_COST);
        }
    }

    public ArrayList<Player> getPlayers(){ return new ArrayList<>(this.players); }
    public Date getCurrentDate(){ return this.currentDate; }
//    public ArrayList<Project> getProjects(){
//        return this.projects;
//    }

//    public ArrayList<Employee> getPlayerEmployees(Player player){
//        ArrayList<Employee> result = new ArrayList<Employee>();
//        for(Employee employee: this.employees){
//            if(employee.getPlayer().hashCode() == player.hashCode()){
//                result.add(employee);
//            }
//        }
//        return result;
//    }

//    public ArrayList<Project> getPlayerProjects(Player player){
//        ArrayList<Project> result = new ArrayList<Project>();
//        for(Project project: projects){
//            if(project.getPlayer().hashCode() == player.hashCode()){
//                result.add(project);
//            }
//        }
//        return result;
//    }

    public ArrayList<Project> getAvailableProjects(){ return new ArrayList<>(this.projects); }
    public ArrayList<Employee> getAvailableEmployees(){ return new ArrayList<>(this.employees); }


}
