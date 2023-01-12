package graphicsLib;

import java.awt.*;
import java.util.Random;

public class G {
    public static final Random RANDOM = new Random();

    public static int rnd(int max) {
        return RANDOM.nextInt(max); // [0,n)
    }
    public static Color rndColor(){return new Color(rnd(256), rnd(256), rnd(256));}
}
