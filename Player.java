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
    
    int room = 0;
    
    int fc = 0;
    
    boolean firstFall = true;
    
    int healthframe = 0;
    
    boolean moving = false;
    
    int lx = 0;
    int ly = 0;
    int mxax = 0;
    int mnax = 0;
    
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
    
    int health = 100;
    
    int savedRoom = 0;
    int savedX = 40;
    int savedY = 400;
    
    ArrayList<Chemical> chemicals;
    ArrayList<Chemical> ochemicals;
    
    public Player() {
        x = 500;
        y = 385;
        w = 30;
        h = 30;
        
        speed = 6;
        
        chemicals = new ArrayList<Chemical>();
        ochemicals = new ArrayList<Chemical>();
        
        effect = -1;
        
        mx = 0;
    }
    
    public void draw(Graphics g, int tx, int ty) {
        int wid = 0;
        if (mx > 0) {
            wid = (int) ((((double)timeleft) / mx) * 1080);
        }
        
        if (effect == 1) {
            fill(0, 200, 0, 50, g);
            ellipse(x+w/2, y+h/2, 150, 150, g, tx, ty);
        }
        
        fill(col1[0], col1[1], col1[2], 200, g);
        
        rect(1080 - wid / 2, 5, wid, 10, g, 0, 0);
        
        fill(col2[0], col2[1], col2[2], 200, g);
        
        rect(1080 - wid / 2, 15, wid, 10, g, 0, 0);
        
        fill(255, 0, 0, g);
        
        rect(x + w/2, y + h/2, w, h, g, tx, ty);
        
    }
    
    public void update(Keyboard kb, Display d) {
        
        healthframe++;
        if (healthframe % 24 == 0 && health < 100) {
            health++;
        }
        
        timeleft--;
        
        if (timeleft <= 0) {
            effect = -1;
            timeleft = 0;
        }
        
        if (effect == 0) {
            speed = 12;
        }
        else if (effect == 3) {
            speed = 10;
        }
        else if (effect == 4) {
            speed = 13;
        }
        else {
            speed = 6;
        }
        
        velx = 0;
        
        moving = false;
        
        if (kb.keys[RIGHT]) {
            velx = speed;
            moving = true;
        }
        
        if (kb.keys[LEFT]) {
            velx = -speed;
            moving = true;
        }
        
        int mnx = -1;
        
        
        
        if (effect == 1 && !moving) {
            int mn = 1000000000;
            for (int i = 0 ; i < d.blocks.size() ; i++) {
                Block b = d.blocks.get(i);
                int val = Math.min(Math.abs(b.x - (x+w)), Math.abs(x - (b.x+b.w)));
                if (b.t == 1 && val < mn && Math.abs(b.y - y) <= 100) {
                    mn = val;
                    mnx = b.x;
                }
            }
            
            if (mnx != -1) {
                if (mnx > x) {
                    velx = 3;
                }
                else {
                    velx = -3;
                }
            }
        }
        
        if (kb.keys[UP] && !falling && effect != 2) {
            falling = true;
            firstFall = true;
            vely = -11;
        }
        
        ox = x;
        
        System.out.println(sticking);
        if (sticking && (effect == 2 || effect == 4)) {
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
        
        if (kb.keys[UP] && sticking && (effect == 2 || effect == 4)) {
            y -= 2;
            vely = -2;
            if (y < stickly) {
                y = stickly;
            }
            if (y > stickhy) {
                y = stickhy;
            }
        }
        
        if (kb.keys[DOWN] && sticking && effect == 2) {
            y += 2;
            vely = 2;
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
        
        int prevy = y;
        
        falling = true;
        
        if (x != stickx) {
            sticking = false;
        }
        
        boolean first = true;
        
        fc++;
        
        for (int i = 0 ; i < d.blocks.size() ; i++) {
            
            Block b = d.blocks.get(i);
            
            if (b.t == 4) {
                if (((ox <= b.x + 20 && x >= b.x + 20) || (ox >= b.x + 20 && x <= b.x + 20)) && y >= b.y - 60 && y <= b.y + 40) {
                    b.on = true;
                    savedX = x;
                    savedY = y;
                    savedRoom = d.room;
                }
            }
            
            boolean seenb = false;
            
            if (x + w > b.x && x < b.x + b.w && y + h > b.y && y < b.y + b.h) {
                
                if (b.t == 0) {
                
                    if (ox + w > b.x && ox < b.x + b.w) {
                        if (vely > 0.0) {
                            y = b.y - h;
                            if (vely > 15) {
                                health -= (vely - 15) * 6;
                                if (health <= 0) {
                                    die(d);
                                }
                            }
                            if (!seenb && effect == 3) {
                                if (firstFall) {
                                    firstFall = false;
                                    mnax = x;
                                    mxax = x;
                                }
                                else {
                                    d.blocks.remove(d.blocks.size() - 1);
                                }
                                
                                mnax = Math.min(mnax, x);
                                mxax = Math.max(mxax, x);
                                
                                
                                d.blocks.add(new Block(mnax, y + 10, mxax - mnax + 30, 20, 1));
                                
                                seenb = true;
                            }
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
                else if (b.t == 1 && effect != 3) {
                    health -= 3;
                    if (health <= 0) {
                        die(d);
                        break;
                    }
                }  
                else if (b.t == 3) {
                    room = b.rbt;
                }
            }
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
    
    public void die(Display d) {
        for (int i = 0 ; i < d.allChemicals.size() ; i++) {
            d.allChemicals.get(i).status = 0;
            d.allChemicals.get(i).x = d.allChemicals.get(i).ox;
            d.allChemicals.get(i).y = d.allChemicals.get(i).oy;
            d.allChemicals.get(i).id = d.allChemicals.get(i).oid;
        }
        
        d.room = savedRoom;
        x = savedX;
        y = savedY;
        d.setLevel(true);
        health = 100;
        effect = -1;
    }
}