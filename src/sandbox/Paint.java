package sandbox;

import graphicsLib.G;
import graphicsLib.Window;

import java.awt.*;
import java.awt.event.MouseEvent; // import sub directoris
import java.util.ArrayList;

public class Paint extends Window {
    public static int click = 0;
    public static Path thePath;
    public static Path.List paths = new Path.List();
    // constructor has to use the name of the class
    Paint() {
        super("Paint", 1000, 700); // new Window("Paint", 1000, 700); with pixels
    }
    // call the paintComponent function up in the window class, which is a placeholder
    @Override
    public void paintComponent(Graphics g){
        // os creates a space for Graphics g, paintComponent to draw
        G.clearBack(g);
        g.setColor(G.rndColor()); // graphic object.function that's in Graphics (pass in the RED which is in the Color class)
        paths.draw(g);
//        g.setColor(G.rndColor());
//        g.fillRect(100, 100, 100, 100);
//        g.drawRect(300, 300, 100, 100);
//        g.setColor(G.rndColor());
//        g.drawOval(500, 500, 100, 200);
//        g.drawOval(700, 700, 200, 100);
//        g.drawLine(100, 100, 100, 600);
//        g.drawLine(100, 100, 600, 200);
//        int x = 300, y = 300;
//        String str = "Dude, this is really sweet!";
//        g.drawString(str, x, y); // text is baseline
//        g.setColor(G.rndColor());
//        g.fillRect(x, y, 2, 2);
//        // write a box for text
//        FontMetrics fm = g.getFontMetrics();
//        int a = fm.getAscent();
//        int h = fm.getHeight();
//        int w = fm.stringWidth(str);
//        g.drawRect(x, y-a, w, h);
    }

    // way to tell class descended from, want all same data/functions and add extra stuff in class
    // get behavior of class - want to use name but deal with it
    @Override
    public void mousePressed (MouseEvent me){
        // click++;
        click = 0;
        thePath = new Path();
        thePath.add(me.getPoint());
        paths.add(thePath);
        repaint();
    }

    @Override
    public void mouseDragged (MouseEvent me){
        click++;
        thePath.add(me.getPoint());
        repaint();
    }

    // put static at the bottom?
    // take an array of strings as parameters
    public static void main(String[] args){
        PANEL = new Paint();
        launch(); // launch a window
    }

    // ---------------------nested class-----------PATH------------
    public static class Path extends ArrayList<Point> {
        // default constructor

        public void draw(Graphics g){
            for (int i = 1; i < size(); i++) {
                Point p = get(i-1), n = get(i); // previous and next points
                g.drawLine(p.x, p.y, n.x, n.y);
            }
        }
        // ---------------------list----------------------
        public static class List extends ArrayList<Path> {
            public void draw(Graphics g){for(Path p: this){p.draw(g);}}
        }
    }

}
