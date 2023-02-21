package reactions;

import UC.I;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
/* Layer is a list of items(I.Show), eg.Reactions
byName(Layer's database): HashMap<name, Layer> stores a mapping from name to layer.
ALL: stores all the layers?
 */
public class Layer extends ArrayList<I.Show> implements I.Show{
    public String name;
    public static HashMap<String, Layer> byName = new HashMap<>();
    public static Layer ALL = new Layer("ALL");
    // Note the sequence of the two static variables
    // if we first construct Layer, byName doesn't even exist
    // when we put this to byName
    public Layer(String name){
        this.name = name;
        if(!name.equals("ALL")){ ALL.add(this);}
        byName.put(name, this);
    }

    public static void nuke(){ // nukes layers before undo
        for(I.Show layer: ALL){
            ((Layer) layer).clear();
            // ALL are full of layers, fetch layers from ALL
            // each layer is just an arrayList, clear it
        }
    }

    public static void createAll(String[] a){
        for(String s: a){
            new Layer(s);
        }
    }

    @Override
    public void show(Graphics g) {
        for(I.Show item: this){
            item.show(g);
        }
    }
}
