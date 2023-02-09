package reactions;

import UC.UC;
import graphicsLib.Window;
import graphicsLib.G;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*ShapeTrainer as running window: paintComponent(draw curName and curState),
keyTyped(append char to current name until hit space or return), reset state and repaint.*/
// illegal shape: dots, empty string
public class ShapeTrainer extends Window {
    public static String UNKNOWN = " <- this name is currently unknown.";
    public static String ILLEGAL = " <- this name is NOT A LEGAL SHAPE NAME.";
    public static String KNOWN = " <- this is a known shape.";
    public static String curName = "";
    public static String curState = ILLEGAL;
    public static Shape.Prototype.List plist = null;

    public ShapeTrainer(){
        super("ShapeTrainer", 1000, 700);
    }
    public void setState(){
        curState = (curName.equals("") || curName.equals("DOT"))? ILLEGAL: UNKNOWN;
        if(curState == UNKNOWN){
            if(Shape.DB.containsKey(curName)){
                curState = KNOWN;
                plist = Shape.DB.get(curName).prototypes;
            }else{plist = null;}
        }
    }
    public static void main(String[] args){
        (PANEL = new ShapeTrainer()).launch();
    }
    public void paintComponent(Graphics g){
        G.clearBack(g);
        g.setColor(Color.BLACK);
        g.drawString(curName, 600, 30);
        g.drawString(curState, 700, 30);

        g.setColor(Color.RED);
        Ink.BUFFER.show(g);
        if(plist != null){
            plist.show(g);
        }
    }

//    only keyTyped correctly interprets ASCII chars
    public void keyTyped(KeyEvent ke){// vs. keyPressed
        char c = ke.getKeyChar(); // get char typed
        System.out.println("Typed: " + c);
        // End symbols: ' ', '\r', '\n'
        curName = (c == ' ' || c == 0x0d || c == 0x0a)? "": curName+c; // SPACE/RETURN to clear
        if(c == 0x0d || c == 0x0a){Shape.saveShapeDB(UC.shapeDbFileName);}
        setState();
        repaint();
    }

    public void mousePressed(MouseEvent me){Ink.BUFFER.dn(me.getX(), me.getY());repaint();}
    public void mouseDragged(MouseEvent me){Ink.BUFFER.drag(me.getX(), me.getY());repaint();}
    public void mouseReleased(MouseEvent me){
        if(curState != ILLEGAL) {
            Ink ink = new Ink();
            Shape.Prototype proto;
            Shape recognized = Shape.recognize(ink);
//            if it's a dot, user not training
            if(recognized == Shape.DOT){removePrototype(me.getX(), me.getY());return;}
            if (plist == null) {
                Shape s = new Shape(curName);
                Shape.DB.put(curName, s);
                plist = s.prototypes;
            }
            if (plist.bestDist(ink.norm) < UC.noMatchDist) {
                //Found match
                proto = Shape.Prototype.List.bestMatch;
                proto.blend(ink.norm);
            } else {
                //No good match
                proto = new Shape.Prototype();
                plist.add(proto);
            }
            setState(); // possibly Unknown but known after updating DB
        }
        repaint();
    }

    public void removePrototype(int x, int y){
        if(plist == null){return;}
        int idx = plist.hitProto(x, y);
        if(idx >= 0){plist.remove(idx);}
        repaint();
    }

}






