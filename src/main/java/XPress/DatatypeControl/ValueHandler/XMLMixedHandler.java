package XPress.DatatypeControl;

import XPress.GlobalRandom;

public class XMLMixedHandler extends ValueHandler {
    @Override
    public String getValue() {
        int length = GlobalRandom.getInstance().nextInt(5);
        StringBuilder value = new StringBuilder("(");
        for(int i = 0; i < length; i ++) {
            double prob = GlobalRandom.getInstance().nextDouble();
            XMLDatatype_t xmlDatatype;
            if(prob < 0.25) xmlDatatype = XMLDatatype_t.INTEGER;
            else if(prob < 0.5) xmlDatatype = XMLDatatype_t.STRING;
            else if(prob < 0.75) xmlDatatype = XMLDatatype_t.DOUBLE;
            else xmlDatatype = XMLDatatype_t.BOOLEAN;
            value.append(XMLDatatype_t.wrapExpression(xmlDatatype.getValueHandler().getValue(), xmlDatatype));
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
