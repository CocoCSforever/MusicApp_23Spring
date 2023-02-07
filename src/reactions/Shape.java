package reactions;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import UC.UC;
import graphicsLib.G;
/*Shape: HashMap DB stores shape and its name, DOT(shape named “dot”), LIST(list of shapes from DB.values())
loadShapeDB(): set up-add “DOT” Shape
recognize(Ink ink): if ink.bBox < dotThreshold, ink is actually recognized as dot and return DOT.
else, loop through all list of proto and find the bestMatch Shape(in this Shape’s list of proto, has the minDist)*/

/*Shape.Prototype(Ink.Norm): blend(Ink.Norm norm): blend a norm to a certain proto
Updated Ink.Norm: added blend(Norm norm, int n)
Updated G.V: added blend(V v, int k)*/

/*Shape.Prototype.List(Ink.Norm.List): Prototype bestMatch;
bestDist(Ink.Norm norm): loop through the list of prototype, find the bestMatch proto and return the min dist;
G.VS showBox: show the proto;
show(g): loop through list and set a loc of showbox to show every proto with margin and number(nBlend) */
public class Shape {
    public static HashMap<String, Shape> DB = loadShapeDB(); // keep association
    public static Shape DOT = DB.get("DOT");
    public static Collection<Shape> LIST = DB.values(); // auto updated/keep track of list of Shapes
    public static Shape bestMatch;

    public Prototype.List prototypes = new Prototype.List();
    public String name;

    public Shape(String name){this.name = name;}

    public static HashMap<String, Shape> loadShapeDB(){
        HashMap<String, Shape> res = new HashMap<>();
        res.put("DOT", new Shape("DOT"));
        return res;
    }

    public static void saveShapeDB(){}
    public static Shape recognize(Ink ink){ // can return null
        if(ink.vs.size.x < UC.dotThreshold && ink.vs.size.y < UC.dotThreshold){return DOT;}
        bestMatch = null;
        int bestSoFar = UC.noMatchDist;
        for(Shape s: LIST){
            int d = s.prototypes.bestDist(ink.norm);
            if(d < bestSoFar){bestMatch = s; bestSoFar = d;}
        }
        return bestMatch;
    }

    //--------Prototype--------//
    // the average element blended together
    public static class Prototype extends Ink.Norm{
        public int nBlend = 1;
        public void blend(Ink.Norm norm){
            blend(norm, nBlend); nBlend++; // keep track of nBlend
        }
        //--------List of Prototype-------//
        public static class List extends ArrayList<Prototype>{
            public static Prototype bestMatch; // set by side effect by min/best dist

            public int bestDist(Ink.Norm norm){ // bestMatch doesn't have to exist
                bestMatch = null; // assume no match
                int res = UC.noMatchDist;
                for(Prototype p: this){
                    int d = p.dist(norm);
                    if(d < res){bestMatch = p; res = d;} //updated min dist
                }
                return res;
            }
            private static int m = 10, w = 60; // cell: margin, width
            private static G.VS showBox = new G.VS(m, m, w, w);
            public void show(Graphics g){
                g.setColor(Color.ORANGE);
                for(int i = 0; i < size(); i++){
                    Prototype p = get(i);
                    int x = m + i*(m+w);
                    showBox.loc.set(x, m);
                    p.drawAt(g, showBox);
                    g.drawString(""+p.nBlend, x, 20);
                }
            }
        }
    }

}