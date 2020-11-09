import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;
import java.applet.Applet;
import java.net.URL;
import javax.sound.sampled.*;
import java.applet.AudioClip;
import java.net.URL;

public class Display extends drawInterface {
    
    
    
    Game game;
    Mouse mouse;
    MoveMouse mm;
    Keyboard kb;
    Frame frame;
    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    Cursor arrowCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    
    int room = 0;
    int oroom = room;
    int stage = 0;
    
    int tx, ty;
    int sizeX, sizeY;
    
    int[] stageNum = {35, 0, 0, 0, 0, 0};
    
    Player p;
    ArrayList<Block> blocks;
    ArrayList<Robot> robots;
    ArrayList<Chemical> allChemicals;
    ArrayList<Integer> crooms;
    ArrayList<Block> allBlocks;
    ArrayList<Integer> brooms;
    ArrayList<Robot> allRobots;
    ArrayList<Integer> rrooms;
    
    boolean start;
    
    boolean chosen = false;
    
    boolean selfdestruct = false;
    
    double sec = 300.0;
    
    ArrayList<Integer> xs = new ArrayList<Integer>();
    ArrayList<Integer> ys = new ArrayList<Integer>();
    
    ArrayList<Clip> clips = new ArrayList<Clip>();
    ArrayList<String> clipNames = new ArrayList<String>();
        
    
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
        allChemicals = new ArrayList<Chemical>();
        crooms = new ArrayList<Integer>();
        allBlocks = new ArrayList<Block>();
        brooms = new ArrayList<Integer>();
        allRobots = new ArrayList<Robot>();
        rrooms = new ArrayList<Integer>();
        for (int i = 0 ; i < 1000 ; i += 20) {
            if (Math.random() < 0.27 && ys.size() >= xs.size()) {
                xs.add(i);
            }
            if (Math.random() < 0.27 && xs.size() >= ys.size()) {
                ys.add(i);
            }
        }
        
