package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

public class XMLIntegerHandler extends PooledValueHandler implements XMLComparable {
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
        return null;
    }
}
