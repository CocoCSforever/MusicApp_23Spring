package music;

import reactions.Gesture;
import reactions.Mass;
import UC.UC;
import reactions.Reaction;

import java.awt.*;
import java.util.ArrayList;

import static music.MusicEdi.PAGE;

public class Page extends Mass {
    public Margin margin = new Margin();
    public Sys.Fmt sysFmt;
    public int sysGap, nSys;
    public ArrayList<Sys> sysList = new ArrayList<>();


    public Page(Sys.Fmt sysFmt){
        super("BACK");
        this.sysFmt = sysFmt;

        addReaction(new Reaction("E-E") { // add a new staff
            @Override
            public int bid(Gesture g) {
                int y = g.vs.yM();
                if(y <= PAGE.margin.top + sysFmt.height() + 30){
                    return UC.noBid;
                }
                return 50;
            }

            @Override
            public void act(Gesture g) {
                int y = g.vs.yM();
                PAGE.addNewStaff(y-PAGE.margin.top);
            }
        });

        addReaction(new Reaction("E-W") { // add a new sys
            @Override
            public int bid(Gesture g) {
                int y = g.vs.yM();
                int yBot = PAGE.sysTop(nSys);
                if(y <= yBot){ return UC.noBid;} // return noBid if drawn above existing staff
                return 50;
            }

            @Override
            public void act(Gesture g) {
                int y = g.vs.yM();
                if(PAGE.nSys == 1){
                    PAGE.sysGap = y - PAGE.sysTop(1);
                }
                PAGE.addNewSys();
            }
        });
    }

    public int sysTop(int iSys){
        return margin.top + iSys*(sysFmt.height() + sysGap);
    }

    public void addNewStaff(int yOff){
        Staff.Fmt sf = new Staff.Fmt();
        int n = sysFmt.size();
        sysFmt.add(sf);
        sysFmt.staffOffset.add(yOff);
        for(int i = 0; i < nSys; i++){
            sysList.get(i).addStaff(new Staff(n, sf)); // add staff to list and set sys to avoid null pointer
//            sysList.get(i).staffs.add(new Staff(n, sf));
        }
    }

    public void addNewSys(){
        sysList.add(new Sys(nSys, sysFmt));
        nSys++;
    }

    @Override
    public void show(Graphics g) {
        for(int i = 0; i < nSys; i++){
            g.setColor(Color.BLACK);
            sysFmt.showAt(g, sysTop(i));
        }
    }

    //-----------Margin-----------//
    public static class Margin{
        private static final int M = 50;
        public int top = M, left = M;
        public int bot = UC.MAIN_WINDOW_HEIGHT - M;
        public int right = UC.MAIN_WINDOW_WIDTH - M;


    }
}
