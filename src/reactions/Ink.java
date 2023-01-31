package reactions;

import UC.I;
import UC.UC;
import graphicsLib.G;

import java.awt.*;
import java.util.ArrayList;

public class Ink extends G.PL implements I.Show{
    public static Buffer BUFFER = new Buffer();
    public Ink(){
        super(BUFFER.n);
        for(int i = 0; i < BUFFER.n; i++){
            points[i].set(BUFFER.points[i]);
        }
    }
    @Override
    public void show(Graphics g) {draw(g);}
    //--------Buffer---------//
    public static class Buffer extends G.PL implements I.Show, I.Area{
        public static final int MAX = UC.inkbuffermax; // max size of buffer
        public int n; // actual points in the buffer
        public G.BBox bBox = new G.BBox();
        private Buffer(){super(MAX);} // single term to read and deal with data
        public void add(int x, int y){if(n < MAX){points[n].set(x, y); bBox.add(x, y); n++;}}
        public void clear(){n = 0;}
        public void show(Graphics g){drawNDots(g, n); bBox.draw(g);}
        public boolean hit(int x, int y){return true;}
        public void dn(int x, int y){clear(); add(x, y); bBox.set(x, y);}
        public void drag(int x, int y){add(x, y);}
        public void up(int x, int y){}
    }
    //------List------//
    public static class List extends ArrayList<Ink> implements I.Show{
        @Override
        public void show(Graphics g) {for(Ink ink: this){ink.show(g);}}
    }
}
