import java.awt.event.*;
public class Keyboard extends KeyAdapter{
    boolean[] keys;
    boolean[] pressed;
    boolean lastPressed = false;
    public Keyboard() {
        keys = new boolean[200];
        pressed = new boolean[200];
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        keys[key] = true;
        pressed[key] = true;
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        keys[key] = false;
        pressed[key] = false;
        lastPressed = false;
    }
}