        addClip("labheist.wav");
        addClip("birthday.wav");
    }
    
    public void addClip(String s) {
      try {
         // Open an audio input stream.
         URL url = this.getClass().getClassLoader().getResource(s);
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clips.add(clip);
         clipNames.add(s);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   public void play(String s) {
       for (int i = 0 ; i < clips.size() ; i++) {
           if (clipNames.get(i).equals(s)) {
               if (clips.get(i).isRunning()) {
                   clips.get(i).stop();
               }
               try {
                   URL url = this.getClass().getClassLoader().getResource(s);
                   AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                     // Get a sound clip resource.
                   Clip clip = AudioSystem.getClip();
                     // Open audio clip and load samples from the audio input stream.
                   clip.open(audioIn);
                   clips.set(i, clip);
                   clips.get(i).start();
               }
               catch (UnsupportedAudioFileException e) {
                 e.printStackTrace();
              } catch (IOException e) {
                 e.printStackTrace();
              } catch (LineUnavailableException e) {
                 e.printStackTrace();
              }
               
            }
            else {
                if (clips.get(i).isRunning()) {
                   clips.get(i).stop();
                }
            }
       }
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
    
    public void setStage() {
        for (int k = 0 ; k < stageNum[stage] ; k++) {
            Level l = new Level(stage, k);
            String[] level = l.setLevel();
            
            sizeX = level.length;
            sizeY = level[0].length();
            
            System.out.println(k + "  " + sizeX + " " + sizeY);
            
            for (int i = 0 ; i < sizeX ; i++) {
                for (int j = 0 ; j < sizeY ; j++) {
                    int x = j*40;
                    int y = i*40;
                    char ans = level[i].charAt(j);
                    if (i == 0 || i == sizeX-1 || j == 0 || j == sizeY-1) {
                    }
                    else if (!level[i].substring(j, j+1).equals("x") && (int)ans >= 97 && (int)ans <= 122 && (i == sizeX - 1 || !level[i + 1].substring(j, j + 1).equals("?"))) {
                        allChemicals.add(new Chemical((int)ans-97, x + 20, y+20));
                        crooms.add(k);
                    } 
                    else if ((int)ans >= 65 && (int)ans <= 90) {
                        int vl = 1;
                        if (level[i].charAt(j+1) != 'x') {
                            vl = -1;
                        }
                        allRobots.add(new Robot ((int)ans-65, x, y-50, vl));
                        rrooms.add(k);
                    }
                    else if (level[i].substring(j, j+1).equals("+")) {
                        
                        allBlocks.add(new Block(j*40, i*40, 40, 40, 4));
                        brooms.add(k);
                    }
                }
            }
        }
    }
    
    public void setLevel(boolean goback) {
        if (Math.random() < 0.5) {
            play("labheist.wav");
        }
        else {
            play("birthday.wav");
        }
        
        blocks = new ArrayList<Block>();
        robots = new ArrayList<Robot>();
        
        for (int i = p.chemicals.size() - 1 ; i >= 0 ; i--) {
            if (p.chemicals.get(i).status == 0) {
                p.chemicals.remove(i);
            }
        }
        
        for (int i = blocks.size() - 1 ; i >= 0 ; i--) {
            if (blocks.get(i).t == 4) {
                blocks.remove(i);
            }
        }
        
        robots = new ArrayList<Robot>();
        
        for (int i = 0 ; i < allChemicals.size() ; i++) {
            if (crooms.get(i) == room && allChemicals.get(i).status == 0) {
                System.out.println(i);
                p.chemicals.add(allChemicals.get(i));
            }
        }
        
        for (int i = 0 ; i < allBlocks.size() ; i++) {
            if (brooms.get(i) == room) {
                blocks.add(allBlocks.get(i));
            }
        }
        
        for (int i = 0 ; i < allRobots.size() ; i++) {
            if (rrooms.get(i) == room) {
                allRobots.get(i).dead = false;
                allRobots.get(i).fade = 255;
                System.out.println(i);
                robots.add(allRobots.get(i));
            }
        }
        
        Level l = new Level(stage, room);
        String[] level = l.setLevel();
        
        sizeX = level.length;
        sizeY = level[0].length();
        
        for (int i = 0 ; i < sizeX ; i++) {
            for (int j = 0 ; j < sizeY ; j++) {
                String ans = level[i].substring(j, j+1);
                int x = j*40;
                int y = i*40;
                if (ans.equals("x")) {
                    blocks.add(new Block(j*40, i*40, 40, 40));
                }
                else if (i == sizeX - 1 || i == 0 || j == 0 || j == sizeY - 1) {
                    int val = (int)ans.charAt(0) - 65;
                    if (val > 26) {
                        val -= 6;
                    }
                    blocks.add(new Block(j*40, i*40, 40, 40, 3, val));
                }
                else if (ans.equals("!")) {
                    blocks.add(new Block(j*40, i*40, 40, 40, 1));
                }
                else if ((int)ans.charAt(0) >= 97 && (int)ans.charAt(0) <= 122 && (i == sizeX - 1 || !level[i + 1].substring(j, j + 1).equals("?"))) {
                    //p.chemicals.add(new Chemical((int)ans.charAt(0)-97, x + 20, y+20));
                }    
                else if ((int)ans.charAt(0) >= 65 && (int)ans.charAt(0) <= 90) {
                    //robots.add(new Robot ((int)ans.charAt(0)-65, x, y-60));
                }
                else if (ans.equals("?")) {
                    int val = level[i - 1].substring(j, j+1).charAt(0) - 97;
                    blocks.add(new Block(j*40 - 40, i*40 - 40, 120, 120, 2, val));
                }
                else if (ans.equals("/")) {
                    blocks.add(new Block(j*40, i*40, 40, 40, 5));
                }
            }
        }
        
        if (goback) {
            return;
        }
        
        int vl = oroom + 65;
        if (vl > 90) {
            vl += 6;
        }
        char tlet = (char)(vl);
        System.out.println("TARGET: " + tlet);
        
        p.x = 120;
        p.y = sizeX * 40 - 80;
        
        for (int i = 0 ; i < sizeX ; i++) {
            for (int j = 0 ; j < sizeY ; j++) {
                char ans = level[i].charAt(j);
                if (ans == tlet) {
                    if (i == sizeX - 1 || i == 0 || j == sizeY - 1 || j == 0) {
                        p.x = j * 40;
                        p.y = i * 40;
                    }
                    if (i == sizeX - 1) {
                        p.y -= 40;
                    }
                    else if (i == 0) {
                        p.y += 40;
                    }
                    else if (j == sizeY - 1) {
                        p.x -= 40;
                    }
                    else {
                        p.x += 40;
                    }
                    break;
                }
            }
        }
        
        p.ochemicals = new ArrayList<Chemical>();
        for (int i = 0 ; i < p.chemicals.size() ; i++) {
            p.ochemicals.add(p.chemicals.get(i));
        }
            
        
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
    
    public void setLevel() {
        setLevel(false);
    }
    
    public void paintComponent(Graphics g){
        
        
        super.paintComponent(g);
        
        sec -= 0.01;
        
        fill(255, 255, 255, g);
        rect(540, 360, 1080, 720, g, 0, 0);
        
        
        
        /*fill(125, 125, 125, 100, g);
        strokeWeight(8, g);
        for (int i = 0 ; i < Math.min(xs.size(), ys.size()) ; i++) {
            line(0, xs.get(i), 540, xs.get(i), g, 0, 0);
            line(540, ys.get(i), 1080, ys.get(i), g, 0, 0);
            line(540, ys.get(i), 540, xs.get(i), g, 0, 0);
        }
        
        strokeWeight(1, g);*/
        
        
        
        if (start) {
            play("labheist.wav");
            setStage();
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
        ty = Math.max(ty, lty);
        
        
        ty = Math.min(ty, rty);
        
        fill(255, 255, 255, g);
        rect(540, 640, 1080, 160, g, 0, 0);
        
        fill(0, 0, 0, g);
        textSize(30, g);
        text("LAB HEIST", 140, 80, g, 0, 0);
        text("STAGE " + (stage+1), 350, 80, g, 0, 0);
        text("ROOM " + (room+1), 530, 80, g, 0, 0);
        
        fill(0, 0, 0, g);
        rect(850, 70, 310, 46, g, 0, 0);
        fill(255, 255, 255, g);
        rect(850, 70, 300, 36, g, 0, 0);
        fill(255, 0, 0, 200, g);
        rect(700 + (int)(300*(p.health/200.0)), 70, (int)(300*(p.health/100.0)), 36, g, 0, 0);
        
        fill(0, 0, 0, g);
        textSize(21, g);
        text("Health: " + p.health, 780, 78, g, 0, 0);
        
        if (selfdestruct) {
            fill(0, 0, 0, g);
            textSize(30, g);
            text("SELF-DESTRUCT SEQUENCE INITIATED!", 300, 135, g, 0, 0);
            fill(255, 0, 0, g);
            textSize(24, g);
            text("THE LAB WILL SELF-DESTRUCT IN " + (int)sec + " SECONDS", 310, 170, g, 0, 0);
        }
        else {
            sec = 300;
        }
        
        p.display(g, kb, tx, ty, this, mouse, mm);
        
        
        if (p.x > sizeY*40 || p.y > sizeX*40 || p.x < 0 || p.y < 0) {
            oroom = room;
            room = p.room;
            setLevel();
        }
        
        mouse.clicked = false;
        
    }
}