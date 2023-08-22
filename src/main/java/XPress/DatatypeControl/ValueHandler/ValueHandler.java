package XPress.DatatypeControl;

import XPress.GlobalRandom;
import org.apache.commons.lang3.tuple.Pair;

public abstract class ValueHandler {
    public String officialTypeName;
    public abstract String getValue();

    public String getValue(boolean pooling) {
        return getValue();
    }


    /**
     * Get a sequence of constants with dataType *xmlDatatype*
     * @param xmlDatatype
     * @return
     */
    public Pair<Integer, String> getSequenceValue(XMLDatatype_t xmlDatatype) {
        int length = GlobalRandom.getInstance().nextInt(5) + 1;
        return Pair.of(length, generateConstantSequence(length, xmlDatatype));
    }

    /**
     * Generate a single element of *xmlDatatype* type, and return in its XPath expression string format.
     * @param xmlDatatype Datatype of element to generate.
     * @return
     */
    String generateSingleElementExpr(XMLDatatype_t xmlDatatype) {
        if(xmlDatatype.getValueHandler() != null) {
            String value = xmlDatatype.getValueHandler().getValue();
            return XMLDatatype_t.wrapExpression(value, xmlDatatype);
        }
        return null;
    }

    /**
     * Generate a sequence consisting of *length* *xmlDatatype* objects, return in string format
     * @param length Length of sequence to generate.
     * @param xmlDatatype Data type of sequence items.
     * @return
     */
    public String generateConstantSequence(int length, XMLDatatype_t xmlDatatype) {
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

    /**
     * Return a mutation of the given value.
     * @param baseString Value in string format.
     * @return
     */
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
