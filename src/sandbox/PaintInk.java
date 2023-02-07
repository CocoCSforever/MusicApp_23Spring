package sandbox;

import graphicsLib.G;
import graphicsLib.Window;
import reactions.Ink;
import UC.UC;
import reactions.Shape;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
/* PaintInk keep track of all the prototypes with pList, updated when new ink created.
   when we created a ink, if find a matched proto to current ink.norm,
   blend it to the proto and assign the proto to ink.norm(changing different ink to its matched norm),
   else, create a new proto to represent cur ink and add it to the pList.*/
public class PaintInk extends Window {
    public static Ink.List inkList = new Ink.List();
    public static Shape.Prototype.List pList = new Shape.Prototype.List();

//    static{inkList.add(new Ink());}
    public PaintInk(){super("PaintInk", UC.MAIN_WINDOW_WIDTH, UC.MAIN_WINDOW_HEIGHT);}

    public void paintComponent(Graphics g){
        G.clearBack(g);
        g.setColor(Color.BLACK); inkList.show(g);
        g.setColor(Color.RED); Ink.BUFFER.show(g);
        pList.show(g);
//        int n = inkList.size() - 1;
//        if(n > 0){
//            int d = inkList.get(n).norm.dist(inkList.get(n-1).norm);
//            g.setColor((d < 1000000)? Color.BLACK: Color.RED);
//            g.drawString("Dist: " + d, 600, 30);
//        }
    }
    public void mousePressed(MouseEvent me){
        Ink.BUFFER.dn(me.getX(), me.getY());
        repaint();
    }
    public void mouseDragged(MouseEvent me){
        Ink.BUFFER.drag(me.getX(), me.getY());
        repaint();
    }
    public void mouseReleased(MouseEvent me){
        Ink ink = new Ink();
        Shape.Prototype proto;
        inkList.add(ink);
        if(pList.bestDist(ink.norm) < UC.noMatchDist){
//            Shape.Prototype.List.bestMatch.blend(ink.norm);
            proto = Shape.Prototype.List.bestMatch;
            proto.blend(ink.norm);
        }else{
//            pList.add(new Shape.Prototype());
            proto = new Shape.Prototype();
            pList.add(proto); //Norm()
        }
        ink.norm = proto;
        repaint();
    }

    public static void main(String[] args){(PANEL = new PaintInk()).launch();}
}
