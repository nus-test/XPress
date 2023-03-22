package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.XMLGeneration.ContextNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XMLSequenceHandler {
    public static String getRandomValue() {
        Pair pair = getRandomIntervalPair();
        return "(" + pair.x + " to " + pair.y + ")";
    }

    public static List<Integer> getRandomNodeValue() {
        Pair pair = getRandomIntervalPair();
        List<Integer> generatedList = Arrays.asList(pair.x, pair.y);
        return generatedList;
    }

    public static Pair getRandomIntervalPair() {
        int l = GlobalRandom.getInstance().nextInt(10000);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) l = -l;
        int length = GlobalRandom.getInstance().nextInt(1000);
        return new Pair(l, l + length);
    }
}
