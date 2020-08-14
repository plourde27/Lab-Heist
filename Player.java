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
    
    public Player() {
        x = 500;
        y = 385;
        w = 30;
        h = 30;
    }
    
    public void draw(Graphics g, int tx, int ty) {
        fill(255, 0, 0, g);
        rect(x + w/2, y + h/2, w, h, g, tx, ty);
        
    }
    
    public void update(Keyboard kb, Display d) {
        velx = 0;
        
        if (kb.keys[RIGHT]) {
            velx += 5;
        }
        
        if (kb.keys[LEFT]) {
            velx = -5;
        }
        
        if (kb.keys[UP] && !falling) {
            falling = true;
            vely = -8;
        }
        
        ox = x;
        oy = y;
        
        vely += 0.35;
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
        
        
    }
    
    public void display(Graphics g, Keyboard kb, int tx, int ty, Display d) {
        draw(g, tx, ty);
        update(kb, d);
    }
}