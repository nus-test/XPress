package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;
import XTest.Pair;
import XTest.XMLGeneration.ContextNode;

import java.util.ArrayList;
import java.util.List;

public class XMLSequenceHandler {
    public static String getRandomValue() {
        Pair pair = getRandomIntervalPair();
        return "(" + pair.x + " to " + pair.y + ")";
    }

    public static List<ContextNode> getRandomNodeValue() {
        Pair pair = getRandomIntervalPair();
        List<ContextNode> generatedList = new ArrayList<>();
        for(int i = pair.x; i <= pair.y; i ++) {
            ContextNode dummyNode = new ContextNode();
            dummyNode.dataType = XMLDatatype.INTEGER;
            dummyNode.dataContext = Integer.toString(i);
            generatedList.add(dummyNode);
        }
        return generatedList;
    }

    public static Pair getRandomIntervalPair() {
        int l = GlobalRandom.getInstance().nextInt();
        int r = GlobalRandom.getInstance().nextInt();
        if(r < l) {
            int t = r;
            r = l;
            l = t;
        }
        return new Pair(l, r);
    }
}
