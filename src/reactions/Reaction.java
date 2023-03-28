package reactions;

import UC.I;
import UC.UC;

import java.util.*;

/* Reaction.shape: related to a shape
byShape = HashMap<Shape, List<Reaction>> stores shape and its corresponding list of Reactions
add/remove reaction == remove this(reaction) from the list of Reactions of a certain shape(this.shape)
 */
//abstract class can implement interface without override the method
public abstract class Reaction implements I.React{
    // bookkeeping for every item who wants to react
    private static Map byShape = new Map();
    public static List initialReactions = new List(); // used by undo to restart
    public Shape shape;
    public Reaction(String shapeName){
        shape = Shape.DB.get(shapeName);
        if(shape == null){ System.out.println("WTF? Shpae DB does not contain " + shapeName);}
    }

    public static void nuke(){
        byShape.clear(); // wipes out everything in the map
        initialReactions.enable();
    }

    // take the byShape list and look to see whether self already in the list
    public void enable(){
        List list = byShape.getList(shape);
        if(!list.contains(this)){list.add(this);}
    }

    public void disable(){ // remove this reaction from its shape's list
        List list = byShape.getList(shape);
        list.remove(this);
    }

    public static Reaction best(Gesture g){// return the best reaction or null
        return byShape.getList(g.shape).loBid(g);
    }

    //-------List---------//
    public static class List extends ArrayList<Reaction>{
        public void addReaction(Reaction r){ add(r); r.enable();}
        public void removeReaction(Reaction r){ remove(r); r.disable();}
        public void clearAll(){
            for(Reaction r: this){ r.disable();}
            this.clear();
        }
        public Reaction loBid(Gesture g){// can return null
            // find the lowest bit on gesture g in the marketplace
            Reaction res = null;
            int bestSoFar = UC.noBid;
            for(Reaction r: this){
                int b = r.bid(g);
                if(b < bestSoFar){ bestSoFar = b; res = r;}
            }
            return res;
        }
        public void enable(){
            for(Reaction r: this){ r.enable();}
        }
    }
    //-------Map--------//
    public static class Map extends HashMap<Shape,List>{
        //Shape-> ArrayList<Reaction>
        public List getList(Shape s){
            //forceGet a list
            List res = get(s);
            if(res == null){res = new List(); put(s, res);}
            return res;
        }
    }
}
