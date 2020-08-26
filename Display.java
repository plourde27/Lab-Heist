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
        blocks.add(new Block(0, 560, 1080, 200));
        blocks.add(new Block(300, 520, 200, 40));
        blocks.add(new Block(700, 440, 200, 40));
        blocks.add(new Block(300, 320, 40, 240));
        blocks.add(new Block(340, 420, 40, 140));
        
        p.chemicals.add(new Chemical(0, 300, 500));
        p.chemicals.add(new Chemical(1, 240, 500));
        p.chemicals.add(new Chemical(2, 140, 500));
        
        robots.add(new Robot(0, 600, 500));
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
        
        fill(255, 255, 255, g);
        rect(540, 640, 1080, 160, g, tx, ty);
        
        p.display(g, kb, tx, ty, this, mouse, mm);
        
        
        mouse.clicked = false;
        
    }
}