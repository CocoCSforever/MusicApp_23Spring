package graphicsLib;

import java.awt.*;
import java.util.Random;

public class G {
    public static final Random RANDOM = new Random();

    public static int rnd(int max) {
        return RANDOM.nextInt(max); // [0,n)
    }
    public static Color rndColor(){return new Color(rnd(256), rnd(256), rnd(256));}
    public static void clearBack (Graphics g) {
        // clear background for windows, mac will automatically do it for users
        g.setColor(Color.white);
        g.fillRect(0, 0, 5000,5000);
    }
}
