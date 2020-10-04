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
    
    boolean stickr;
    boolean sticking;
    int stickly;
    int stickhy;
    
    int col1[] = {0, 0, 0};
    int col2[] = {0, 0, 0};
    
    int timeleft = 0;
    int mx;
    
    int ox, oy;
    
    double vely = 0;
    double velx = 0;
    
    boolean falling = true;
    
    int effect;
    int speed;
    int stickx = -1000;
    
    ArrayList<Chemical> chemicals;
    
    public Player() {
        x = 500;
        y = 385;
        w = 30;
        h = 30;
        
        speed = 6;
        
        chemicals = new ArrayList<Chemical>();
        
        effect = -1;
        
        mx = 0;
    }
    
    public void draw(Graphics g, int tx, int ty) {
        int wid = 0;
        if (mx > 0) {
            wid = (int) ((((double)timeleft) / mx) * 1080);
        }
        
        fill(col1[0], col1[1], col1[2], 200, g);
        
        rect(1080 - wid / 2, 5, wid, 10, g, 0, 0);
        
        fill(col2[0], col2[1], col2[2], 200, g);
        
        rect(1080 - wid / 2, 15, wid, 10, g, 0, 0);
        
        fill(255, 0, 0, g);
        
        rect(x + w/2, y + h/2, w, h, g, tx, ty);
        
    }
    
    public void update(Keyboard kb, Display d) {
        
        timeleft--;
        
        if (timeleft <= 0) {
            effect = -1;
            timeleft = 0;
        }
        
        if (effect == 0) {
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
        
        if (kb.keys[UP] && !falling && effect != 2) {
            falling = true;
            vely = -11;
        }
        
        //System.out.println(sticking);
        
        ox = x;
        
        if (kb.keys[UP] && sticking && effect == 2) {
            y -= 3;
            if (y < stickly) {
                y = stickly;
            }
            if (y > stickhy) {
                y = stickhy;
            }
        }
        if (sticking && effect == 2) {
            vely = 0;
            if (stickr && !kb.keys[LEFT]) {
                x += 3;
                velx = 3;
            }
            else if (!stickr && !kb.keys[RIGHT]) {
                x -= 3;
                velx = -3;
            }
        }
        
        if (kb.keys[DOWN] && sticking && effect == 2) {
            y += 3;
            if (y < stickly) {
                y = stickly;
            }
            if (y > stickhy) {
                y = stickhy;
            }
        }
        
        
        oy = y;
        
        vely += 0.37;
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
        
        if (x != stickx) {
            sticking = false;
        }
        
        boolean first = true;
        
        for (int i = 0 ; i < d.blocks.size() ; i++) {
            Block b = d.blocks.get(i);
            if (x + w > b.x && x < b.x + b.w && y + h > b.y && y < b.y + b.h) {
                //System.out.println(i);
                if (ox + w > b.x && ox < b.x + b.w) {
                    if (vely > 0) {
                        y = b.y - h;
                        vely = 0;
                        falling = false;
                        //System.out.println("hell0");
                    }
                    else {
                        y = b.y + b.h;
                        vely = 0;
                        //System.out.println("h3ll0");
                    }
                }
                else {
                    if (velx > 0) {
                        stickr = true;
                        x = b.x - w;
                        if (first) {
                            stickly = b.y - h;
                            stickhy = b.y + b.h;
                            first = false;
                        }
                        else {
                            stickly = Math.min(stickly, b.y - h);
                            stickhy = Math.max(stickhy, b.y + b.h);
                        }
                        sticking = true;
                        stickx = x;
                    }
                    else {
                        stickr = false;
                        x = b.x + b.w;
                        if (first) {
                            stickly = b.y - h;
                            stickhy = b.y + b.h;
                            first = false;
                        }
                        else {
                            stickly = Math.min(stickly, b.y - h);
                            stickhy = Math.max(stickhy, b.y + b.h);
                        }
                        sticking = true;
                        stickx = x;
                    }
                }
            }
        }
        
        if (sticking) {
            //System.out.println(stickly + " " + stickhy);
        }
        
        for (int i = 0 ; i < chemicals.size() ; i++) {
            Chemical c = chemicals.get(i);
            
            //x - 5, x + 5, y - 20, y + 8
            
            if (c.status == 0 && x + w >= c.x - 5 && x <= c.x + 5 && y + w >= c.y - 20 && y <= c.y + 8) {
                c.status = 1;
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
            chemicals.get(i).display(g, tx, ty, m, mm, d, this);
        }
    }
}