package sandbox;

import graphicsLib.*;
import graphicsLib.Window;


import java.awt.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Squares extends Window {
    public static G.VS theVS = new G.VS(100, 100, 200, 300);
    public static Color theColor = G.rndColor();
    public static Square.List theList = new Square.List();

    // Window requires constructor, so we have a red line if we don't have constructor
    public Squares() {super("squares", 1000, 700);// new Window();
    }

    @Override
    public void paintComponent(Graphics g){
        G.clearBack(g);
        theVS.fill(g, theColor);
        theList.draw(g);
    }

    public void mousePressed(MouseEvent me){
        /* if(theVS.hit(me.getX(), me.getY())){
            theColor = G.rndColor();
        }*/
        theList.add(new Square(me.getX(), me.getY()));
        // call os to paintComponent
        repaint();
        // swing: build menus, using labels, scrollbars. instead of how to use swing, the focus is on how to write swing.
    }

    //pass everything on the command line
    public static void main(String[] args) {
        (PANEL = new Squares()).launch();
//      (PANEL = new Window("Window", 1000, 700)).launch();
    }
    //------------Square------------//
    public static class Square extends G.VS{
        public Color c = G.rndColor(); // if we only declare, it will default set to 0 or null(reference)
        public Square(int x, int y){super(x, y, 100, 100);}

        //-----------List------------//
        public static class List extends ArrayList<Square> {
            // the only argument we passed in is g, c is in class square and should be referenced by s.c
            public void draw(Graphics g){for(Square s: this){s.fill(g, s.c);}
        }

        }
    }
}
