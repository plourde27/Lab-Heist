import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class Chemical {
    int id;
    int map[][];
    public Chemical(int idd) {
        id = idd;
        /*0 = Red
          1 = Green
          2 = Blue
          3 = Red + Green
          4 = Red + Blue
          5 = Green + Blue
          6 = White
         */
        
        int MX = 100;
        
        answer = new int[MX][MX];
        for (int i = 0 ; i < MX ; i++) {
            for (int j = 0 ; j < MX ; j++){ {
                answer[i][j] = -1;
            }
        }
        
        map[0][1] = 3;
        map[0][2] = 4;
        map[1][2] = 5;
        map[3][2] = 6;
        map[4][1] = 6;
        map[5][0] = 6;
    }
    
    public void mix(Chemical other) {
        if (map[id][other.id] != -1) {
            return map[id][other.id];
        }
        if (map[other.id][id] != -1) {
            return map[other.id][id];
        }
    }
}