package sandbox;

import graphicsLib.G;
import graphicsLib.Window;
import reactions.Ink;
import UC.UC;

import java.awt.*;

public class PaintInk extends Window {
    public static Ink.List inkList = new Ink.List();

    static{inkList.add(new Ink());}
    public PaintInk(){super("PaintInk", UC.MAIN_WINDOW_WIDTH, UC.MAIN_WINDOW_HEIGHT);}

    public void paintComponent(Graphics g){
        G.clearBack(g);
        inkList.show(g);
    }
    public static void main(String[] args){(PANEL = new PaintInk()).launch();}
}
