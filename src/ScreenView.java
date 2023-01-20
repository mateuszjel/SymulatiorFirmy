import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.JTextField;
import javax.swing.*;


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
            Integer userCount = Integer.parseInt(Objects.requireNonNull(this.readValue()));
            this.game = new Game(userCount);
        }catch (Exception e){
            this.insertUserCount("Podano niepoprawną wartość");
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
