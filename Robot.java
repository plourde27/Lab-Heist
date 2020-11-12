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
    int laserFrame = 0;
    int lx = 0;
    int rx = 0;
    int dir = 0;
    
    
    public Robot(int t, int xx, int yy) {
        //0 = patrol robot
        //1 = laser robot
        //2 = spider robot
        //3 = guard robot
        //4 = drone robot
        //5 = bug spy robot
        
        type = t;
        x = xx;
        y = yy;
        mode = -1;
    }
    
    public Robot(int t, int xx, int yy, int d) {
        //0 = patrol robot
        //1 = laser robot
        //2 = spider robot
        //3 = guard robot
        //4 = drone robot
        //5 = bug spy robot
        
        type = t;
        x = xx;
        y = yy;
        mode = -1;
        dir = d;
    }
    
    public void draw(Graphics g, Player p, Display d, int tx, int ty) {
        if (type == 1) {
            fill(200, 200, 0, (int)(80 * (fade / 255.0)), g);
            rect((int)((lx+rx)/2), y - 13, rx - lx, 17, g, tx, ty);
        }
        
        if (type == 0) {
            fill(200, 0, 0, fade, g);
        }
        else if (type == 1) {
            fill(0, 0, 200, fade, g);
        }
        
        if (type == 2) {
            fill(180, 0, 0, fade, g);
            if (dir == -1) {
                rect((int)x + 10, y + 44, 20, 3, g, tx, ty);
                rect((int)x + 10, y + 55, 20, 3, g, tx, ty);
                rect((int)x + 10, y + 65, 20, 3, g, tx, ty);
                rect((int)x + 10, y + 76, 20, 3, g, tx, ty);
            }
            else {
                rect((int)x + 30, y + 44, 20, 3, g, tx, ty);
                rect((int)x + 30, y + 55, 20, 3, g, tx, ty);
                rect((int)x + 30, y + 65, 20, 3, g, tx, ty);
                rect((int)x + 30, y + 76, 20, 3, g, tx, ty);
            }
            fill(40, 0, 0, fade, g);
            ellipse((int)x + 20, y + 60, 30, 45, g, tx, ty);
            
        }
        
        if (type != 2) {
            rect((int)x, y - 13, 17, 17, g, tx, ty);
            rect((int)x, y, 10, 10, g, tx, ty);
            rect((int)x, y + 25, 23, 40, g, tx, ty);
            rect((int)x, y + 60, 8, 30, g, tx, ty); //x - 12, x + 12, y - 22, y + 75
        }
    }
    
    public void update(Player p, Display d) {
        laserFrame++;
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
                    if (d.blocks.get(i).t == 1) {
                        die();
                    }
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
            
            
            
            if ((p.effect == 1 || p.effect == 3 || p.effect == 5) && Math.sqrt(Math.pow(p.x+p.w/2-x,2)+Math.pow(p.y+p.h/2-y,2)) < 75) {
                die();
            }
            if (p.effect != 1 && p.effect != 3 && p.effect != 5 && p.x + 15 >= this.x - 12 && p.x - 15 <= this.x + 12 && p.y + 15 >= this.y - 22 && p.y - 15 <= this.y + 75) {
                p.die(d);
            }
        }
        else if (type == 1) {
            if ((p.effect == 3) && Math.sqrt(Math.pow(p.x+p.w/2-x,2)+Math.pow(p.y+p.h/2-y,2)) < 75) {
                die();
            }
            //System.out.println(lx + " " + rx + " " + (p.x > lx && p.x < rx));
            if (p.x > lx && p.x < rx && p.y + p.h >= y - 21 && p.y <= y - 5) {
                p.die(d);
            }
            
            if (vely == 0 && p.x + 15 >= this.x - 12 && p.x - 15 <= this.x + 12 && p.y + 15 >= this.y - 22 && p.y - 15 <= this.y + 75 && (p.effect != 3)) {
                p.die(d);
            }
            
            y += vely;
            vely += 0.35;
            //System.out.println(vely);
            for (int i = 0 ; i < d.blocks.size() ; i++) {
                if (x + 12 >= d.blocks.get(i).x && x - 12 <= d.blocks.get(i).x + d.blocks.get(i).w && y + 75 >= d.blocks.get(i).y && y - 22 <= d.blocks.get(i).y + d.blocks.get(i).h) {
                    y = d.blocks.get(i).y - 75;
                    //System.out.println("HELLO!");
                    vely = 0;
                }
            }
            
            if (laserFrame % 500 <= 200) {
                int mxx = 0;
                for (int i = 0 ; i < d.blocks.size() ; i++) {
                    Block b = d.blocks.get(i);
                    if (b.x + b.w < x && y - 13 >= b.y && y - 13 <= b.y + b.h) {
                        mxx = Math.max(mxx, b.x + b.w);
                    }
                }
                lx = mxx;
                rx = (int)x;
                
            }
            else if (laserFrame % 500 >= 250 && laserFrame % 500 <= 450) {
                int mnx = 1000000000;
                for (int i = 0 ; i < d.blocks.size() ; i++) {
                    Block b = d.blocks.get(i);
                    if (b.x > x && y - 13 >= b.y && y - 13 <= b.y + b.h) {
                        mnx = Math.min(mnx, b.x);
                    }
                }
                lx = (int)x;
                rx = mnx;
            }
            else {
                lx = 0;
                rx = 0;
            }
        }
        else if (type == 2) {
            int py = y;
            System.out.println(x);
            y += 1 * mode;
            boolean found = false;
            for (int i = 0 ; i < d.blocks.size() ; i++) {
                if (Math.abs(x - d.blocks.get(i).x) <= 5 && y + 38 <= d.blocks.get(i).y + d.blocks.get(i).h && y + 82 >= d.blocks.get(i).y) {
                    if (d.blocks.get(i).t == 1) {
                        die();
                    }
                    if (!(py + 38 <= d.blocks.get(i).y + d.blocks.get(i).h && py + 82 >= d.blocks.get(i).y)) {
                        found = true;
                        y = py;
                    }
                }
                
            }
            if (found) {
                mode = -mode;
            }
            
            if (p.x + 30 >= x + 5 && p.x <= x + 35 && p.y + 30 >= y + 38 && p.y <= y + 82) {
                //System.out.println("COLLIDING WITH EFFECT " + p.effect);
                if (p.effect == 5) {
                    this.die();
                }
                else {
                    p.die(d);
                }
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