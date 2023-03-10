package reactions;

import UC.I;
import graphicsLib.G;

import java.util.ArrayList;

//Gesture - Shape, G.VS
public class Gesture {
    // Ink has diff way(clockwise or not) but Shape doesn't recognize the way of
    // drawing but the final shape
    public static List UNDO = new List();
    public Shape shape;
    public G.VS vs;
    private Gesture(Shape shape, G.VS vs){
        this.shape = shape;
        this.vs = vs;
    }
    public static Gesture getNew(Ink ink){// can return null
        Shape s = Shape.recognize(ink);
        return (s == null)? null: new Gesture(s, ink.vs);
    }

    public void doGesture(){
        Reaction r = Reaction.best(this);
        if(r != null){ System.out.println("Reaction"); UNDO.add(this); r.act(this);}
    }

    public void redoGesture(){
        Reaction r = Reaction.best(this);
        if(r != null){ r.act(this);}
    }

    public static void unDo(){
        if(UNDO.size() == 0){ return; }
        UNDO.remove(UNDO.size()-1); // undo the last one
        Layer.nuke(); // eliminate all the masses
        Reaction.nuke(); // clear the byShape map and reload initial reactions
        UNDO.redo();
    }

    public static I.Area AREA = new I.Area(){
        public boolean hit(int x, int y){ return true;} // gesture area fill the whole screen
        public void dn(int x, int y){Ink.BUFFER.dn(x, y);}
        public void drag(int x, int y){Ink.BUFFER.drag(x, y);}
        public void up(int x, int y){
            //TODO add/clear?
            Ink.BUFFER.add(x, y);
            Ink ink = new Ink();
            Gesture gesture = Gesture.getNew(ink); // can fail if unrecognized
            Ink.BUFFER.clear();
            if(gesture != null) {
                System.out.println("Gesture: " + gesture.shape.name);
                if(gesture.shape.name.equals("N-N")){
                    unDo();
                }else{
                    gesture.doGesture();
                }
            }
//            if(gesture != null){
//                System.out.println("Gesture: " + gesture.shape.name);
//                Reaction r = Reaction.best(gesture); // can fail
//                if(r != null){
//                    System.out.println("Reaction");
//                    r.act(gesture);} // find a winner in the marketplace
//            }
        }
    }; // initialize interface doesn't work unless tell them

    //--------List--------//
    public static class List extends ArrayList<Gesture>{// remember everything we've done
        public void redo(){ for(Gesture g: this){ g.redoGesture();} }
    }
}
