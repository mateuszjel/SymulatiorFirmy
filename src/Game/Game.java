package Game;

import People.Employee;
import People.Person;
import People.Player;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
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
            ArrayList<Technology> technologies = new ArrayList<Technology>();
            Integer reducedProbability = 100;
            while (Random.randBool(probability)){
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

    private Date currentDate = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<Employee> employees = new ArrayList<>();

    public Game() {
//        for(int i = 0; i < userCount; i++){
//            players.add(new Player());
//        }
    }
    public void addPlayer(Player player){
        players.add(player);
        for( int i = 0; i < 3; i++){
            projects.add(new Project(Project.LevelType.EASY, currentDate));
            projects.add(new Project(Project.LevelType.MEDIUM, currentDate));
            projects.add(new Project(Project.LevelType.HARD, currentDate));
        }
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public ArrayList<Project> getProjects(){
        return this.projects;
    }

    public ArrayList<Employee> getPlayerEmployees(Player player){
        ArrayList<Employee> result = new ArrayList<Employee>();
        for(Employee employee: this.employees){
            if(employee.getPlayer().hashCode() == player.hashCode()){
                result.add(employee);
            }
        }
        return result;
    }

    public ArrayList<Project> getPlayerProjects(Player player){
        ArrayList<Project> result = new ArrayList<Project>();
        for(Project project: projects){
            if(project.getPlayer().hashCode() == player.hashCode()){
                result.add(project);
            }
        }
        return result;
    }

    public ArrayList<Project> getAvailableProjects(Player player){
        ArrayList<Project> result = new ArrayList<Project>();
        for(Project project: projects){
            if(project.getStatus() == Project.Status.AVAILABLE){
                result.add(project);
            }
        }
        return result;
    }

}
