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
    
    
    double dfr = 0;
    int fr = 0;
    
    Game game;
    Mouse mouse;
    MoveMouse mm;
    Keyboard kb;
    Frame frame;
    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    Cursor arrowCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    
    String scene = "Menu";
    
    int room = 0;
    int oroom = room;
    int stage = 0;
    
    int tx, ty;
    int sizeX, sizeY;
    
    int[] stageNum = {35, 0, 0, 0, 0, 0};
    int[] stageRooms = {0, 0, 0, 0, 0, 0};
    int[][] stageCols = {{200, 0, 0}, {200, 0, 200}, {200, 200, 0}, {200, 0, 100}, {125, 55, 0}, {0, 200, 100}};
    
    String[] stageNames = {"Speedy Jumps", "Sticky Ceilings", "Bombs and Backpacks", "Teleportation", "No Name", "No Name"};
    
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
    
    int[][][] cols;
    
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
        
        cols = new int[100][100][4];
        
        for (int i = 0 ; i < 100 ; i++) {
            for (int j = 0 ; j < 100 ; j++) {
                int[] col = new int[]{0, 0, 0, 0};
                if (Math.random() < 0.5) {
                    if (Math.random() < 0.33) {
                        col = new int[]{255, 0, 0, 0};
                    }
                    else if (Math.random() < 0.5) {
                        col = new int[]{0, 255, 0, 0};
                    }
                    else {
                        col = new int[]{0, 0, 255, 0};
                    }
                }
                else {
                    if (Math.random() < 0.33) {
                        col = new int[]{255, 0, 0, 1};
                    }
                    else if (Math.random() < 0.5) {
                        col = new int[]{0, 255, 0, 1};
                    }
                    else {
                        col = new int[]{0, 0, 255, 1};
                    }
                }
                cols[i][j] = col;
            }
        }
        
        addClip("labheist.wav");
        addClip("birthday.wav");
        addClip("menu.wav");
        
        play("menu.wav");
        
        
        
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
         clip.loop(Clip.LOOP_CONTINUOUSLY);  // repeat forever
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
                   clip.loop(Clip.LOOP_CONTINUOUSLY);  // repeat forever
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
    
    public void labBR(Graphics g) {
        dfr += 0.18;
        fr = (int)dfr;
        int ix = 1080/25;
        int iy = 700/10;
        double cs = 1.8;
        double rs = 0.6;
        int n = 0;
        int m = 0;
        for (int i = 0 ; i < 2160 ; i += ix) {
            m = 0;
            for (int j = 0 ; j < 720 ; j += iy) {
                int[] col = new int[]{cols[n][m][0], cols[n][m][1], cols[n][m][2]};
                int x = i + ix/2 + fr;
                if (m % 2 == 1) {
                    x = i + ix/2 - fr;
                }
                
                if (cols[n][m][3] == 0) {
                    drawChemical((x)%2160 - 540, j + iy/2 + 5, g, col[0], col[1], col[2], col[0], col[1], col[2], 0, 0, (int)cs);
                }
                else {
                    drawBot((x)%2160 - 540, j + iy/2 - 15, rs, col[0], col[1], col[2], g, 0, 0);
                }
                //fill(0, 0, 0, g);
                //line(0, j, 1080, j, g, 0, 0);
                m++;
            }
            
            n++;
        }
        fill(255, 255, 255, 220, g);
        rect(540, 360, 1080, 720, g, 0, 0);
    }
    
    public void paintComponent(Graphics g){
        
        
        super.paintComponent(g);
        
        fill(255, 255, 255, g);
        rect(540, 360, 1080, 720, g, 0, 0);
        
        
        
        switch (scene) {
            case "Menu":
                labBR(g);
                
                fill(0, 0, 0, g);
                textSize(120, g);
                textFont("Skia", 120, g);
                text("LAB HEIST", 230, 140, g, 0, 0);
                int cmy = 285;
                drawChemical(540, cmy, g, 255, 0, 0, 255, 0, 0, 0, 0, 4);
                drawChemical(200, cmy, g, 0, 255, 0, 0, 255, 0, 0, 0, 4);
                drawChemical(880, cmy, g, 0, 0, 255, 0, 0, 255, 0, 0, 4);
                int rby = 405;
                drawBot(200, rby, 1.7, 255, 0, 0, g, 0, 0);
                drawBot(540, rby, 1.7, 0, 0, 255, g, 0, 0);
                drawBot(880, rby, 1.7, 0, 255, 0, g, 0, 0);
                int by = 618;
                fill(0, 0, 0, g);
                rect(540, by, 160, 80, g, 0, 0);
                fill(255, 255, 255, g);
                rect(540, by, 156, 76, g, 0, 0);
                fill(0, 0, 0, g);
                textFont("Monaco", 30, g);
                ntext("Play", 540, by, g, 0, 0);
                
                if (mouse.clicked && mouse.x >= 460 && mouse.x <= 620 && mouse.y >= 578 && mouse.y <= 658) {
                    scene = "LevelSelect";
                }
                break;
            case "LevelSelect":
                labBR(g);
                fill(0, 0, 0, g);
                textSize(120, g);
                textFont("Skia", 110, g);
                text("LAB SELECT", 230, 134, g, 0, 0);
                fill(0, 0, 0, g);
                strokeWeight(1, g);
                line(0, 180, 1080, 180, g, 0, 0);
                
                int x = 135;
                int y = 320;
                
                for (int i = 0 ; i < 4 ; i++) {
                    fill(0, 0, 0, g);
                    rect(x, y, 200, 200, g, 0, 0);
                    fill(255, 255, 255, g);
                    rect(x, y, 196, 196, g, 0, 0);
                    
                    fill(stageCols[i][0], stageCols[i][1], stageCols[i][2], g);
                    rect(x, y + 58, 196, 80, g, 0, 0);
                    
                    fill(255, 255, 255, g);
                    textFont("Monaco", 14, g);
                    ntext("LAB " + (i+1) + " \\ " + stageNames[i] + " \\ " + (stageRooms[i]) + " / " + (stageNum[i]) + " Rooms Completed", x + 7, y + 61, g, 0, 0);
                    
                    if (i == 0 && mouse.clicked && mouse.x >= x - 100 && mouse.x <= x + 100 && mouse.y >= y - 100 && mouse.y <= y + 100) {
                        scene = "Play";
                        play("labheist.m4a");
                        room = stageRooms[i];
                        start = true;
                    }
                    
                    if (i > 0) {
                        fill(255, 255, 255, 210, g);
                        rect(x, y, 200, 200, g, 0, 0);
                    }
                    
                    x += 270;

                    if (x > 1080) {
                        x = 135;
                        y += 220;
                    }
                    
                    
                    
                    
                }
                fill(0, 0, 0, g);
                textFont("Monaco", 40, g);
                ntext("More Stages Coming Soon!", 540, 600, g, 0, 0);
                break;
            case "Play":
        
                sec -= 0.01;
                
                
                
                
                
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
                int lty = -100;
                int rty = sizeX*40 - 560;
                
                tx = p.x - 540;
                tx = Math.max(tx, ltx);
                tx = Math.min(tx, rtx);
                
                ty = p.y - 320;
                ty = Math.max(ty, lty);
                
                
                ty = Math.min(ty, rty);
                
                fill(255, 255, 255, g);
                rect(540, 640, 1080, 160, g, 0, 0);
                
                p.display(g, kb, tx, ty, this, mouse, mm);

                
                
                
                int ttx = -20;
                fill(255, 255, 255, 255, g);
                rect(540, 50, 1080, 100, g, 0, 0);
                
                fill(0, 0, 0, g);
                strokeWeight(3, g);
                line(0, 100, 1080, 100, g, 0, 0);
                strokeWeight(1, g);
                
                fill(0, 0, 0, g);
                textSize(30, g);
                text("LAB HEIST", 140, 80 + ttx, g, 0, 0);
                text("STAGE " + (stage+1), 350, 80 + ttx, g, 0, 0);
                text("ROOM " + (room+1), 530, 80 + ttx, g, 0, 0);
                
                fill(0, 0, 0, g);
                rect(850, 70 + ttx, 310, 46, g, 0, 0);
                fill(255, 255, 255, g);
                rect(850, 70 + ttx, 300, 36, g, 0, 0);
                fill(255, 0, 0, 200, g);
                rect(700 + (int)(300*(p.health/200.0)), 70 + ttx, (int)(300*(p.health/100.0)), 36, g, 0, 0);
                
                fill(0, 0, 0, g);
                textSize(21, g);
                text("Health: " + p.health, 780, 78 + ttx, g, 0, 0);
                
                if (selfdestruct) {
                    fill(0, 0, 0, g);
                    textSize(30, g);
                    text("SELF-DESTRUCT SEQUENCE INITIATED!", 300, 135 + ttx, g, 0, 0);
                    fill(255, 0, 0, g);
                    textSize(24, g);
                    text("THE LAB WILL SELF-DESTRUCT IN " + (int)sec + " SECONDS", 310, 170 + ttx, g, 0, 0);
                }
                else {
                    sec = 300;
                }
                
                
                
                if (p.x > sizeY*40 || p.y > sizeX*40 || p.x < 0 || p.y < 0) {
                    oroom = room;
                    room = p.room;
                    setLevel();
                }
                
                mouse.clicked = false;
                break;
        }
        
    }
}