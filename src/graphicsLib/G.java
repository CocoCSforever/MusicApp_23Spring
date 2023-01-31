package graphicsLib;

import java.awt.*;
import java.util.Random;

public class G {
    public static final Random RANDOM = new Random();

    public static int rnd(int max) {
        return RANDOM.nextInt(max); // [0,n)
    } // wrappers are to clear up functions
    public static Color rndColor(){return new Color(rnd(256), rnd(256), rnd(256));}
    public static void clearBack (Graphics g) {
        // clear background by fill the full screen to white for windows, mac will automatically do it for users
        g.setColor(Color.white);
        g.fillRect(0, 0, 5000,5000);
    }
    // nested class: have a file doing sth, need helper data structure(nested inside this class)
    // --------------V----------------//
    // Vector: two data points
    public static class V{
        public int x, y;
        public V(int x, int y){set(x, y);}
        public void set(int x, int y){this.x = x; this.y = y;}// need existing vector object in order to set values for nonstatic functions
        public void set(V v){x = v.x; y = v.y;}
        public void add(V v){x += v.x; y += v.y;}
    }
    // --------------VS----------------//
    // For rectangle: Vector and size to figure out where the corner is.
    public static class VS{
        public V loc, size;
        public VS(int x, int y, int w, int h){loc = new V(x, y); size = new V(w, h);}
        public void fill(Graphics g, Color c){g.setColor(c); g.fillRect(loc.x, loc.y, size.x, size.y);}
        public boolean hit(int x, int y){return loc.x <= x && x <= loc.x + size.x && loc.y <= y && y <= loc.y + size.y;}
        public int xL(){return loc.x;}
        public int xH(){return loc.x+size.x;}
        public int xM(){return loc.x + size.x/2;}
        public int yL(){return loc.y;}
        public int yH(){return loc.y+size.y;}
        public int yM(){return loc.y + size.y/2;}
    }
    // --------------LoHi----------------//
    // two numbers: lo and hi represent a range of numbers.
    public static class LoHi{
        public int lo, hi;
        public LoHi(int min, int max){lo = min; hi = max;}
        //clear the old range and set a new one
        public void set(int v){lo = v; hi = v;}
        // if v not in range, reset the range
        public void add(int v){if(v < lo){lo = v;} if(v > hi){hi = v;}}
        public int size(){return (hi-lo) > 0 ? hi-lo: 1;}
    }
    // --------------BBox----------------//
    // bounding box: min x,y and max x,y
    // horizontal and vertical bounds
    public static class BBox{
        public LoHi h, v;
        public BBox(){h = new LoHi(0, 0); v = new LoHi(0, 0);}
        public void set(int x, int y){h.set(x); v.set(y);}
        public void add(int x, int y){h.add(x); v.add(y);}
        public void add(V v){h.add(v.x); this.v.add(v.y);}// add a single Vector E
        public VS getNewVS(){return new VS(h.lo, v.lo, h.size(), v.size());}// turn bbox to regular VS
        public void draw(Graphics g){g.drawRect(h.lo, v.lo, h.size(), v.size());}
    }
    // --------------PL----------------//
    // Polyline: a whole long list of x,y coords
    public static class PL{
        public V[] points;
        //# of points reserved for PL
        public PL(int n){
            points = new V[n];
            for(int i = 0; i < n; i++){points[i] = new V(0, 0);}
        }
        public int size(){return points.length;}
        public void drawN(Graphics g, int n){
            for(int i = 1; i < n; i++){
                g.drawLine(points[i-1].x,points[i-1].y, points[i].x, points[i].y);
            }
        }
        public void draw(Graphics g){drawN(g, size());}
        public void drawNDots(Graphics g, int n){
            for(int i = 0; i < n; i++){
                g.drawOval(points[i].x - 1,points[i].y - 1, 3, 3);
            }
        }
        public void drawDots(Graphics g){drawNDots(g, size());}
    }
}
