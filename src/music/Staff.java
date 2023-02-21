package music;

import reactions.Mass;

import java.awt.*;

import static music.MusicEdi.PAGE;

public class Staff extends Mass {
    public Sys sys; //staff lives in Sys
    public int iStaff; //index of staff in Sys
    public Staff.Fmt fmt;


    public Staff(int i, Staff.Fmt fmt){
        super("BACK");
        this.iStaff = i;
        this.fmt = fmt;
    }

    @Override
    public void show(Graphics g) {}


    //---------Staff.Fmt---------//
    public static class Fmt{
        public int nLines = 5, H = 8; // H for half line

        public int height(){
            return 2 * H * (nLines-1);
        }

        public void showAt(Graphics g, int y){
            int left = MusicEdi.PAGE.margin.left, right = MusicEdi.PAGE.margin.right;
            for(int i = 0; i < nLines; i++){
                g.drawLine(left, y + 2*H*i, right, y + 2*H*i);
            }
        }
    }
}
