package XPress.PrimitiveDatatype;

import XPress.GlobalRandom;

public class XMLBooleanHandler extends ValueHandler implements XMLSimple, XMLAtomic {
    XMLBooleanHandler() {
        officialTypeName = "xs:boolean";
    }

    @Override
    public String getValue() {
        double prob1 = GlobalRandom.getInstance().nextDouble();
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
