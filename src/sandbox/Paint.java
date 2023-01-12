package sandbox;

import graphicsLib.G;
import graphicsLib.Window;

import java.awt.*;

public class Paint extends Window {
    Paint() {
        super("Paint", 1000, 700); // new Window("Paint", 1000, 700); with pixels
    }
    // call the paintComponent function up in the window class, which is a placeholder
    @Override
    public void paintComponent(Graphics g){
        g.setColor(G.rndColor());
        g.fillRect(100, 100, 100, 100);
        g.drawRect(300, 300, 100, 100);
        g.setColor(G.rndColor());
        g.drawOval(500, 500, 100, 200);
        g.drawOval(700, 700, 200, 100);
        g.drawLine(100, 100, 100, 600);
        g.drawLine(100, 100, 600, 200);
        int x = 300, y = 300;
        String str = "Dude, this is really sweet!";
        g.drawString(str, x, y); // text is baseline
        g.setColor(G.rndColor());
        g.fillRect(x, y, 2, 2);
        // write a box for text
        FontMetrics fm = g.getFontMetrics();
        int a = fm.getAscent();
        int h = fm.getHeight();
        int w = fm.stringWidth(str);
        g.drawRect(x, y-a, w, h);
    }

    // put static at the bottom
    public static void main(String[] args){
        PANEL = new Paint();
        launch(); // launch a window
    }



}
