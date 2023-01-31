package reactions;

import UC.I;

import java.awt.*;
import java.util.ArrayList;

public class Ink implements I.Show{

    @Override
    public void show(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(100, 100, 100, 100);
    }
    public static class List extends ArrayList<Ink> implements I.Show{
        @Override
        public void show(Graphics g) {for(Ink ink: this){ink.show(g);}}
    }
}
