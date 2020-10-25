import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class Block extends drawInterface {
    int x, y, w, h;
    int t = 0;
    int rbt = -1;
    boolean on = false;
    
    public Block(int xx, int yy, int ww, int hh) {
        x = xx;
        y = yy;
        w = ww;
        h = hh;
    }
    
    public Block(int xx, int yy, int ww, int hh, int tp) {
        x = xx;
        y = yy;
        w = ww;
        h = hh;
        t = tp;
    }
    
    public Block(int xx, int yy, int ww, int hh, int tp, int r) {
        x = xx;
        y = yy;
        w = ww;
        h = hh;
        t = tp;
        rbt = r;
    }
    
    public void draw(Graphics g, int tx, int ty) {
        if (t == 3) {
            return;
        }
        
        if (t == 5) {
            fill(200, 50, 0, 200, g);
            rect(x + w/2, y + w/2, 4, 40, g, tx, ty);
            return;
        }
        
        if (t == 4) {
            fill(0, 0, 0, g);
            rect(x + 20, y - 10, 6, 100, g, tx, ty);
            fill(255, 0, 0, g);
            if (on) {
                fill(0, 240, 0, g);
            }
            vertex(x + 23, y - 60);
            vertex(x + 52, y - 45);
            vertex(x + 23, y - 30);
            endShape(g, tx, ty);
            return;
        }
        
        if (t == 2) {
            fill(200, 180, 150, 255, g);
            rect(x + w/2, y + w/2, w - 20, h - 20, g, tx, ty);
            rect(x + w/2 - 30, y + h - 20, 5, 40, g, tx, ty);
            rect(x + w/2 + 30, y + h - 20, 5, 40, g, tx, ty);
            fill(0, 125, 0, 255, g);
            rect(x + w/2, y + w/2, w - 30, h - 30, g, tx, ty);
            
            switch (rbt) {
                case 0:
                    fill(200, 200, 200, g);
                    textSize(30, g);
                    text("LAB", x + 30, y + 55, g, tx, ty);
                    text("HEIST", x + 20, y + 85, g, tx, ty);
                    break;
                case 1:
                    fill(200, 200, 200, g);
                    textSize(12, g);
                    text("Use the arrow", x + 19, y + 53, g, tx, ty);
                    text("keys to move", x + 20, y + 74, g, tx, ty);

                    break;
            }
            
            return;
        }
        
        fill(0, 0, 0, g);
        if (t == 1) {
            fill(0, 184, 0, g);
        }
       
        rect(x + w/2, y + h/2, w, h, g, tx, ty);
    }
    
    public void update(Display d) {
        
    }
    
    public void display(Graphics g, int tx, int ty, Display d) {
        draw(g, tx, ty);
        update(d);
    }
}