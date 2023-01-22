import Game.Game;
import Game.Project;
import People.Employee;
import People.Player;
import People.Subcontractor;
import People.Worker;

import java.util.*;


public class ScreenView {
    private Game game;
    public void start(){
        this.start("");
    }
    private void start(String message){
        this.clearConsole();
        this.topBottomBorder();
        this.header(null, message);
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
        this.header(null, message);
        System.out.println("Podaj liczbę graczy:\n");
        this.footer();
        this.topBottomBorder();
        Integer userCount = 0;
        try{
            this.game = new Game();
            userCount = Integer.parseInt(Objects.requireNonNull(this.readValue()));
        }catch (Exception e){
            this.insertUserCount("Podano niepoprawną wartość");
        }
        for (int i = 1; i <= userCount; i++){
            this.game.addPlayer(insertUser(i,""));
        }
        while(true){
            for (Player player: game.getPlayers()){
                if(this.game.getCurrentDate() != Game.START_DATE){
                    game.nextDay(player);
                }
                this.playerMenu(player, "");
            }
        }
    }

    private Player insertUser(Integer number,String message) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(null, message);
        System.out.println("Podaj nazwę gracza " + number + ":\n");
        this.footer();
        this.topBottomBorder();
        String userName = this.readValue();
        return new Player(userName);
    }

    private void playerMenu(Player player, String message) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(player, message);
        System.out.println("Wybierz czynność:\n\n");
        System.out.println("1. Podgląd listy projektów na rynku\n");
        System.out.println("2. Szukaj nowych projektów\n");
        System.out.println("3. Przypisz pracownika do projektu lub zacznij programować samemu\n");
        System.out.println("4. Rozpocznij testy kodu\n");
        System.out.println("5. Oddaj projekt klientowi\n");
        System.out.println("6. Zatrudnij pracownika\n");
        System.out.println("7. Rozlicz pracowników\n");
        this.footer();
        this.topBottomBorder();
        switch (this.readValue()){
            case "1" -> { this.availableProjects(player, ""); }
            case "2" -> { this.searchNewProject(player); }
            case "3" -> { this.manageHumanResources(player, ""); }
            case "4" -> { this.startTests(player, ""); }
            case "5" -> { this.giveFinishedProject(player, ""); }
            case "6" -> { this.searchNewEmployees(player); }
            case "7" -> { this.hireNewEmployee(player, ""); }
            case "8" -> { this.accounting(player); }
            default -> {
                this.playerMenu(player, "Wprowadzono niepoprawną wartość");
            }
        }
    }

    private void availableProjects(Player player, String message) {
        ArrayList<Project> projects = player.getProjects();
        projects.addAll(game.getAvailableProjects());
        this.clearConsole();
        this.topBottomBorder();
        this.header(player, message);
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
        System.out.println("\n" + (projects.size() + 1) + ". Powrót do menu czynności\n");
        System.out.println("Podaj numer projektu który chcesz podejżeć lub wykonać:");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number <= projects.size()){
                this.projectScreen(player, projects.get(number - 1), "");
            }else if (number == projects.size() + 1){
                this.playerMenu(player, "");
            }else{
                this.availableProjects(player,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.availableProjects(player,"Podano niepoprawną wartość");
        }
    }

    private void projectScreen(Player player, Project project, String message) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(player, message);
        System.out.println("Nazwa projektu: " + project.getName());
        System.out.println("Klient: " + project.getClient());
        System.out.println("\nEstymacja wymaganych technologii");
        for(Object technology : project.getTechnologies().keySet()){
            System.out.println(Game.technologiesText.get(technology) + ": " + project.getTechnologies().get(technology) + " dni");
        }
        System.out.println("\nTermin ukończenia: " + project.getProjectDeadline());
        System.out.println("Termin płatności: " + project.getPaymentDeadline());
        System.out.println("\nCena za wykonanie projektu: " + project.getPrice() + " zł");
        System.out.println("Każdy dzień opóżnienia zminiejsza wartość projektu o: " + project.getPenalty() + " procent\n");
        if (project.getStatus() == Project.Status.AVAILABLE) {
            System.out.println("1. Przyjmij zlecenie\n");
            System.out.println("2. Powrót\n");
        }else{
            System.out.println("1. Powrót\n");
        }
        this.footer();
        this.topBottomBorder();
        if (project.getStatus() == Project.Status.AVAILABLE) {
            switch (Objects.requireNonNull(this.readValue())){
                case "1" -> {
                    game.addPlayerProject(player, project);
                }
                case "2" -> {
                    this.availableProjects(player, "");
                }
                default -> {
                    this.projectScreen(player, project, "Wprowadzono niepoprawną wartość");
                }
            }
        }else{
            if ("1".equals(Objects.requireNonNull(this.readValue()))) {
                this.availableProjects(player, "");
            } else {
                this.projectScreen(player, project, "Wprowadzono niepoprawną wartość");
            }
        }
    }

    private void searchNewProject(Player player) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(null,"");
        player.bumpSearchNewProjectSince();
        System.out.println("Poszukujesz nowych zleceń (dzień " + player.getSearchNewProjectSince() + ")...");
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            return;
        }
    }
    private void searchNewEmployees(Player player) {
        if (player.getMoney() >=  Game.SEARCH_NEW_EMPLOYEE_COST){
            this.clearConsole();
            this.topBottomBorder();
            this.header(null,"");
            player.searchEmployees();
            System.out.println("Poszukujesz nowych zleceń (dzień " + player.getSearchNewProjectSince() + ")...");
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                return;
            }
        }else{
            playerMenu(player, "Brak wystarczającej ilości gotówki");
        }
    }
    private void workingOnProject(Project project, Game.Technology technology) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(null,"");
        project.workOnProject(technology);
        System.out.println("Pracujesz nad projektem (" + project.getName() + ")...");
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            return;
        }
    }
    private void testProject(Project project) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(null,"");
        project.testProject();
        System.out.println("Testujesz projekt (" + project.getName() + ")...");
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            return;
        }
    }
    private void accounting(Player player) {
        this.clearConsole();
        this.topBottomBorder();
        this.header(null,"");
        player.addAccountingDay();
        System.out.println("Dziś rozliczasz sie z urzędami (dzień: " + player.getAccountingDays() + ")...");
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            return;
        }
    }

    private void manageHumanResources(Player player, String message) {
        Integer counter = 1;
        ArrayList<Employee> employees = player.getEmployees();
        ArrayList<Subcontractor> subcontractors = player.getSubcontractors();
        this.clearConsole();
        this.topBottomBorder();
        this.header(player, message);
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
        System.out.println("\n" + counter + ". programuj samemu\n");
        System.out.println((counter + 1) + ". Powrót do menu czynności\n");
        System.out.println("Podaj numer osoby do której chcesz przypisać projekt:");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number <= employees.size()) {
                this.employeeScreen(player, employees.get(number - 1), "");
            }else  if (number > employees.size() && number <= employees.size() + subcontractors.size()){
                this.subcontractorScreen(player, subcontractors.get(number - employees.size()- 1),"");
            }else if (number == employees.size() + subcontractors.size() + 1){
                this.workSelfScreen(player, "");
            }else if (number == employees.size() + subcontractors.size() + 2){
                this.playerMenu(player, "");
            }else{
                this.manageHumanResources(player,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.manageHumanResources(player,"Podano niepoprawną wartość");
        }
    }

    private void workSelfScreen(Player player, String message) {
        ArrayList<ArrayList<Object>> projects = new ArrayList<>();
        this.clearConsole();
        this.topBottomBorder();
        this.header( player, message);
        StringBuilder technologies = new StringBuilder(new String(" "));
        System.out.println("Dostępne technologie: ");
        for(Game.Technology technology: player.getTechnologies()){
            technologies.append(Game.technologiesText.get(technology)).append(", ");
        }
        System.out.println(technologies.subSequence(0,technologies.length()-2));

        System.out.println("\nWybierz projekt nad którym chcesz pracować:\n");
        for( Project project : player.getProjects()){
            for(Object technology : project.getTechnologies().keySet()) {
                if ( player.getTechnologies().contains(technology) && (Integer)project.getTechnologies().get(technology) > 0) {
                    projects.add(new ArrayList<>(){{
                        add(project);
                        add(technology);
                    }});
                    System.out.println(projects.size() + ". " + project.getName() + ", technologia: " + Game.technologiesText.get(technology) + ", pozostało dnii: " + project.getTechnologies().get(technology));
                }
            }
        }
        if (projects.size() == 0 ){
            System.out.println("Brak projektów w dostępnych technologiach");
        }
        System.out.println("\n" + (projects.size() + 1) + ". Powrót do listy pracowników\n");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number <= projects.size()) {
                this.workingOnProject((Project) projects.get(number-1).get(0), (Game.Technology) projects.get(number-1).get(1));
//                Project project = (Project) projects.get(number-1).get(0);
//                project.workOnProject((Game.Technology) projects.get(number-1).get(1));
            } else if (number == projects.size() + 1){
                this.manageHumanResources(player, "");
            }else{
                this.workSelfScreen(player,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.workSelfScreen(player,"Podano niepoprawną wartość");
        }
    }

    private void subcontractorScreen(Player player, Subcontractor subcontractor, String message) {
        ArrayList<ArrayList<Object>> projects = new ArrayList<>();
        ArrayList<Employee> employees = player.getEmployees();
        ArrayList<Subcontractor> subcontractors = player.getSubcontractors();
        this.clearConsole();
        this.topBottomBorder();
        this.header(player, message);
        System.out.println("Podwykonawca:\n\n" + subcontractor.getName());
        StringBuilder technologies = new StringBuilder(new String("  "));
        System.out.println("Specjalista w technologiach: ");
        for(Game.Technology technology: subcontractor.getTechnologies()){
            technologies.append(Game.technologiesText.get(technology)).append(", ");
        }
        System.out.println(technologies.subSequence(0,technologies.length()-2));

        String projectName;
        try{
            projectName = subcontractor.getProject().getName() + " , w technologii: " + subcontractor.getWorkingTechnology() + " , do ukończenia pozostało " + subcontractor.getProject().getTechnologies().get(subcontractor.getWorkingTechnology());
        }catch (Exception e){
            projectName = "brak";
        }
        System.out.println("\nAktualnie zlecony projekt: " + projectName);
        System.out.println("Zmień lub przypisz nowy projekt:\n");
        for( Project project : player.getProjects()){
            for(Object technology : project.getTechnologies().keySet()) {
                if ( subcontractor.getTechnologies().contains(technology) && (Integer)project.getTechnologies().get(technology) > 0) {
                    projects.add(new ArrayList<>(){{
                        add(project);
                        add(technology);
                    }});
                    System.out.println(projects.size() + ". " + project.getName() + ", technologia: " + Game.technologiesText.get(technology) + ", pozostało dnii: " + project.getTechnologies().get(technology));
                }
            }
        }
        if (projects.size() == 0 ){
            System.out.println("Brak projektów w dostępnych technologiach");
        }
        System.out.println("\n" + (projects.size() + 1) + ". Powrót do listy pracowników\n");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number <= projects.size()) {
                projects.get(number-1);
                subcontractor.setWorkingProject((Project) projects.get(number-1).get(0),(Game.Technology) projects.get(number-1).get(1));
            } else if (number == projects.size()+1){
                this.manageHumanResources(player, "");
            }else{
                this.subcontractorScreen(player,subcontractor,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.subcontractorScreen(player,subcontractor,"Podano niepoprawną wartość");
        }
    }

    private void employeeScreen(Player player, Employee employee, String message) {
        ArrayList<ArrayList<Object>> projects = new ArrayList<>();
        this.clearConsole();
        this.topBottomBorder();
        this.header(player, message);
        System.out.println("Pracownik:\n\n" + employee.getName());
        System.out.println("Typ pracownika: " + employee.getWorkerTypeText());
        if (employee.getWorkerType() == Worker.WorkerType.PROGRAMMER){
            StringBuilder technologies = new StringBuilder(new String("  "));
            System.out.println("Specjalista w technologiach: ");
            for(Game.Technology technology: employee.getTechnologies()){
                technologies.append(Game.technologiesText.get(technology)).append(", ");
            }
            System.out.println(technologies.subSequence(0,technologies.length()-2));
        }
        String projectName;
        try{
            projectName = employee.getProject().getName() + " , w technologii: " + employee.getWorkingTechnology() + " , do ukończenia pozostało " + employee.getProject().getTechnologies().get(employee.getWorkingTechnology());
        }catch (Exception e){
            projectName = "brak";
        }
        System.out.println("\nAktualnie pracuje nad projektem: " + projectName);
        System.out.println("Zmień lub przypisz nowy projekt:\n");
        for( Project project : player.getProjects()){
            if (employee.getWorkerType() == Worker.WorkerType.TESTER && project.getStatus() == Project.Status.FINISHED){
                projects.add(new ArrayList<>(){{
                    add(project);
                    add(null);
                }});
                System.out.println(projects.size() + ". " + project.getName());
            }
            for(Object technology : project.getTechnologies().keySet()) {
                if ( employee.getWorkerType() == Worker.WorkerType.PROGRAMMER && employee.getTechnologies().contains(technology) && (Integer)project.getTechnologies().get(technology) > 0) {
                    projects.add(new ArrayList<>(){{
                        add(project);
                        add(technology);
                    }});
                    System.out.println(projects.size() + ". " + project.getName() + ", technologia: " + Game.technologiesText.get(technology) + ", pozostało dnii: " + project.getTechnologies().get(technology));
                }
            }
        }
        if (projects.size() == 0 ){
            System.out.println("Brak projektów w dostępnych technologiach");
        }
        System.out.println("\n" + (projects.size() + 1) + ". Powrót do listy pracowników\n");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number < projects.size()) {
                employee.setWorkingProject((Project) projects.get(number-1).get(0),(Game.Technology) projects.get(number-1).get(1));
            } else if (number == projects.size() + 1){
                this.manageHumanResources(player, "");
            }else{
                this.employeeScreen(player,employee,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.employeeScreen(player,employee,"Podano niepoprawną wartość");
        }
    }

    private void startTests(Player player, String message) {
        ArrayList<Project> projects = new ArrayList<>();
        this.clearConsole();
        this.topBottomBorder();
        this.header( player, message);

        System.out.println("\nWybierz projekt Który checesz przetestować:\n");
        for( Project project : player.getProjects()){
            if (project.getStatus() == Project.Status.FINISHED) {
                projects.add(project);
                System.out.println(projects.size() + ". " + project.getClient() + ", " + project.getName());
            }
        }
        if (projects.size() == 0 ){
            System.out.println("Nie ma jeszcze żadnego ukończonego projektu do przetestowania");
        }
        System.out.println("\n" + (projects.size() + 1) + ". Powrót do menu czynności\n");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number <= projects.size()) {
                this.testProject(projects.get(number -1));
            } else if (number == projects.size() + 1){
                this.playerMenu(player, "");
            }else{
                this.startTests(player,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.startTests(player,"Podano niepoprawną wartość");
        }
    }


    private void giveFinishedProject(Player player, String message) {
        ArrayList<Project> projects = new ArrayList<>();
        this.clearConsole();
        this.topBottomBorder();
        this.header( player, message);

        System.out.println("\nWybierz projekt Który checesz oddać:\n");
        for( Project project : player.getProjects()){
            if (project.getStatus() == Project.Status.FINISHED) {
                projects.add(project);
                System.out.println(projects.size() + ". " + project.getClient() + ", " + project.getName());
            }
        }
        if (projects.size() == 0 ){
            System.out.println("Nie ma jeszcze żadnego ukończonego projektu do oddania");
        }
        System.out.println("\n" + (projects.size() + 1) + ". Powrót do menu czynności\n");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number <= projects.size()) {
//                TODO: finish project
//                this.testProject(projects.get(number -1));
            } else if (number == projects.size() + 1){
                this.playerMenu(player, "");
            }else{
                this.giveFinishedProject(player,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.giveFinishedProject(player,"Podano niepoprawną wartość");
        }
    }

    private void hireNewEmployee(Player player, String message) {
        ArrayList<Employee> employees = player.getEmployees();
        employees.addAll(game.getAvailableEmployees());
        this.clearConsole();
        this.topBottomBorder();
        this.header(player, message);
        System.out.println("Posiadania pracownicy:\n\n");
        for( int i = 0; i < employees.size(); i++){
            Employee employee = employees.get(i);
            if (employee.getPlayer() != null) {
                System.out.println(i + 1 + ". " + employee);
            }
        }
        System.out.println("Dostępne pracownicy na rynku pracy:\n\n");
        for( int i = 0; i < employees.size(); i++){
            Employee employee = employees.get(i);
            if (employee.getPlayer() == null) {
                System.out.println(i + 1 + ". " + employee);
            }
        }
        System.out.println("\n" + (employees.size() + 1) + ". Powrót do menu czynności\n");
        System.out.println("Podaj numer pracwnika którego chcesz zwolnić lub zatrudnić:");
        this.footer();
        this.topBottomBorder();
        try{
            int number = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            if (number > 0 && number <= employees.size()){
                this.marketEmployeeScreen(player, employees.get(number - 1), "");
            }else if (number == employees.size() + 1){
                this.playerMenu(player, "");
            }else{
                this.hireNewEmployee(player,"Podano niepoprawną wartość");
            }
        }catch (Exception e){
            this.hireNewEmployee(player,"Podano niepoprawną wartość");
        }
    }

    private void marketEmployeeScreen(Player player, Employee employee, String message) {
        ArrayList<ArrayList<Object>> projects = new ArrayList<>();
        this.clearConsole();
        this.topBottomBorder();
        this.header(player, message);
        System.out.println("Pracownik:\n\n" + employee.getName());
        System.out.println("Typ pracownika: " + employee.getWorkerTypeText());
        if (employee.getWorkerType() == Worker.WorkerType.PROGRAMMER){
            StringBuilder technologies = new StringBuilder(new String("  "));
            System.out.println("Specjalista w technologiach: ");
            for(Game.Technology technology: employee.getTechnologies()){
                technologies.append(Game.technologiesText.get(technology)).append(", ");
            }
            System.out.println(technologies.subSequence(0,technologies.length()-2));
        }
        if (employee.getPlayer() == null) {
            System.out.println("Koszt zatrudnienia pracownika: " + Game.HIRE_EMPLOYEE_COST + "zł");
            System.out.println("\n1. Zatrudnij pracownika\n");
        }else{
            System.out.println("Koszt zwolnienia pracownika: " + Game.FIRE_EMPLOYEE_COST + "zł");
            System.out.println("\n1. Zwolnij pracownika\n");
        }
        System.out.println("\n2. Powrót do listy pracowników\n");
        this.footer();
        this.topBottomBorder();
        switch (this.readValue()){
            case "1" -> {
                if(employee.getPlayer() == null){
                    if(player.getMoney() >= Game.HIRE_EMPLOYEE_COST) {
                        this.game.hireEmployee(player, employee);
                    }else{
                        this.employeeScreen(player,employee,"Brak wystarczającej ilości gotówki");
                    }
                }else{
                    if (player.getMoney() >= Game.FIRE_EMPLOYEE_COST) {
                        this.game.fireEmployee(employee);
                    }else{
                        this.employeeScreen(player,employee,"Brak wystarczającej ilości gotówki");
                    }
                }
            }
            case "2" -> {this.hireNewEmployee(player, "");}
            default -> {
                this.employeeScreen(player,employee,"Podano niepoprawną wartość");
            }
        }
    }

    private void header(Player player, String message){
        System.out.println("        StartupHorror - symulator firmy\n");
        if (player != null){
            System.out.println("Wybiera gracz: " + player + ", dzień: " + this.game.getCurrentDate());
        } else if ( !message.equals("") ){
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
