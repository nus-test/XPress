package XPress.DatatypeControl;

import XPress.GlobalRandom;
import XPress.Pair;

import java.util.Arrays;
import java.util.List;

public class XMLSequenceHandler extends ValueHandler {
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

    @Override
    public String getValue() {
        //Pair pair = getRandomIntervalPair();
        return "()";
        //return "(" + pair.x + " to " + pair.y + ")";
    }

    @Override
    public String mutateValue(String baseString) {
        return null;
    }
}
