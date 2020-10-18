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
    int oid;
    int map[][];
    int x, y;
    int ox, oy;
    int status;
    boolean dragging;
    int diffX;
    int diffY;
    int circSize;
    
    int col1[];
    int col2[];
    
    int RED[] = {255, 0, 0};
    int GREEN[] = {0, 255, 0};
    int BLUE[] = {0, 0, 255};
    int WHITE[] = {255, 255, 255};
    
    public Chemical(int idd, int xx, int yy) {
        id = idd;
        oid = id;
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
        ox = x;
        oy = y;
        
        dragging = false;
        
        circSize = 0;
        
        col1 = RED;
        col2 = RED;
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
        if (id == 0) {
            col1 = RED;
            col2 = RED;
        }
        else if (id == 1) {
            col1 = GREEN;
            col2 = GREEN;
        }
        else if (id == 2) {
            col1 = BLUE;
            col2 = BLUE;
        }
        else if (id == 3) {
            col1 = RED;
            col2 = GREEN;
        }
        else if (id == 4) {
            col1 = BLUE;
            col2 = RED;
        }
        else if (id == 5) {
            col1 = GREEN;
            col2 = BLUE;
        }
        else if (id == 6) {
            col1 = WHITE;
            col2 = WHITE;
        }
        
        circSize += 10;
        if (status == 2 && circSize >= 150) {
            status = 3;
            p.effect = id;
            p.timeleft = 700;
            p.mx = p.timeleft;
            p.col1 = col1;
            p.col2 = col2;
        }
        if (m.clicked && Math.sqrt((m.x-x)*(m.x-x)+(m.y-y)*(m.y-y)) <= 25 && status == 1 && !d.chosen) {
            dragging = true;
            d.chosen = true;
            diffX = m.x - x;
            diffY = m.y - y;
        }
        if (!m.pressed) {
            if (dragging && status == 1) {
                for (int i = 0 ; i < p.chemicals.size() ; i++) {
                    Chemical c2 = p.chemicals.get(i);
                    if (c2 == this) continue;
                    if (c2.status != 1) continue;
                    if (Math.sqrt((x-c2.x)*(x-c2.x)+(y-c2.y)*(y-c2.y)) <= 25) {
                        c2.id = mix(c2);
                        p.chemicals.remove(p.chemicals.indexOf(this));
                        break;
                    }
                }
            }
            dragging = false;
            d.chosen = false;
        }
        
        if (dragging) {
            x = mm.x - diffX;
            y = mm.y - diffY;
        }
        
        if (status == 1) {
            if (x < 25) {
                x = 25;
            }
            
            if (x > 1055) {
                x = 1055;
            }
            
            if (y > 670) {
                y = 670;
            }
        }
        
        if (y < 560 && status == 1 && !m.pressed) {
            circSize = 0;
            status = 2;
        }
        
        
    }
    
    public void draw(Graphics g, int tx, int ty) {
        int vx = tx;
        int vy = ty;
        
        if (status == 1 || status == 2) {
            vx = 0;
            vy = 0;
        }
        
        
        fill(200, 200, 200, 100, g);
        rect(x, y - 8, 10, 25, g, vx, vy);
        
        fill(col1[0], col1[1], col1[2], 200, g);
        
        ellipse(x, y + 2, 10, 12, g, vx, vy);
        
        fill(200, 200, 200, 200, g);
        strokeWeight(3, g);
        ellipseOutline(x, y + 2, 10, 12, g, vx, vy);
        
        fill(col1[0], col1[1], col1[2], 200, g);
        
        rect(x, y - 2, 10, 10, g, vx, vy);
        
        fill(col2[0], col2[1], col2[2], 200, g);
        
        rect(x, y - 10, 10, 10, g, vx, vy);
        
        fill(200, 200, 200, 200, g);
        
        line(x - 5, y - 19, x - 5, y + 4, g, vx, vy);
        line(x + 5, y - 19, x + 5, y + 4, g, vx, vy);
        
        if (status == 2) {
            fill(col1[0], col1[1], col1[2], 200, g);
            ellipse(x, y, circSize, circSize, g, vx, vy);
        }
    }
    
    public void display(Graphics g, int tx, int ty, Mouse mm, MoveMouse m, Display d, Player p) {
        update(mm, m, d, p);
        if (status < 3) {
            draw(g, tx, ty);
        }
        
    }
}