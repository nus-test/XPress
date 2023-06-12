package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;
import org.apache.commons.lang3.tuple.Pair;

public abstract class ValueHandler {
    public String officialTypeName;
    public abstract String getValue();

    public String getValue(boolean pooling) {
        return getValue();
    }

    public Pair<Integer, String> getSequenceValue(XMLDatatype xmlDatatype) {
        int length = GlobalRandom.getInstance().nextInt(5) + 1;
        return Pair.of(length, generateConstantSequence(length, xmlDatatype));
    };

    String generateSingleElementExpr(XMLDatatype xmlDatatype) {
        if(xmlDatatype.getValueHandler() != null) {
            String value = xmlDatatype.getValueHandler().getValue();
            return XMLDatatype.wrapExpression(value, xmlDatatype);
        }
        return null;
    }

    public String generateConstantSequence(int length, XMLDatatype xmlDatatype) {
        String stringBuild = "(";
        boolean start = true;
        for(int i = 0; i < length; i ++) {
            String element = generateSingleElementExpr(xmlDatatype);
            if(!start) stringBuild += ",";
            stringBuild += element;
            start = false;
        }
        stringBuild += ")";
        return stringBuild;
    }

    public abstract String mutateValue(String baseString);

    public void clear() {}

    /**
     *
     * @param baseString
     * @return The same as given parameter baseString.
     */
    public String getEqual(String baseString) {
        return baseString;
    }

    /**
     *
     * @param baseString
     * @return A likely different value than baseString. But no guarantees.
     */
    public String getNotEqual(String baseString) {
        String value = getValue(false);
        if(value.equals(baseString)) value = mutateValue(value);
        return value;
    }
}
