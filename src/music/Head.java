package music;

import UC.UC;
import reactions.Gesture;
import reactions.Mass;
import reactions.Reaction;

import java.awt.*;
import java.util.ArrayList;

public class Head extends Mass {// Note Head
    public Staff staff;
    public int x, line;
    public Time time;
    public Glyph forcedGlyph = null;
    public Stem stem = null;
    public boolean wrongSide = false;

    public Head(Staff staff, int x, int y){
        super("NOTE");
        this.staff = staff;
        this.time = staff.sys.getTime(x);
        time.heads.add(this);
        line = staff.lineOfY(y);
        System.out.println("line: " + line);

        addReaction(new Reaction("S-S") {//this will stem or un-stem heads
            @Override
            public int bid(Gesture g) {
                int x = g.vs.xM(), y1 = g.vs.yL(), y2 = g.vs.yH();
                int w = Head.this.W();
                int hy = Head.this.y();
                if(y1 > y || y2 < y){return UC.noBid;}
                int hl = Head.this.time.x, hr = hl + w;
                if (x < hl - w || x > hr + w){return UC.noBid;}
                if(x < hl + w / 2){return hl - x;}
                if(x > hr - w / 2){return x - hr;}
                return UC.noBid;
            }

            @Override
            public void act(Gesture g) {
                int x = g.vs.xM(), y1 = g.vs.yL(), y2 = g.vs.yH();
                Staff staff = Head.this.staff;
                Time t = Head.this.time;
                int w = Head.this.W();
                boolean up = x > (t.x + w / 2);
                if (Head.this.stem == null){
                    t.stemHeads(staff, up, y1, y2);
                }else{
                    t.unStemHeads(y1, y2);
                }
            }
        });
    }
    public void show(Graphics g){
        int H = staff.h();
        (forcedGlyph != null? forcedGlyph : normalGlyph()).showAt(g, H, x(), y());
    }
    public int W(){return 24 * staff.h() / 10;}
    public int y(){return staff.yLine(line);}
    public int x(){return time.x;}// this is a stub
    public Glyph normalGlyph(){return Glyph.HEAD_Q;}// this is a stub
    public void deleteHead(){time.heads.remove(this);}// this is a stub
    public void unStem(){
        if (stem != null){
            stem.heads.remove(this);
            if (stem.heads.size() == 0){stem.deleteStem();}
            stem = null;
            wrongSide = false;
        }
    }
    public void joinStem(Stem s){
        if (stem != null){unStem();}
        s.heads.add(this);
        stem = s;

    }

    //------------------------------------List-------------------------------------\\
    public static class List extends ArrayList<Head>{}
}
