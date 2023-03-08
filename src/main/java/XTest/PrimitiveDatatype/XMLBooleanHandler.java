package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

public class XMLBooleanHandler extends ValueHandler {

    @Override
    public String getValue() {
        double prob1 = GlobalRandom.getInstance().nextDouble();
        double prob2 = GlobalRandom.getInstance().nextDouble();
        String value;
        if(prob1 < 0.5)
            value = (prob2 < 0.5) ? "1" : "true";
        else
            value = (prob2 < 0.5) ? "0" : "false";
        return value;
    }
}
