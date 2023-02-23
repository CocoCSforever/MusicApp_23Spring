package music;

import reactions.Gesture;
import reactions.Mass;
import reactions.Reaction;

import java.awt.*;

import static music.MusicEdi.PAGE;
import UC.UC;

public class Staff extends Mass {
    public Sys sys; //staff lives in Sys // null pointer Sys.addStaff Page, Page.addNewStaff
    public int iStaff; //index of staff in Sys
    public Staff.Fmt fmt;


    public Staff(int i, Staff.Fmt fmt){
        super("BACK");
        this.iStaff = i;
        this.fmt = fmt;

        addReaction(new Reaction("S-S") { // bar line
            @Override
            public int bid(Gesture g) {
                int x = g.vs.xM(), y1 = g.vs.yL(), y2 = g.vs.yM();
                if(x < PAGE.margin.left || x > PAGE.margin.right + UC.barToMarginSnap) {return UC.noBid;}
                int d = Math.abs(y1-Staff.this.yTop()) + Math.abs(y2-Staff.this.yBot());
                return (d < 30)? d+UC.barToMarginSnap: UC.noBid;
            }

            @Override
            public void act(Gesture g) {
                new Bar(Staff.this.sys, g.vs.xM());
            }
        });

        addReaction(new Reaction("S-S"){ //toggling barContinues

            @Override
            public int bid(Gesture g) {
                if(Staff.this.sys.iSys != 0){ return UC.noBid; }
                int y1 = g.vs.yL(), y2 = g.vs.yH();
                int iStaff = Staff.this.iStaff;
                if(iStaff == PAGE.sysFmt.size()-1){ return UC.noBid; }
                if(Math.abs(y1-Staff.this.yBot()) > 30){ return UC.noBid; }

                Staff nextStaff = sys.staffs.get(iStaff+1);
                if(Math.abs(y2-nextStaff.yTop()) > 30){ return UC.noBid; }

                return 10;
            }

            @Override
            public void act(Gesture g) {
                PAGE.sysFmt.get(Staff.this.iStaff).toggleBarContinues();
            }
        });


    }

    public int sysOff(){ return sys.fmt.staffOffset.get(iStaff); }
    public int yTop(){ return sys.yTop()+sysOff(); }
    public int yBot(){ return yTop()+ fmt.height(); }



    @Override
    public void show(Graphics g) {}


    //---------Staff.Fmt---------//
    public static class Fmt{
        public int nLines = 5, H = UC.defaultStaffH; // H for half line
        public boolean barContinues = false;

        public int height(){
            return 2 * H * (nLines-1);
        }
        public void toggleBarContinues(){ barContinues = !barContinues; }

        public void showAt(Graphics g, int y){
            int left = MusicEdi.PAGE.margin.left, right = MusicEdi.PAGE.margin.right;
            for(int i = 0; i < nLines; i++){
                g.drawLine(left, y + 2*H*i, right, y + 2*H*i);
            }
        }
    }
}
