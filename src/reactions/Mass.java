package reactions;

import UC.I;

import java.awt.*;
/* Mass == list of Reactions

 */
public abstract class Mass extends Reaction.List implements I.Show{
    //ancestor object for anyone gonna bid sth and have a reaction
    public Layer layer;
    public Mass(String layerName){ //if valid name: get layer from db, then add mass(I.Show) to layer(list of I.Show)
        layer = Layer.byName.get(layerName);
        if(layer != null){layer.add(this);}
        else{System.out.println("Bad layer name: " + layerName);}
    }
    public void delete(){
        // Mass as a list of reactions, for each r: disable r(remove it from a shape's list), and clear current list.
        clearAll();// clear all reactions from this list and byShape
        layer.remove(this); // remove self(I.Show) from layer(list of I.Show)
    }
}
