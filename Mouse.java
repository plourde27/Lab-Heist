import java.awt.event.*;
import javax.swing.*;

public class Mouse extends JPanel implements MouseListener{
    boolean clicked;
    boolean pressed;
    int x, y;
    public Mouse(){
        clicked = false;
        pressed = false;
    }
    
    public void mousePressed(MouseEvent e){
        x = e.getX();
        y = e.getY();
        
        clicked = true;
        pressed = true;
    }
    public void mouseReleased(MouseEvent e){
        pressed = false;
        //clicked = false;
        //pressed = false;
    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mouseClicked(MouseEvent e) {
        //pressed = false;
        clicked = false;
    }
    
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}