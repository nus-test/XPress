package XTest.PrimitiveDatatypes;

import XTest.GlobalRandom;

public class XMLIntegerGenerator extends PooledValueGenerator {
    @Override
    String getRandomValue() {
        return Integer.toString(GlobalRandom.getInstance().nextInt());
    }

    @Override
    String mutateValue(String baseString) {
        return null;
    }
}
