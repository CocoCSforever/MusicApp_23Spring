package reactions;

import graphicsLib.Window;
import graphicsLib.G;

import java.awt.*;
import java.awt.event.KeyEvent;

/*ShapeTrainer as running window: paintComponent(draw curName and curState),
keyTyped(append char to current name until hit space or return), reset state and repaint.*/
// illegal shape: dots, empty string
public class ShapeTrainer extends Window {
    public static String UNKNOWN = " <- this name is currently unknown.";
    public static String ILLEGAL = " <- this name is NOT A LEGAL SHAPE NAME.";
    public static String KNOWN = " <- this is a known shape.";
    public static String curName = "";
    public static String curState = ILLEGAL;

    public ShapeTrainer(){
        super("ShapeTrainer", 1000, 700);
    }
    public void setState(){
        curState = (curName.equals("") || curName.equals("DOT"))? ILLEGAL: UNKNOWN;
    }
    public static void main(String[] args){
        (PANEL = new ShapeTrainer()).launch();
    }
    public void paintComponent(Graphics g){
        G.clearBack(g);
        g.setColor(Color.BLACK);
        g.drawString(curName, 600, 30);
        g.drawString(curState, 700, 30);
    }

    public void keyTyped(KeyEvent ke){// vs. keyPressed
        char c = ke.getKeyChar(); // get char typed
        System.out.println("Typed: " + c);
        curName = (c == ' ' || c == 0x0d || c == 0x0a)? "": curName+c; // SPACE/RETURN to clear
        setState();
        repaint();
    }
}






