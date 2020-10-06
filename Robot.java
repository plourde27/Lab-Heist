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
    double x;
    int y;
    int mode;
    double speed = 1.5;
    double vely = 0;
    boolean dead = false;
    int fade = 255;
    
    
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
        mode = -1;
    }
    
    public void draw(Graphics g, Player p, Display d, int tx, int ty) {
        fill(200, 0, 0, fade, g);
        rect((int)x, y - 13, 17, 17, g, tx, ty);
        rect((int)x, y, 10, 10, g, tx, ty);
        rect((int)x, y + 25, 23, 40, g, tx, ty);
        rect((int)x, y + 60, 8, 30, g, tx, ty); //x - 12, x + 12, y - 22, y + 75
    }
    
    public void update(Player p, Display d) {
        if (dead) {
            fade -= 3;
            if (fade < 0) {
                fade = 0;
            }
        }
        else if (type == 0) {
            double px = x;
            int py = y;
            x += speed * mode;
            y += vely;
            vely += 0.35;
            int ct = 0;
            int val = -1;
            boolean found = false;
            for (int i = 0 ; i < d.blocks.size() ; i++) {
                if (x + 12 >= d.blocks.get(i).x && x - 12 <= d.blocks.get(i).x + d.blocks.get(i).w && y + 75 >= d.blocks.get(i).y && y - 22 <= d.blocks.get(i).y + d.blocks.get(i).h) {
                    
                    if (!(py + 75 > d.blocks.get(i).y && py - 22 < d.blocks.get(i).y + d.blocks.get(i).h)) {
                        y = d.blocks.get(i).y - 75;
                        vely = 0;
                    }
                    else {
                        x = px;
                        found = true;
                    }
                }
                
            }
            
            if (found) {
                mode = -mode;
            }
            
            if (p.x + 15 >= this.x - 12 && p.x - 15 <= this.x + 12 && p.y + 15 >= this.y - 22 && p.y - 15 <= this.y + 75) {
                System.out.println("ALARM");
            }
            
            if (p.effect == 1 && Math.sqrt(Math.pow(p.x+p.w/2-x,2)+Math.pow(p.y+p.h/2-y,2)) < 75) {
                die();
            }
        }
    }
    
    public void die() {
        dead = true;
    }
    
    public void display(Graphics g, Player p, Display d, int tx, int ty) {
        draw(g, p, d, tx, ty);
        update(p, d);
    }
}