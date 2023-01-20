import sun.misc.Signal;
import sun.misc.SignalHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main implements KeyEventDispatcher {

    public static void main(String[] args) {
//        System.out.println("TTTTTTTT");
	    new ScreenView().start();


    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println("event.getKeyCode()");
//        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//            if (event.getAction() == KeyEvent.ACTION_UP){
//
//                enter();
//
//                return true;
//            }}
        return false;
    }
}
class MKeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent event) {

        char ch = event.getKeyChar();

        if (ch == 'a' ||ch == 'b'||ch == 'c' ) {

            System.out.println(event.getKeyChar());

        }

        if (event.getKeyCode() == KeyEvent.VK_HOME) {

            System.out.println("Key codes: " + event.getKeyCode());

        }
    }
}