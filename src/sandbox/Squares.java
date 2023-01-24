package sandbox;

import graphicsLib.*;
import graphicsLib.Window;
import UC.*;


import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Squares extends Window implements ActionListener {
    //interface, short piece of codes, obey the contract
//    public static G.VS theVS = new G.VS(100, 100, 200, 300);
//    public static Color theColor = G.rndColor();
    public static Square.List theList = new Square.List();
    public static Square theSquare;
    public static boolean dragging = false; // drag to resize or remove, flag to show whether are dragging sth to sw else
    public static G.V mouseDelta = new G.V(0, 0);
    public static Timer timer;
    public static G.V pressedLoc = new G.V(0, 0);
    public static final int WIDTH = UC.MAIN_WINDOW_WIDTH, HEIGHT = UC.MAIN_WINDOW_HEIGHT;


    // Window requires constructor, so we have a red line if we don't have constructor
    // new Window();
    public Squares() {
        super("squares", WIDTH, HEIGHT);
        timer = new Timer(30, this); // how many milliseconds and who is the action listener
        timer.setInitialDelay(5000);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        G.clearBack(g);
//        theVS.fill(g, theColor);
        theList.draw(g);
    }

    public void mousePressed(MouseEvent me){
        /* if(theVS.hit(me.getX(), me.getY())){
            theColor = G.rndColor();
        }*/
        int x = me.getX(), y = me.getY();
        theSquare = theList.hit(x, y); // capture
        if(theSquare == null){dragging = false; theSquare = new Square(x, y); theList.add(theSquare);}
        else{dragging = true;
            theSquare.dv.set(0, 0); pressedLoc.set(x, y);
            mouseDelta.set(theSquare.loc.x-x, theSquare.loc.y-y);}

        // call os to paintComponent
        repaint();
        // swing: build menus, using labels, scrollbars. instead of how to use swing, the focus is on how to write swing.
    }

    public void mouseDragged(MouseEvent me){
        int x = me.getX(), y = me.getY();
        if(dragging){theSquare.move(x+mouseDelta.x, y+mouseDelta.y);}else{theSquare.resize(x, y);}
        repaint();
    }

    public void mouseReleased(MouseEvent me){
//     click on the square to stop it, and reset the dv by drag it and release it
        if(dragging){theSquare.dv.set(me.getX()-pressedLoc.x, me.getY()-pressedLoc.y);}
    }
    //pass everything on the command line
    public static void main(String[] args) {
        (PANEL = new Squares()).launch();
//      (PANEL = new Window("Window", 1000, 700)).launch();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    //------------Square------------//
    public static class Square extends G.VS implements I.Draw{
        public Color c = G.rndColor(); // if we only declare, it will default set to 0 or null(reference)
        public G.V dv = new G.V(G.rnd(20)-10, G.rnd(20)-10);
        public Square(int x, int y){super(x, y, 100, 100);}
        public void draw(Graphics g){fill(g, c); moveAndBounce();}
        public void resize(int x, int y){if(x > loc.x && y > loc.y){size.set(x-loc.x, y-loc.y);}}
        public void move(int x, int y){loc.set(x, y);}
        public void moveAndBounce(){
            loc.add(dv);
            if(xL() < 0 && dv.x < 0){dv.x = -dv.x;}
            if(xH() > WIDTH && dv.x > 0){dv.x = -dv.x;}
            if(yL() < 0 && dv.y < 0){dv.y = -dv.y;}
            if(yH() > HEIGHT && dv.y > 0){dv.y = -dv.y;}
        }
        //boolean hit already in VS class
        //public boolean hit(int x, int y){}

        //-----------List------------//
        public static class List extends ArrayList<Square> {
            // the only argument we passed in is g, c is in class square and should be referenced by s.c
            public void draw(Graphics g){for(Square s: this){s.draw(g);}}
            public Square hit(int x, int y) {
                Square res = null;
                for(Square s: this){if(s.hit(x, y)){res = s;}}
                return res;
            }

        }
    }
}
