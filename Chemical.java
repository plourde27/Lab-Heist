import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class Chemical extends drawInterface {
    int id;
    int map[][];
    int x, y;
    int status;
    boolean dragging;
    int diffX;
    int diffY;
    int circSize;
    
    public Chemical(int idd, int xx, int yy) {
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
        
        map = new int[MX][MX];
        for (int i = 0 ; i < MX ; i++) {
            for (int j = 0 ; j < MX ; j++){
                map[i][j] = -1;
            }
        }
        
        map[0][1] = 3;
        map[0][2] = 4;
        map[1][2] = 5;
        map[3][2] = 6;
        map[4][1] = 6;
        map[5][0] = 6;
        
        status = 0;
        
        x = xx;
        y = yy;
        
        dragging = false;
        
        circSize = 0;
    }
    
    public int mix(Chemical other) {
        if (map[id][other.id] != -1) {
            return map[id][other.id];
        }
        if (map[other.id][id] != -1) {
            return map[other.id][id];
        }
        return -1;
    }
    
    public void update(Mouse m, MoveMouse mm, Display d, Player p) {
        circSize += 10;
        if (status == 2 && circSize >= 150) {
            status = 3;
            System.out.println("Hello!");
            p.effect = id;
        }
        if (m.clicked && Math.sqrt((m.x-x)*(m.x-x)+(m.y-y)*(m.y-y)) <= 25 && status == 1 && !d.chosen) {
            dragging = true;
            d.chosen = true;
            diffX = m.x - x;
            diffY = m.y - y;
        }
        if (!m.pressed) {
            dragging = false;
            d.chosen = false;
        }
        
        if (dragging) {
            x = mm.x - diffX;
            y = mm.y - diffY;
        }
        
        if (x < 25) {
            x = 25;
        }
        
        if (x > 1055) {
            x = 1055;
        }
        
        if (y > 670) {
            y = 670;
        }
        
        if (y < 560 && status == 1 && !m.pressed) {
            circSize = 0;
            status = 2;
        }
    }
    
    public void draw(Graphics g, int tx, int ty) {
        fill(200, 200, 200, 100, g);
        rect(x, y - 8, 10, 25, g, tx, ty);
        
        fill(255, 0, 0, g);
        
        ellipse(x, y + 2, 10, 12, g, tx, ty);
        
        fill(200, 200, 200, g);
        strokeWeight(3, g);
        ellipseOutline(x, y + 2, 10, 12, g, tx, ty);
        
        fill(255, 0, 0, g);
        
        rect(x, y - 5, 10, 19, g, tx, ty);
        
        fill(200, 200, 200, g);
        
        line(x - 5, y - 19, x - 5, y + 4, g, tx, ty);
        line(x + 5, y - 19, x + 5, y + 4, g, tx, ty);
        
        if (status == 2) {
            fill(255, 0, 0, 100, g);
            ellipse(x, y, circSize, circSize, g, tx, ty);
        }
    }
    
    public void display(Graphics g, int tx, int ty, Mouse mm, MoveMouse m, Display d, Player p) {
        if (status < 3) {
            draw(g, tx, ty);
        }
        update(mm, m, d, p);
    }
}