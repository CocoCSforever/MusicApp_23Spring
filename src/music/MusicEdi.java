package music;

import graphicsLib.G;
import graphicsLib.Window;
import reactions.Gesture;
import reactions.Ink;
import reactions.Layer;
import UC.UC;
import reactions.Reaction;

import java.awt.*;
import java.awt.event.MouseEvent;

import static UC.UC.MAIN_WINDOW_HEIGHT;

public class MusicEdi extends Window {
    public static Page PAGE;
    static{
        Layer.createAll("BACK FORE".split(" "));
//        new Layer("BACK");
//        new Layer("FORE");
    }
    public MusicEdi(){
        super("Music Editor", UC.MAIN_WINDOW_WIDTH,  UC.MAIN_WINDOW_HEIGHT);

        Reaction.initialReactions.addReaction(new Reaction("E-E") {
            @Override
            public int bid(Gesture g) {
                return 10;
            }

            @Override
            public void act(Gesture g) {
                int y = g.vs.yM();
                Sys.Fmt sf = new Sys.Fmt();
                PAGE = new Page(sf);
                PAGE.margin.top = y;
                PAGE.addNewSys();
                PAGE.addNewStaff(0);
                this.disable();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        G.clearBack(g);
        g.setColor(Color.GREEN);
        Ink.BUFFER.show(g);
        Layer.ALL.show(g);
    }

    @Override
    public void mousePressed(MouseEvent me){
        Gesture.AREA.dn(me.getX(), me.getY());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me){
        Gesture.AREA.drag(me.getX(), me.getY());
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me){
        Gesture.AREA.up(me.getX(), me.getY());
        repaint();
    }

    public static void main(String[] args){
        (PANEL = new MusicEdi()).launch();
    }
}
