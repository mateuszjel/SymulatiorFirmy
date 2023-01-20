import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Project<projectLevel> {
    public enum LevelType{
        EASY, MEDIUM, HARD
    }
    final private String[] projectNames = {
        "Gringo", "Lampart", "Saper", "Japan", "Mazowsze", "Korale", "Tekstowo", "Serpentyna", "NaNowo", "Krakus", "Polska", "Vertigo", "Mirage", "Dust",
        "Anubis", "Korona", "Waszyngton", "Drzewo", "Dom", "Strych", "Kibice", "NaSportowo", "Taantula", "Amfiteatr", "Bank", "ParkRozrywki", "Budapeszt"
    };
    private String name;
    private Client client;
    private Date projectDeadline;
    private Date paymentDeadline;
    private Integer penalty;
    private Integer price;
    private LevelType level;
    private Map<Game.Technology, Integer> estimateDays;
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
        for( int i = 0; i < technologiesCount; i++ ){
            Game.Technology technology = Game.Technology.random();
            if ( !this.estimateDays.containsKey(technology) ){
                Integer technologyEstimation = Random.randInt(7, 30);
                this.estimateDays.put(technology, technologyEstimation);
                if (technologyEstimation > maxExtimateDays){
                    maxExtimateDays = technologyEstimation;
                }
            }else{
                i -= 1;
            }
        }
        this.level = level;
        this.name = this.projectNames[Random.randInt(0, this.projectNames.length-1)];
        this.client = new Client();
        this.projectDeadline = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24 * Random.randInt(maxExtimateDays, maxExtimateDays + 10)));
        this.paymentDeadline = new Date(this.projectDeadline.getTime() + (1000 * 60 * 60 * 24 * Random.randInt(1,10)));
        this.price = Random.randInt(maxExtimateDays * 700,maxExtimateDays * 1000);
        this.penalty = this.price * ( Random.randInt(7,15) / 100 );
    }
}
