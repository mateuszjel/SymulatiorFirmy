package ConsoleGame;

import ConsoleGame.Game;
import People.Client;
import People.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Project {
    public enum LevelType{
        EASY, MEDIUM, HARD;

        public static LevelType random()  {
            LevelType[] levelTypes = values();
            return levelTypes[ThreadLocalRandom.current().nextInt(levelTypes.length)];
        }
    }
    public enum Status{
        AVAILABLE, IN_PROGRESS, FINISHED, WAITING_FOR_PAYMENT, PAID, CANCELED
    }
    final private String[] projectNames = {
        "Gringo", "Lampart", "Saper", "Japan", "Mazowsze", "Korale", "Tekstowo", "Serpentyna", "NaNowo", "Krakus", "Polska", "Vertigo", "Mirage", "Dust",
        "Anubis", "Korona", "Waszyngton", "Drzewo", "Dom", "Strych", "Kibice", "NaSportowo", "Taantula", "Amfiteatr", "Bank", "ParkRozrywki", "Budapeszt"
    };
    private Map<Status, String> statusesText = new HashMap<Status, String>(){{
            put(Status.AVAILABLE, "dostępny");
            put(Status.IN_PROGRESS, "w trakcie realizacji");
            put(Status.FINISHED, "ukończony");
            put(Status.WAITING_FOR_PAYMENT, "oczekuje na płatność");
            put(Status.PAID, "faktura opłacona");
            put(Status.CANCELED, "anulowany");
    }};
    private Map<LevelType, String> levelsText = new HashMap<>(){{
        put(LevelType.EASY, "prosty");
        put(LevelType.MEDIUM, "średni");
        put(LevelType.HARD, "złożony");
    }};
    private Boolean countedToVictory = false;
    private String name;
    private Status status;
    private Client client;
    private Date projectDeadline;
    private Date paymentDeadline;
    private Integer penalty;
    private Integer price;
    private LevelType level;
    private Integer testDays;
    private Map<Game.Technology, Integer> estimateDays = new HashMap<>();
    private Player assignedPlayer;
    private Integer maxEstimateDays=0;

    private Integer daysAfterDeadline = 0;

    public Project(Date currentDate){
        this(LevelType.random(), currentDate);
    }

    public Project(LevelType level, Date currentDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int technologiesCount = 0;
//        int maxExtimateDays = 0;
        switch (level){
            case EASY -> {technologiesCount = 1;}
            case MEDIUM -> {technologiesCount = 2;}
            case HARD -> {
                technologiesCount = Random.randInt(3, Game.Technology.values().length);
                this.countedToVictory = true;
            }
        }
//        ArrayList<Game.Technology> technologies = Game.Technology.randomList(technologiesCount);
        for( Game.Technology technology: Game.Technology.randomList(technologiesCount) ){
                Integer technologyEstimation = Random.randInt(7, 30);
                this.estimateDays.put(technology, technologyEstimation);
                if (technologyEstimation > this.maxEstimateDays){
                    maxEstimateDays = technologyEstimation;
                }
        }

        this.level = level;
        this.status = Status.AVAILABLE;
        this.name = this.projectNames[Random.randInt(0, this.projectNames.length-1)];
        this.client = new Client();
        this.projectDeadline = new Date(currentDate.getTime() + (1000L * (long)(60 * 60 * 24 * Random.randInt(this.maxEstimateDays, this.maxEstimateDays + 10))));
        this.paymentDeadline = new Date(this.projectDeadline.getTime() + (1000L * (long)(60 * 60 * 24 * Random.randInt(1,10))));
        this.price = Random.randInt(this.maxEstimateDays * 700,this.maxEstimateDays * 1000);
        this.penalty = Random.randInt(2,5);
        this.testDays = 0;
    }

    private Boolean workingCorrectly(){
        return Random.randBool(this.getTestDays() * 20);
    }

    public void setPlayer(Player player){
        this.assignedPlayer = player;
        this.status = Status.IN_PROGRESS;
    }

    public Boolean workOnProject(Game.Technology technology){
        if (this.estimateDays.containsKey(technology) && this.estimateDays.get(technology) > 0){
            this.estimateDays.put(technology, this.estimateDays.get(technology) - 1 );
            Boolean finished = true;
            for(Game.Technology key: this.estimateDays.keySet()){
                if (this.estimateDays.get(key) > 0){
                    finished = false;
                }
            }
            if ( finished){
                this.status = Status.FINISHED;
            }
            return true;
        }
        return false;
    }

    public void testProject(){
        if (this.status == Status.FINISHED){
            this.testDays += 1;
        }
    }

    public void finishProject(Date currentDate){
        if(this.workingCorrectly()){
            if(currentDate.compareTo(this.projectDeadline) < 0 ){
                Long daysLate = TimeUnit.DAYS.convert(currentDate.getTime() - this.projectDeadline.getTime(), TimeUnit.MILLISECONDS);
                if(!this.client.projectLate(daysLate.intValue())){
                    this.price -= this.price * this.penalty / 100;
                }
                this.daysAfterDeadline = daysLate.intValue();
            }
            this.status = Status.WAITING_FOR_PAYMENT;
            this.paymentDeadline = new Date(currentDate.getTime() + (this.paymentDeadline.getTime() - this.projectDeadline.getTime()));
        }else if(!this.client.projectNotWorking()){
            this.status = Status.CANCELED;
        }
    }
    public void payment(Date currentDate){
        if(this.status == Status.WAITING_FOR_PAYMENT && currentDate.compareTo(this.paymentDeadline) <= 0 ){
            Integer paymentLateDays = this.client.payment();
            this.paymentDeadline = new Date(this.projectDeadline.getTime() + (1000L * (long)(60 * 60 * 24 * paymentLateDays)));
            if(paymentLateDays == 0){
                this.status = Status.PAID;
                if(this.countedToVictory){
                    this.assignedPlayer.addVictoryProjects();
                }
            }
        }
    }

    public Integer getTestDays() {
        if(this.testDays < 0 ){
            return 0;
        }
        return testDays;
    }
    public void requireFix(Integer days){
        this.testDays -= days;
    }
    public void removeFromVictory(){
        this.countedToVictory = false;
    }

    public Map<Game.Technology, Integer> getTechnologies(){return new HashMap<>(this.estimateDays); }
    public String getName(){ return this.name; }
    public Integer getDaysAfterDeadline(){ return this.daysAfterDeadline; }
    public LevelType getLevel(){ return this.level; }
    public Date getProjectDeadline(){ return this.projectDeadline; }
    public Date getPaymentDeadline(){ return this.paymentDeadline; }
    public Integer getPrice(){ return this.price; }
    public Integer getPenalty(){ return this.penalty; }
    public String getClient(){ return this.client.toString(); }
    public Status getStatus(){ return this.status; }
    public String getStatusText(){ return this.statusesText.get(this.status); }
    public String getLevelText(){ return this.levelsText.get(this.level); }
    public Player getPlayer(){ return this.assignedPlayer; }
}
