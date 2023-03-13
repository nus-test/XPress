package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

public class XMLBooleanHandler extends ValueHandler {

    @Override
    public String getValue() {
        double prob1 = GlobalRandom.getInstance().nextDouble();
        double prob2 = GlobalRandom.getInstance().nextDouble();
        String value;
        if(prob1 < 0.5)
            value = "true";
        else
            value = "false";
        return value;
    }

    @Override
    public String mutateValue(String baseString) {
        return getValue();
    }
}
