package reactions;

import java.awt.*;
import java.io.*;
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
show(g): loop through list and set a loc of showBox to show every proto with margin and number(nBlend) */
public class Shape implements Serializable {
    public static DataBase DB = DataBase.load(); // keep association
    public static Shape DOT = DB.get("DOT");
    public static Collection<Shape> LIST = DB.values(); // auto updated/keep track of list of Shapes
    public static Shape bestMatch;

    public Prototype.List prototypes = new Prototype.List();
    public String name;

    public Shape(String name){this.name = name;}

    // can return null. check ink from a list of Shapes
    public static Shape recognize(Ink ink){
        // handle dots
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
    public static class Prototype extends Ink.Norm implements Serializable{
        public int nBlend = 1;
        public void blend(Ink.Norm norm){
            blend(norm, nBlend); nBlend++; // keep track of nBlend
        }
        //--------List of Prototype-------//
        public static class List extends ArrayList<Prototype> implements Serializable{
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

            public int hitProto(int x, int y){
//                Return index of hit prototype(-1: didn't hit anything)
                if(y < m || x < m || y > m + w){return -1;}
                int res = (x-m)/(m+w);
                return res < size()? res: -1; // whether click way out of proto list, return -1 instead of invalid index
            }

            public void train(Ink.Norm norm){
                if(bestDist(norm) < UC.noMatchDist){//found match -> blend
                    bestMatch.blend(norm);
                }else{
                    add(new Shape.Prototype());
                }
            }
        }
    }

    //-------DataBase---------//
    public static class DataBase extends HashMap<String, Shape> {
        public DataBase(){super(); String DOT = "DOT"; put(DOT, new Shape(DOT));}

        public static DataBase load(){
            //initialize DB
            DataBase res;
//            DataBase res = new HashMap<>();
//            res.put("DOT", new Shape("DOT"));
            // TODO: load prior saved DB
            try{
                System.out.println("Attempting DB load...");
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(UC.shapeDbFileName));
                res = (DataBase) ois.readObject();
                System.out.println("Successfully load - found " + res.keySet());
                ois.close();
            } catch (Exception e) {
                System.out.println("Load failed");
                System.out.println(e);
                res = new DataBase();
            }
            return res;
        }
        // serialize DB
        public static void save() {
            try{
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(UC.shapeDbFileName));
                oos.writeObject(DB);
                System.out.println("Saved " + UC.shapeDbFileName);
                oos.close();
            }catch(Exception e){
                System.out.println("Failed DB save");
                System.out.println(e);
            }
        }

        public Shape forcedGet(String name) {
            if (!DB.containsKey(name)) {DB.put(name, new Shape(name));} // make sure things were there}}
            return DB.get(name);
        }
        public void train(String name, Ink.Norm norm){
            if(isLegal(name)){
                forcedGet(name).prototypes.train(norm);
            }
        }
        public static boolean isLegal(String name){
            return !name.equals("") && !name.equals("DOT");
        }
    }
}
