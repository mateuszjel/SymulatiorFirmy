import Game.Game;
import Game.Project;
import People.Employee;
import People.Player;
import People.Subcontractor;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public class ScreenView {
    private Game game;
    public void start(){
        this.start("");
    }
    private void start(String message){
        this.clearConsole();
        this.topBottomBorder();
        this.header(message);
        System.out.println("1. Rozpocznij nową grę\n");
        this.footer();
        this.topBottomBorder();
        if (Objects.equals(this.readValue(), "1")){
            this.insertUserCount("");
        }else{
            this.start("Wprowadzono niepoprawną wartość");
        };
    }

    private void insertUserCount(String message) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(message);
        System.out.println("Podaj liczbę graczy:\n");
        this.footer();
        this.topBottomBorder();
        try{
            this.game = new Game();
            int userCount = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            for (int i = 0; i < userCount; i++){
                this.game.addPlayer(insertUser(i,""));
            }
            while(true){
                for (Player player: game.getPlayers()){
                    this.playerMenu(player, "");
                }
            }
        }catch (Exception e){
            this.insertUserCount("Podano niepoprawną wartość");
        }
    }

    private Player insertUser(Integer number,String message) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(message);
        System.out.println("Podaj nazwę gracza " + number + ":\n");
        this.footer();
        this.topBottomBorder();
        String userName = this.readValue();
        return new Player(userName);
    }

    private void playerMenu(Player player, String message) {
        this.clearConsole();
        this.topBottomBorder();
        this.header("Wybiera gracz: " + player);
        System.out.println("Wybierz czynność:\n\n");
        System.out.println("1. Podgląd listy projektów na rynku\n");
        System.out.println("2. Szukaj nowych projektów\n");
        System.out.println("3. Przypisz pracownika do projektu lub zacznij programować samemu\n");
        System.out.println("4. Rozpocznij testy kodu\n");
        System.out.println("5. Oddaj projekt klientowi\n");
        System.out.println("5. Zatrudnij pracownika\n");
        System.out.println("5. Rozlicz pracowników\n");
        this.footer();
        this.topBottomBorder();
        switch (this.readValue()){
            case "1" -> { availableProjects(player, ""); }
            case "2" -> { searchNewProject(player); }
            case "3" -> { manageHumanResources(player, ""); }
            default -> {
                this.start("Wprowadzono niepoprawną wartość");
            }
        }
    }

    private void availableProjects(Player player, String message) {
        ArrayList<Project> projects = game.getPlayerProjects(player);
        projects.addAll(game.getAvailableProjects(player));
        this.clearConsole();
        this.topBottomBorder();
        this.header("Wybiera gracz: " + player);
        System.out.println("Podpisane projekty:\n\n");
        for( int i = 0; i < projects.size(); i++){
            Project project = projects.get(i);
            if (project.getPlayer() != null) {
                System.out.println(i + 1 + ". " + project.getClient() + ", " + project.getName() + " (" + project.getStatusText() + ")");
            }
        }
        System.out.println("Dostępne wolne projekty:\n\n");
        for( int i = 0; i < projects.size(); i++){
            Project project = projects.get(i);
            if (project.getStatus() == Project.Status.AVAILABLE) {
                System.out.println(i + 1 + ". " + project.getClient() + ", " + project.getName() + " (" + project.getStatusText() + ")");
            }
        }
        System.out.println(projects.size() + ". Powrót do menu czynności\n");
        System.out.println("Podaj numer projektu który chcesz podejżeć lub wykonać:");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number < projects.size()){
                this.projectScreen(player, projects.get(number));
            }else if (number == projects.size()){
                this.playerMenu(player, "");
            }else{
                this.availableProjects(player,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.availableProjects(player,"Podano niepoprawną wartość");
        }
    }

    private void projectScreen(Player player, Project project) {
        this.clearConsole();
        this.topBottomBorder();
        this.header("Wybiera gracz: " + player);
        System.out.println("Nazwa projektu: " + project.getName());
        System.out.println("Klient: " + project.getClient());
        System.out.println("Estymacja wymaganych technologii\n");
        for(Object technology : project.getTechnologies().keySet()){
            System.out.println(Game.technologiesText.get(technology) + ": " + project.getTechnologies().get(technology) + " dni");
        }
        System.out.println("Termin ukończenia: " + project.getProjectDeadline());
        System.out.println("Termin płatności: " + project.getPaymentDeadline());
        System.out.println("Cena za wykonanie projektu: " + project.getPrice());
        System.out.println("Każdy dzień opóżnienia zminiejsza wartość projektu o: " + project.getPenalty() + " procent");
        System.out.println("1. Przyjmij zlecenie\n");
        System.out.println("2. Powrót\n");
        this.footer();
        this.topBottomBorder();
        switch (this.readValue()){
            case "1" -> {
                project.setPlayer(player);
                return;
            }
            case "2" -> {
                availableProjects(player, "");
            }
            default -> {
                this.start("Wprowadzono niepoprawną wartość");
            }
        }
    }

    private void searchNewProject(Player player) {
        this.clearConsole();
        this.topBottomBorder();
        this.header("");
        player.bumpSearchNewProjectSince();
        System.out.println("Poszukujesz nowych zleceń (dzień " + player.getSearchNewProjectSince() + ")...");
        try{
            Thread.sleep(3000);
        }catch (Exception e){
            this.searchNewProject(player);
        }
    }

    private void manageHumanResources(Player player, String message) {
        Integer counter = 1;
        ArrayList<Employee> employees = game.getPlayerEmployees(player);
        ArrayList<Subcontractor> subcontractors = player.getSubcontractors();
        this.clearConsole();
        this.topBottomBorder();
        this.header("Wybiera gracz: " + player);
        System.out.println("Posiadani pracownicy:\n\n");
        for( int i = 0; i < employees.size(); i++){
            Employee employee = employees.get(i);
            System.out.println(counter + ". " + employee);
            counter += 1;
        }
        System.out.println("Dostępni podwykonawcy:\n\n");
        for( int i = 0; i < subcontractors.size(); i++){
            Subcontractor subcontractor = subcontractors.get(i);
            System.out.println(counter + ". " + subcontractor);
            counter += 1;

        }
        System.out.println(counter + ". Powrót do menu czynności\n");
        System.out.println("Podaj numer osoby do której chcesz przypisać projekt:");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number <= employees.size()) {
                this.employeeScreen(player, employees.get(number - 1));
            } if (number > employees.size() && number <= employees.size() + subcontractors.size()){
                this.subcontractorScreen(player, subcontractors.get(number - employees.size()- 1));
            }else if (number == employees.size() + subcontractors.size()){
                this.playerMenu(player, "");
            }else{
                this.availableProjects(player,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.availableProjects(player,"Podano niepoprawną wartość");
        }
    }

    private void header(String message){
        System.out.println("        StartupHorror - symulator firmy\n");
        if ( !message.equals("") ){
            System.out.println("Uwaga: " + message + "\n\n");
        }else{
            System.out.println();
        }
    }
    private void footer(){
        System.out.println("\n(Podaj numer akcji i wciśnij enter.)");
    }
    private void topBottomBorder() {
        StringBuilder border = new StringBuilder();
        border.append("-".repeat(1000));
        System.out.println(border);
    }
    private void clearConsole() {
        for(int i=0; i < 100 ;i++){
            System.out.println();
        }
    }
    private String readValue(){
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
