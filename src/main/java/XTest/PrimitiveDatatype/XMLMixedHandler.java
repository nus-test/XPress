package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

public class XMLMixedHandler extends ValueHandler {
    @Override
    public String getValue() {
        int length = GlobalRandom.getInstance().nextInt(5);
        StringBuilder value = new StringBuilder("(");
        for(int i = 0; i < length; i ++) {
            double prob = GlobalRandom.getInstance().nextDouble();
            XMLDatatype xmlDatatype;
            if(prob < 0.25) xmlDatatype = XMLDatatype.INTEGER;
            else if(prob < 0.5) xmlDatatype = XMLDatatype.STRING;
            else if(prob < 0.75) xmlDatatype = XMLDatatype.DOUBLE;
            else xmlDatatype = XMLDatatype.BOOLEAN;
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
