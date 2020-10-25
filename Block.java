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
            rect(x + w/2, y + h/2, w - 20, h - 20, g, tx, ty);
            rect(x + w/2 - 30, y + h - 20, 5, 40, g, tx, ty);
            rect(x + w/2 + 30, y + h - 20, 5, 40, g, tx, ty);
            fill(0, 125, 0, 255, g);
            rect(x + w/2, y + h/2, w - 30, h - 30, g, tx, ty);
            
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
                case 2:
                    w = 240;
                    fill(200, 200, 200, g);
                    textSize(11, g);
                    text("Pick up potions, and drag them into", x + 26, y + 35, g, tx, ty);
                    text("the main game area to use them.", x + 26, y + 55, g, tx, ty);

                    drawChemical(x + 55, y + 87, g, 255, 0, 0, tx, ty);
                    
                    fill(0, 0, 0, g);
                    line(x + 88, y + 77, x + 110, y + 77, g, tx, ty);
                    line(x + 88, y + 86, x + 110, y + 86, g, tx, ty);
                    rect(x + 140, y + 76, 12, 1, g, tx, ty);
                    rect(x + 140, y + 82, 12, 1, g, tx, ty);
                    rect(x + 140, y + 88, 12, 1, g, tx, ty);
                    fill(255, 0, 0, g);
                    rect(x + 165, y + 82, 30, 30, g, tx, ty);
                    break;
                case 3:
                    w = 140;
                    fill(200, 200, 200, g);
                    textSize(16, g);
                    text("Watch out", x + 31, y + 56, g, tx, ty);
                    text("for robots!", x + 31, y + 76, g, tx, ty);
                    break;
                case 4:
                    w = 240;
                    fill(200, 200, 200, g);
                    textSize(12, g);
                    drawChemical(x + 55, y + 62, g, 0, 255, 0, tx, ty);
                    fill(0, 0, 0, g);
                    line(x + 88, y + 53, x + 110, y + 53, g, tx, ty);
                    line(x + 88, y + 62, x + 110, y + 62, g, tx, ty);
                    
                    fill(200, 0, 0, g);
                    strokeWeight(8, g);
                    int yf = 60;
                    ellipse(x + 150, y + yf, 60, 60, g, tx, ty);
                    strokeWeight(0, g);
                    fill(0, 125, 0, 255, g);
                    ellipse(x + 150, y + yf, 56, 56, g, tx, ty);
                    strokeWeight(3, g);
                    fill(200, 0, 0, g);
                    line((int)(x + 150 - 30 * Math.cos(Math.PI/4)), (int)(y + yf - 30 * Math.sin(Math.PI/4)), (int)(x + 150 + 30 * Math.cos(Math.PI/4)), (int)(y + yf + 30 * Math.sin(Math.PI/4)), g, tx, ty);
                    strokeWeight(1, g);
                    drawBot(x + 150, y + 48, 0.4, g, tx, ty);
            }
            
            return;
        }
        
        fill(0, 0, 0, g);
        if (t == 1) {
            fill(0, 184, 0, g);
        }
       
        rect(x + w/2, y + h/2, w, h, g, tx, ty);
    }
    
    public void drawBot(int x, int y, double scl, Graphics g, int tx, int ty) {
        fill(200, 0, 0, g);
        rect((int)x, (int)(y - 12*scl), (int)(17*scl), (int)(17*scl), g, tx, ty);
        rect((int)x, y, (int)(10*scl), (int)(10*scl), g, tx, ty);
        rect((int)x, (int)(y + 24.0*scl), (int)(23*scl), (int)(40*scl), g, tx, ty);
        rect((int)x, (int)(y + 58.0*scl), (int)(8*scl), (int)(30*scl), g, tx, ty);
    }
    
    public void drawChemical(int x, int y, Graphics g, int c1, int c2, int c3, int tx, int ty) {
        fill(200, 200, 200, 100, g);
        rect(x, y - 8, 10, 25, g, tx, ty);
        
        fill(c1, c2, c3, 200, g);
        
        ellipse(x, y + 2, 10, 12, g, tx, ty);
        
        fill(200, 200, 200, 200, g);
        strokeWeight(3, g);
        ellipseOutline(x, y + 2, 10, 12, g, tx, ty);
        
        fill(c1, c2, c3, 200, g);
        
        rect(x, y - 2, 10, 10, g, tx, ty);
        
        fill(c1, c2, c3, 200, g);
        
        rect(x, y - 10, 10, 10, g, tx, ty);
        
        fill(200, 200, 200, 200, g);
        
        line(x - 5, y - 19, x - 5, y + 4, g, tx, ty);
        line(x + 5, y - 19, x + 5, y + 4, g, tx, ty);
    }
    
    public void update(Display d) {
        
    }
    
    public void display(Graphics g, int tx, int ty, Display d) {
        draw(g, tx, ty);
        update(d);
    }
}