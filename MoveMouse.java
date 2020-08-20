import java.awt.event.*;
public class MoveMouse implements MouseMotionListener {
    int x, y;
    public MoveMouse() {
        x = 0;
        y = 0;
    }
    
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
    
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}