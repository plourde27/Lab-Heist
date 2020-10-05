import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class Display extends drawInterface {
    
    Game game;
    Mouse mouse;
    MoveMouse mm;
    Keyboard kb;
    Frame frame;
    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    Cursor arrowCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    
    
    int tx, ty;
    int sizeX, sizeY;
    
    Player p;
    ArrayList<Block> blocks;
    ArrayList<Robot> robots;
    boolean start;
    
    boolean chosen = false;
    
    public Display(Game g, Mouse m, Keyboard k, MoveMouse mmw, Frame frm) {
        super();
        frame = frm;
        game = g;
        mouse = m;
        kb = k;
        mm = mmw;
        p = new Player();
        start = true;
        blocks = new ArrayList<Block>();
        robots = new ArrayList<Robot>();
    }
    
    public void draw(){
        super.repaint();
    }
    
    public void resetMatrix() {
        tx = 0;
        ty = 0;
    }
    
    public void translate(int xx, int yy) {
        tx += xx;
        ty += yy;
    }
    
    public void setLevel() {
        String[] level = {
                          "....................................",
                          "....................................",
                          "....................................",
                          ".....xxxxxx.............xx........x.",
                          "..xxxxxxxxxx........................",
                          "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                        };
        
        sizeX = level.length;
        sizeY = level[0].length();
        
        for (int i = 0 ; i < sizeX ; i++) {
            for (int j = 0 ; j < sizeY ; j++) {
                String ans = level[i].substring(j, j+1);
                if (ans.equals("x")) {
                    blocks.add(new Block(j*40, i*40, 40, 40));
                }
            }
        }
        
        p.x = 50;
        p.y = sizeX * 40 - 30;
        /*blocks.add(new Block(0, 520, 100080, 200));
        blocks.add(new Block(300, 480, 200, 40));
        blocks.add(new Block(700, 380, 200, 40));
        blocks.add(new Block(300, 320, 40, 240));
        blocks.add(new Block(340, 420, 40, 140));
        blocks.add(new Block(1040, 340, 40, 200));
        blocks.add(new Block(0, 340, 40, 200));
        blocks.add(new Block(1080, 440, 40, 200));
        blocks.add(new Block(1380, 440, 40, 200));
        blocks.add(new Block(1120, 480, 260, 40, 1));
        
        p.chemicals.add(new Chemical(0, 280, 480));
        p.chemicals.add(new Chemical(1, 220, 480));
        p.chemicals.add(new Chemical(2, 120, 480));
        
        robots.add(new Robot(0, 600, 240));*/
    }
    
    public void paintComponent(Graphics g){
        
        
        super.paintComponent(g);
        
        
        
        if (start) {
            setLevel();
            start = false;
        }
        
        for (int i = 0 ; i < blocks.size() ; i++) {
            blocks.get(i).display(g, tx, ty, this);
        }
        
        for (int i = 0 ; i < robots.size() ; i++) {
            robots.get(i).display(g, p, this, tx, ty);
        }
        
        int ltx = 0;
        int rtx = sizeY*40 - 1080;
        int lty = 0;
        int rty = sizeX*40 - 560;
        tx = p.x - 540;
        tx = Math.max(tx, ltx);
        tx = Math.min(tx, rtx);
        
        ty = p.y - 320;
        ty = Math.min(ty, lty);
        
        
        ty = Math.min(ty, rty);
        
        System.out.println(ty + " " + rty);
        System.out.println("end");
        
        fill(255, 255, 255, g);
        rect(540, 640, 1080, 160, g, 0, 0);
        
        p.display(g, kb, tx, ty, this, mouse, mm);
        
        
        
        
        mouse.clicked = false;
        
    }
}