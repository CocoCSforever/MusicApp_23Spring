package reactions;

import UC.I;
import UC.UC;
import graphicsLib.G;

import java.awt.*;
import java.util.ArrayList;

public class Ink extends G.PL implements I.Show{
    // take out extends G.PL/temp, extends G.PL
    public static Buffer BUFFER = new Buffer();
    public static G.VS temp = new G.VS(100, 100, 100, 100); //temporary coord system
//    public static Buffer BUFFER = new Buffer();
//    public Norm norm;
//    public G.VS vs;
    public Ink(){
//        norm = new Norm();
//        vs = BUFFER.bBox.getNewVS();
        super(UC.normSampleSize);
        // make ink a subSample of BUFFER.points
        BUFFER.subSample(this);
        // transform ink to a normalized box and store it to ink
        // we'll draw whatever stored in ink: either normalized line or original line
        // G.PL.transform()-> G.V.setT()->this.set(v.tx(), v.ty());
        G.V.T.set(BUFFER.bBox, temp); transform();
        // transform temp back to ink and store it to ink
        G.V.T.set(temp, BUFFER.bBox.getNewVS()); transform();
//        super(BUFFER.n);
//        for(int i = 0; i < BUFFER.n; i++){
//            points[i].set(BUFFER.points[i]);
//        }
    }
//    @Override
//    public void show(Graphics g) {g.setColor(UC.defaultInkColor); norm.drawAt(g, vs);}
    @Override
    public void show(Graphics g) {draw(g);}
    //--------Norm----------//
    //compare one thing to another
//    public static class Norm extends G.PL{
//        public static final int N = UC.normSampleSize, MAX = UC.normCoordMax;
//        public static final G.VS normCoordSystem = new G.VS(0, 0, MAX, MAX);
//
//        public Norm() {
//            super(N);
//            BUFFER.subSample(this);
//            G.V.T.set(BUFFER.bBox, normCoordSystem);
//            transform();
//        }
//        public void drawAt(Graphics g, G.VS vs){
//            G.V.T.set(normCoordSystem, vs);
//            for(int i = 1; i < N; i++){
//                g.drawLine(points[i-1].tx(), points[i-1].ty(), points[i].tx(), points[i].ty());
//            }
//        }
//        public int dist(Norm n){
//            int res = 0;
//            for(int i = 0; i < N; i++){
//                int dx = points[i].x - n.points[i].x;
//                int dy = points[i].y - n.points[i].y;
//                res += dx * dx + dy * dy;
//            }
//            return res;
//        }
//    }
    //--------Buffer---------//
    public static class Buffer extends G.PL implements I.Show, I.Area{
        public static final int MAX = UC.inkBufferMax; // max size of buffer
        public int n; // # of actual points in the buffer
        public G.BBox bBox = new G.BBox();
        private Buffer(){super(MAX);} // single term to read and deal with data
        public void add(int x, int y){if(n < MAX){points[n].set(x, y); bBox.add(x, y); n++;}}
        public void clear(){n = 0;}
        public void show(Graphics g){drawNDots(g, n); bBox.draw(g);}
        public boolean hit(int x, int y){return true;}
        public void dn(int x, int y){clear(); add(x, y); bBox.set(x, y);}
        public void drag(int x, int y){add(x, y);}
        public void up(int x, int y){}
        public void subSample(G.PL pl){
            int K = pl.size();
            for(int i = 0; i < K; i++){
                int j = i * (n-1)/(K-1);
                pl.points[i].set(points[j]); // copy value from buffer to the subSample pl
            }
        }
    }
    //------List------//
    public static class List extends ArrayList<Ink> implements I.Show{
        @Override
        public void show(Graphics g) {for(Ink ink: this){ink.show(g);}}
    }
}
