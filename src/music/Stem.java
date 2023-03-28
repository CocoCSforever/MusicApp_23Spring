package music;

import java.awt.*;

public class Stem extends Duration{
    public Staff staff;
    public Head.List heads = new Head.List();
    public boolean isUp = true;
    public Stem(Staff staff, boolean up){
        this.staff = staff;
        isUp = up;
    }
    @Override
    public void show(Graphics g) {
     if (nFlag >= -1 && heads.size() > 0){
         int h = staff.h(), yH = yFirstHead(), yB = yBeamEnd();
         g.drawLine(x(), yH, x(), yB);
     }
    }
    public Head firstHead(){return heads.get(isUp ? heads.size() - 1 : 0);}
    public Head lastHead(){return heads.get(isUp ? 0 : heads.size() - 1);}
    public int yFirstHead(){return firstHead().y();}
    public int x(){Head h = firstHead(); return h.time.x + (isUp ? h.W(): 0);}
    public int yBeamEnd(){
        Head h = lastHead();
        int line = h.line;
        line += isUp? - 7 : 7; // default is one octave from last head on stem
        int flagInc = nFlag > 2? 2 * (nFlag - 2) : 0;// if more than 2 flags, adjust beamEnd.
        line += isUp? - flagInc : flagInc;
        if(isUp && line > 4 || (!isUp && line < 4)){line = 4;} // meet center line
        return h.staff.yLine(line);
    }
    public void deleteStem(){ deleteMass();}
    public void setWrongSides(){}// this is a stub
}

