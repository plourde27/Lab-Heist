import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class Level {
    
    int level = -1;
    int room = -1;
    
    public Level(int l, int r) {
        level = l;
        room = r;
    }
    
    public String[] setLevel() {
        String[][] stage1 = {
                         {"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                          "x.....a..........x...........................x",
                          "x.....?......................................x",
                          "x.................................a..........a",
                          "x....xxxxxx.............xx........xx.........a",
                          "x.xxxxxxxxxx.....b...c.......a..A..x!!!!!!!xxx",
                          "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"}, //room 1
                          
                          {"xxxxxxxxx",
                           "x.......x",
                           "x.......x",
                           "x.......x",
                           "xxxxxxxxx"} //room 2
                          
                          
                        };
        
        String[][][] totalStages = {stage1};
        
        return totalStages[level][room];
        
    }
}