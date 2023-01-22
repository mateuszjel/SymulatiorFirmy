package Game;

import People.Client;
import People.Person;
import People.Player;
import People.Worker;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Project<projectLevel> {
    public enum LevelType{
        EASY, MEDIUM, HARD;

        public static LevelType random()  {
            LevelType[] levelTypes = values();
            return levelTypes[ThreadLocalRandom.current().nextInt(levelTypes.length)];
        }
    }
    public enum Status{
        AVAILABLE, IN_PROGRESS, FINISHED, WAITING_FOR_PAYMENT
    }
    final private String[] projectNames = {
        "Gringo", "Lampart", "Saper", "Japan", "Mazowsze", "Korale", "Tekstowo", "Serpentyna", "NaNowo", "Krakus", "Polska", "Vertigo", "Mirage", "Dust",
        "Anubis", "Korona", "Waszyngton", "Drzewo", "Dom", "Strych", "Kibice", "NaSportowo", "Taantula", "Amfiteatr", "Bank", "ParkRozrywki", "Budapeszt"
    };
    private Map<Status, String> statusesText = new HashMap<Status, String>(){{
            put(Status.AVAILABLE, "dostępny");
            put(Status.IN_PROGRESS, "w trakcie realizacji");
            put(Status.FINISHED, "oddany kientowi");
            put(Status.WAITING_FOR_PAYMENT, "oczekuje na płatność");
    }};
    private String name;
    private Status status;
    private Client client;
    private Date projectDeadline;
    private Date paymentDeadline;
    private Integer penalty;
    private Integer price;
    private LevelType level;
    private Map<Game.Technology, Integer> estimateDays = new HashMap<>();
//    private Map<Game.Technology, Worker> assignedPeople;
    private Player assignedPlayer;

    public Project(Date currentDate){
        this(LevelType.random(), currentDate);
    }

    public Project(LevelType level, Date currentDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int technologiesCount = 0;
        int maxExtimateDays = 0;
        switch (level){
            case EASY -> {technologiesCount = 1;}
            case MEDIUM -> {technologiesCount = 2;}
            case HARD -> {technologiesCount = Random.randInt(3, Game.Technology.values().length);}
        }
        ArrayList<Game.Technology> technologies = Game.Technology.randomList(technologiesCount);
        for( Game.Technology technology: technologies ){
                Integer technologyEstimation = Random.randInt(7, 30);
                this.estimateDays.put(technology, technologyEstimation);
                if (technologyEstimation > maxExtimateDays){
                    maxExtimateDays = technologyEstimation;
                }
        }
        this.level = level;
        this.status = Status.AVAILABLE;
        this.name = this.projectNames[Random.randInt(0, this.projectNames.length-1)];
        this.client = new Client();
        this.projectDeadline = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24 * Random.randInt(maxExtimateDays, maxExtimateDays + 10)));
        this.paymentDeadline = new Date(this.projectDeadline.getTime() + (1000 * 60 * 60 * 24 * Random.randInt(1,10)));
        this.price = Random.randInt(maxExtimateDays * 700,maxExtimateDays * 1000);
        this.penalty = Random.randInt(7,15);
    }

//    public void assignPerson(Worker worker, Game.Game.Technology technology) throws Exception{
//        if (!worker.getTechnologies().contains(technology) || !this.estimateDays.containsKey(technology)){
//            throw new Exception("Brak dostępnej technologi");
//        }
//        this.assignedPeople.put(technology, worker);
//    }

//    public void setPlayer(Player player){
//        if (this.assignedPlayer == null){
//            this.assignedPlayer = player;
//            this.status = Status.IN_PROGRESS;
//        }
//    }
//    public void setStatus(Status status){
//        this.status = status;
//    }

    public void setPlayer(Player player){
        this.assignedPlayer = player;
        this.status = Status.IN_PROGRESS;
    }

    public void workOnProject(Game.Technology technology){
        if (this.estimateDays.containsKey(technology) && this.estimateDays.get(technology) > 0){
            this.estimateDays.put(technology, this.estimateDays.get(technology) - 1 );
        }
    }

    public Map<Game.Technology, Integer> getTechnologies(){return new HashMap<>(this.estimateDays); }
    public String getName(){
        return this.name;
    }
    public Date getProjectDeadline(){
        return this.projectDeadline;
    }
    public Date getPaymentDeadline(){
        return this.paymentDeadline;
    }
    public Integer getPrice(){ return this.price; }
    public Integer getPenalty(){ return this.penalty; }
    public String getClient(){
        return this.client.toString();
    }
    public Status getStatus(){
        return this.status;
    }
    public String getStatusText(){
        return this.statusesText.get(this.status);
    }
    public Player getPlayer(){
        return this.assignedPlayer;
    }
}
