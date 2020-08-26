import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class Robot extends drawInterface {
    
    int type = 0;
    int x;
    int y;
    
    
    public Robot(int t, int xx, int yy) {
        //0 = patrol robot
        //1 = watchman robot
        //2 = fast patrol robot
        //3 = spider robot
        //4 = drone robot
        //5 = bug spy robot
        
        type = t;
        x = xx;
        y = yy;
    }
    
    public void draw(Graphics g, Player p, Display d, int tx, int ty) {
        fill(0, 0,  200, g);
        rect(x, y - 13, 17, 17, g, tx, ty);
        rect(x, y, 10, 10, g, tx, ty);
        rect(x, y + 25, 23, 40, g, tx, ty);
        rect(x, y + 60, 8, 30, g, tx, ty);
    }
    
    public void update(Player p, Display d) {
        
    }
    
    public void display(Graphics g, Player p, Display d, int tx, int ty) {
        draw(g, p, d, tx, ty);
        update(p, d);
    }
}