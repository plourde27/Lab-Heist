import java.util.*;
import javax.swing.*;

public class main 
{
  public static void main(String[] args) 
    {
        
        int roomX = 30;
        int roomY = 30;
        boolean makeRoom = false;
        
        String st = "";
        
        if (makeRoom) {
            st += "\"";
            for (int i = 0 ; i < roomX ; i++) {
                st += "x";
            }
            st += "\",";
            st += "\n";
            for (int i = 0 ; i < roomY ; i++) {
                st += "\"x";
                for (int j = 1 ; j < roomX - 1 ; j++) {
                    st += ".";
                }
                st += "x\",\n";
            }
            
            for (int i = 0 ; i < roomX ; i++) {
                st += "x";
            }
            st += "\"";
            st += "\n";
            System.out.println(st);
            return;
        }
        
        JFrame frame = new JFrame("Lab Heist");
        Game game = new Game();
        
        
        Mouse mouse = new Mouse();
        frame.addMouseListener(mouse);
        Keyboard keyboard = new Keyboard();
        frame.addKeyListener(keyboard);
        MoveMouse mm = new MoveMouse();
        frame.addMouseMotionListener(mm);
        Display screen = new Display(game, mouse, keyboard, mm, frame);
        frame.add(screen);
        
        frame.setBounds(0,0,1080,720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        new Thread(game).start();
        new Thread(new frameRateUpdater(30,screen)).start();
  }
}