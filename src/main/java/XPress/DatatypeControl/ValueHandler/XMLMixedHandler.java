package XPress.DatatypeControl.ValueHandler;

import XPress.DatatypeControl.PrimitiveDatatype.*;
import XPress.GlobalRandom;

public class XMLMixedHandler extends ValueHandler {
    @Override
    public String getValue() {
        int length = GlobalRandom.getInstance().nextInt(5);
        StringBuilder value = new StringBuilder("(");
        for(int i = 0; i < length; i ++) {
            double prob = GlobalRandom.getInstance().nextDouble();
            XMLDatatype xmlDatatype;
            if(prob < 0.25) xmlDatatype = XMLInteger.getInstance();
            else if(prob < 0.5) xmlDatatype = XMLString.getInstance();
            else if(prob < 0.75) xmlDatatype = XMLDouble.getInstance();
            else xmlDatatype = XMLBoolean.getInstance();
            value.append(XMLDatatype.wrapExpression(xmlDatatype.getValueHandler().getValue(), xmlDatatype));
            if(i != length - 1) value.append(",");
        }
        value.append(")");
        return value.toString();
    }

    @Override
    public String mutateValue(String baseString) {
        return null;
    }
}
