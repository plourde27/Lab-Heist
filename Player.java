import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class Player extends drawInterface {
    int x, y, w, h;
    int RIGHT = 39;
    int LEFT = 37;
    int UP = 38;
    int DOWN = 40;
    
    int ox, oy;
    
    double vely = 0;
    double velx = 0;
    
    boolean falling = true;
    
    int effect;
    int speed;
    
    ArrayList<Chemical> chemicals;
    
    public Player() {
        x = 500;
        y = 385;
        w = 30;
        h = 30;
        
        speed = 6;
        
        chemicals = new ArrayList<Chemical>();
        
        effect = 0;
    }
    
    public void draw(Graphics g, int tx, int ty) {
        fill(255, 0, 0, g);
        rect(x + w/2, y + h/2, w, h, g, tx, ty);
        
    }
    
    public void update(Keyboard kb, Display d) {
        System.out.println(effect);
        if (effect == 1) {
            speed = 12;
        }
        else {
            speed = 6;
        }
        
        velx = 0;
        
        if (kb.keys[RIGHT]) {
            velx += speed;
        }
        
        if (kb.keys[LEFT]) {
            velx = -speed;
        }
        
        if (kb.keys[UP] && !falling) {
            falling = true;
            vely = -10;
        }
        
        ox = x;
        oy = y;
        
        vely += 0.4;
        y += vely;
        
        x += velx;
        
        
        
        /*for (int i = 0 ; i < d.blocks.size() ; i++) {
            Block b = d.blocks.get(i);
            if (x + w >= b.x && x <= b.x + b.w && y + h >= b.y && y <= b.y + b.h) {
                if (velx > 0) {
                    x = b.x - w;
                }
                else {
                    x = b.x + b.w;
                }
            }
        }*/
        
        falling = true;
        
        for (int i = 0 ; i < d.blocks.size() ; i++) {
            Block b = d.blocks.get(i);
            if (x + w > b.x && x < b.x + b.w && y + h > b.y && y < b.y + b.h) {
                if (ox + w > b.x && ox < b.x + b.w) {
                    if (vely > 0) {
                        y = b.y - h;
                        vely = 0;
                        falling = false;
                    }
                    else {
                        y = b.y + b.h;
                        vely = 0;
                    }
                }
                else {
                    if (velx > 0) {
                        x = b.x - w;
                    }
                    else {
                        x = b.x + b.w;
                    }
                }
            }
        }
        
        for (int i = 0 ; i < chemicals.size() ; i++) {
            Chemical c = chemicals.get(i);
            
            //x - 5, x + 5, y - 20, y + 8
            
            if (c.status == 0 && x + w >= c.x - 5 && x <= c.x + 5 && y + w >= c.y - 20 && y <= c.y + 8) {
                c.status = 1;
                System.out.println("!!!");
                int ct = 0;
                int ox = c.x;
                int oy = c.y;
                while (true && ct < 1000) {
                    c.x = (int)(Math.random() * 1040) + 40;
                    c.y = (int)(Math.random() * 120) + 580;
                    
                    boolean f = true;
                    
                    for (int j = 0 ; j < chemicals.size() ; j++) {
                        Chemical c2 = chemicals.get(j);
                        if (i == j) continue;
                        if (c2.status != 1) continue;
                        if (Math.sqrt((c.x-c2.x)*(c.x-c2.x)+(c.y-c2.y)*(c.y-c2.y)) <= 25) {
                            f = false;
                        }
                    }
                    
                    if (f) {
                        break;
                    }
                    ct++;
                }
                
                if (ct == 1000) {
                    c.x = ox;
                    c.y = oy;
                    c.status = 0;
                }
            }
        }
        
        
    }
    
    public void display(Graphics g, Keyboard kb, int tx, int ty, Display d, Mouse m, MoveMouse mm) {
        draw(g, tx, ty);
        update(kb, d);
        
        for (int i = 0 ; i < chemicals.size() ; i++) {
            System.out.println(chemicals.get(i).status);
            chemicals.get(i).display(g, tx, ty, m, mm, d, this);
        }
    }
}