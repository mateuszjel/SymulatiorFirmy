package ConsoleGame;

import People.Employee;
import People.Player;
import People.Subcontractor;
import People.Worker;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public enum GAME_STATUS{
        CONTINUE, ZUS_INSPECTION, MONEY_LEFT, FINISHED
    }
    public static Integer PLAYER_START_MONEY=10000;
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

    private Date currentDate = new Date(Game.START_DATE.getTime());
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();

    public Game() {
    }
    public void gameNextDay(){
        currentDate = new Date(this.currentDate.getTime() + (1000 * 60 * 60 * 24));
    }

    public GAME_STATUS playerNextDay(Player player){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this.currentDate);
        if(player.getVictoryProjects() >=3 && player.getMoney() > Game.PLAYER_START_MONEY){
            this.players.remove(player);
            return GAME_STATUS.FINISHED;
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) == 1){
            if(!player.checkAccounting()){
                this.players.remove(player);
                return GAME_STATUS.ZUS_INSPECTION;
            }
            for(Employee employee: player.getEmployees()){
                player.removeMoney(employee.getDaysWorked() * employee.getDailySalary());
                employee.resetDaysWorked();
            }
        }
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
        if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            for (Employee employee : player.getEmployees()) {
                if(employee.getWorkerType() == Worker.WorkerType.SELLER){
                    employee.searchNewProjects();
                }if (employee.getWorkingProject() != null) {
                    switch (employee.getWorkerType()) {
                        case PROGRAMMER -> employee.work();
                        case TESTER -> employee.test();
                    }
                } else if (employee.getWorkerType() == Worker.WorkerType.SELLER) {
                    player.bumpSearchNewProjectSince();
                }
            }
            for (Subcontractor subcontractor : player.getSubcontractors()) {
                if (subcontractor.getWorkingProject() != null) {
                    subcontractor.work();
                    player.removeMoney(subcontractor.getDailySalary());
                }
            }
        }
        if (player.getMoney() <=0){
            this.players.remove(player);
            return GAME_STATUS.MONEY_LEFT;
        }
        return GAME_STATUS.CONTINUE;
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

    public ArrayList<Project> getAvailableProjects(){ return new ArrayList<>(this.projects); }
    public ArrayList<Employee> getAvailableEmployees(){ return new ArrayList<>(this.employees); }


}
