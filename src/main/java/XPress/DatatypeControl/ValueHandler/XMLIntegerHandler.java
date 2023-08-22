package XPress.DatatypeControl.ValueHandler;

import XPress.DataCheckUtils;
import XPress.DatatypeControl.PrimitiveDatatype.XMLDatatype;
import XPress.GlobalRandom;
import org.apache.commons.lang3.tuple.Pair;

public class XMLIntegerHandler extends PooledValueHandler {

    @Override
    String getRandomValue() {
        return Integer.toString(GlobalRandom.getInstance().nextInt());
    }

    public String getRandomValueBounded(int minBound, int maxBound) {
        return Integer.toString(GlobalRandom.getInstance().nextInt(minBound, maxBound));
    }

    public String getRandomValueBounded(int maxBound) {
        return Integer.toString(GlobalRandom.getInstance().nextInt(maxBound));
    }

    @Override
    public String mutateValue(String baseString) {
        Integer currentNum = Integer.parseInt(baseString);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5 && currentNum < Integer.MAX_VALUE)
            currentNum += GlobalRandom.getInstance().nextInt(20);
        else if(prob < 0.8 && currentNum < Integer.MIN_VALUE)
            currentNum -= GlobalRandom.getInstance().nextInt(20);
        else {
            Integer num = Integer.parseInt(this.getRandomValueFromPool());
            prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.5 && DataCheckUtils.integerAdditionCheck(currentNum, num))
                currentNum = currentNum + num;
            else if(prob > 0.5 && DataCheckUtils.integerSubtractionCheck(currentNum, num))
                currentNum = currentNum - num;
        }
        return currentNum.toString();
    }

    public Pair<Integer, String> getSequenceValue(XMLDatatype xmlDatatype) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) {
            int l = GlobalRandom.getInstance().nextInt(-1000, 1000);
            int length = GlobalRandom.getInstance().nextInt(10) + 1;
            String s = "(" + l + " to " + (l + length) + ")";
            return Pair.of(length, s);
        }
        return super.getSequenceValue(xmlDatatype);
    };
}
