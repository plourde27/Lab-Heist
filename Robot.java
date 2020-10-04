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
        fill(200, 0, 0, g);
        rect((int)x, y - 13, 17, 17, g, tx, ty);
        rect((int)x, y, 10, 10, g, tx, ty);
        rect((int)x, y + 25, 23, 40, g, tx, ty);
        rect((int)x, y + 60, 8, 30, g, tx, ty); //x - 12, x + 12, y - 22, y + 75
    }
    
    public void update(Player p, Display d) {
        if (type == 0) {
            double px = x;
            int py = y;
            x += speed * mode;
            int ct = 0;
            int val = -1;
            //System.out.println(mode + " " + speed);
            for (int i = 0 ; i < d.blocks.size() ; i++) {
                //System.out.println((x - 12 >= d.blocks.get(i).x) + " " + (x + 12 <= d.blocks.get(i).x + d.blocks.get(i).w) + " " + (y - 22 >= d.blocks.get(i).y) + " " + (y + 75 <= d.blocks.get(i).y + d.blocks.get(i).h));
    
                if (x + 12 >= d.blocks.get(i).x && x - 12 <= d.blocks.get(i).x + d.blocks.get(i).w && y + 75 >= d.blocks.get(i).y && y - 22 <= d.blocks.get(i).y + d.blocks.get(i).h) {
                    
                    val = d.blocks.get(i).y - 75;
                    System.out.println(this.x + " " + d.blocks.get(i).x);
    
                    ct++;
                }
                
            }
            if (ct > 1) {
                mode = -mode;
                vely = 0;
            }
            else if (ct == 0) {
                x = px;
                vely += 0.35;
                y += vely;
                for (int i = 0 ; i < d.blocks.size() ; i++) {
                    //System.out.println((x - 12 >= d.blocks.get(i).x) + " " + (x + 12 <= d.blocks.get(i).x + d.blocks.get(i).w) + " " + (y - 22 >= d.blocks.get(i).y) + " " + (y + 75 <= d.blocks.get(i).y + d.blocks.get(i).h));
        
                    if (x + 12 >= d.blocks.get(i).x && x - 12 <= d.blocks.get(i).x + d.blocks.get(i).w && y + 75 >= d.blocks.get(i).y && y - 22 <= d.blocks.get(i).y + d.blocks.get(i).h) {
                        
                        val = d.blocks.get(i).y - 75;
                        
                        ct++;
                    }
                    
                }
                
                if (ct == 1) {
                    y = val;
                }
            }
            else {
                vely = 0;
                y = val;
            }
            
            if (p.x + 15 >= this.x - 12 && p.x - 15 <= this.x + 12 && p.y + 15 >= this.y - 22 && p.y - 15 <= this.y + 75) {
                System.out.println("ALARM");
            }
            //System.out.println(ct);
        }
    }
    
    public void display(Graphics g, Player p, Display d, int tx, int ty) {
        draw(g, p, d, tx, ty);
        update(p, d);
    }
}