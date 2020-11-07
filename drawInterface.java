import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class drawInterface extends JComponent {
    
    private int tx = 0;
    private int ty = 0;
    double scl = 1.0;
    int tsz = 20;
    int ang = 0;
    int[] coords = new int[]{0, 0, 1080, 720};
    ArrayList<Integer> xs = new ArrayList<Integer>();
    ArrayList<Integer> ys = new ArrayList<Integer>();
    
    
    public drawInterface() {
        /*tx = 0;
        ty = 0;
        scl = 1.0;*/
    }
    
    public void repaint() {
        super.repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    public void translate(int x, int y) {
        tx += x;
        ty += y;
    }
    
    public void scale(double s) {
        scl = (scl * s);
    }
    
    public int[] transform(int x, int y, int tx, int ty) {
        int xx = (int) ((x - tx) * scl);
        int yy = (int) ((y - ty) * scl);
        return new int[]{xx, yy};
    }
    
    public void rect(int x, int y, int w, int h, Graphics g, int tx, int ty) {
        int ox = x;
        int oy = y;
        x = transform(ox, oy, tx, ty)[0];
        y = transform(ox, oy, tx, ty)[1];
        g.fillRect(x - w / 2, y - h / 2, (int) (w * scl), (int) (h * scl));
    }
    
    public void ellipse(int x, int y, int w, int h, Graphics g, int tx, int ty) {
        x = transform(x, y, tx, ty)[0];
        y = transform(x, y, tx, ty)[1];
        g.fillOval((int) (x - (w * scl) / 2), (int) (y - (h * scl) / 2), (int) (w * scl), (int) (h * scl));
    }
    
    public void ellipseOutline(int x, int y, int w, int h, Graphics g, int tx, int ty) {
        x = transform(x, y, tx, ty)[0];
        y = transform(x, y, tx, ty)[1];
        
        
        g.drawOval((int) (x - (w * scl) / 2), (int) (y - (h * scl) / 2), w, h);
    }
    
    public void strokeWeight(int sw, Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(sw));
    }
    
    public void fill(Color c, Graphics gr) {
        gr.setColor(c);
    }
    
    public void fill(int r, int g, int b, Graphics gr) {
        gr.setColor(new Color(r, g, b));
    }
    
    public void fill(int r, int g, int b, int a, Graphics gr) {
        gr.setColor(new Color(r, g, b, a));
    }
    
    public void textSize(int sz, Graphics g) {
        g.setFont(new Font("Avenir", Font.PLAIN, sz));
    }
    
    public void textFont(String f, int sz, Graphics g) {
        tsz = sz;
        g.setFont(new Font(f, Font.PLAIN, sz));
    }
    
    public void textSize(int sz, Graphics g, String f) {
        tsz = sz;
        g.setFont(new Font(f, Font.PLAIN, sz));
    }
    
    public void text(String txt, int x, int y, Graphics g, int tx, int ty) {
        x = transform(x, y, tx, ty)[0];
        y = transform(x, y, tx, ty)[1];
        g.drawString(txt, x, y);
    }
    
    public void ntext(String txt, int x, int y, Graphics g, int tx, int ty) {
        int lns = 0;
        String ntxt = "";
        for (int i = 0 ; i < txt.length() ; i++) {
            
            if (txt.substring(i, i+1).equals("\\")) {
                i++;
                lns ++;
                ntxt = "";
                
            }
            else {
                ntxt += txt.charAt(i);
            }
        }
        
        double TFAC = 0.314;
        double YFAC = 1.1;
        
        //y -= 120;
        y -= (int)((lns / 2.0) * tsz * (YFAC));
        y += (int)(tsz * (0.32));
        
        
        ntxt = "";
        for (int i = 0 ; i < txt.length() ; i++) {
            
            if (txt.substring(i, i+1).equals("\\")) {
                i++;
                //System.out.println(ntxt.length());
                text(ntxt, (int)(x - (ntxt.length() - 2) * (tsz * TFAC)), y, g, tx, ty);
                ntxt = "";
                y += (int) (tsz * YFAC);
            }
            else {
                ntxt += txt.charAt(i);
            }
        }
        //System.out.println(tsz);
        text(ntxt, (int)(x - ntxt.length() * (tsz * TFAC)), y, g, tx, ty);
        /*fill(0, 0, 0, 50, g);
        int cs = ntxt.length();
        rect(x, y,(int)( cs * (tsz * TFAC) * 2), 20, g, tx, ty);
        int st = (int)(x - cs * (tsz * TFAC));
        for (int i = 0 ; i < cs ; i++) {
             st += (tsz * TFAC * 2);
            fill(255, 0, 0, g);
            line(st, y - 10, st, y + 10, g, tx, ty);
           
        }*/
    }
    
    public void line(int x1, int y1, int x2, int y2, Graphics g, int tx, int ty) {
        int ox1 = x1;
        int oy1 = y1;
        int ox2 = x2;
        int oy2 = y2;
        x1 = transform(ox1, oy1, tx, ty)[0];
        y1 = transform(ox1, oy1, tx, ty)[1];
        x2 = transform(ox2, oy2, tx, ty)[0];
        y2 = transform(ox2, oy2, tx, ty)[1];
        g.drawLine(x1, y1, x2, y2);
    }
    
    public void vertex(int x, int y) {
        xs.add(x);
        ys.add(y);
    }
    
    public void endShape(Graphics g, int tx, int ty) {
        int[] x = new int[xs.size()];
        int[] y = new int[ys.size()];
        for (int i = 0 ; i < xs.size() ; i++) {
            x[i] = transform(xs.get(i), ys.get(i), tx, ty)[0];
            y[i] = transform(xs.get(i), ys.get(i), tx, ty)[1];
        }
        g.fillPolygon(new Polygon(x, y, xs.size()));
        xs = new ArrayList<Integer>();
        ys = new ArrayList<Integer>();
    }
    
    public void resetMatrix() {
        tx = 0;
        ty = 0;
        scl = 1.0;
    }
    
    public int[] circleIntersectsLine(int x, int y, int r, int x1, int y1, int x2, int y2) {
        
        double m1 = (y2 - y1) / ((double)(x2 - x1));
        double b1 = y1 - m1 * x1;
        double m2 = -1.0 / m1;
        double b2 = y - m2 * x;
        double ix = (b2 - b1) / (m1 - m2);
        double iy = ix * m2 + b2;
        if (!(Math.sqrt((ix-x)*(ix-x)+(iy-y)*(iy-y)) <= r / 2 && ix >= Math.min(x1, x2) && ix <= Math.max(x1, x2) && iy >= Math.min(y1, y2) && iy <= Math.max(y1, y2))) {
            return new int[]{-1, -1};
        }
        else {
            int ang = (int) (Math.atan(((double)(iy - y)) / (ix - x)) * (180.0 / Math.PI));
            if (x < ix) {
                ang += 180;
            }
            int ds = r / 2;
            return new int[]{(int)ix + (int) (Math.cos(ang * (Math.PI / 180.0)) * (ds)), (int)iy + (int) (Math.sin(ang * (Math.PI / 180.0)) * (ds))};
        }
    }
    
    public double cos(int ang) {
        return Math.cos(ang * (Math.PI / 180.0));
    }
    
    public double sin(int ang) {
        return Math.sin(ang * (Math.PI / 180.0));
    }
    
    public double cos(double ang) {
        return Math.cos(ang * (Math.PI / 180.0));
    }
    
    public double sin(double ang) {
        return Math.sin(ang * (Math.PI / 180.0));
    }
}