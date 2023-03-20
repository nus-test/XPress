package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

import java.util.HashMap;
import java.util.Map;

public enum XMLDatatype {
    INTEGER(1, new XMLIntegerHandler()),
    STRING(2, new XMLStringHandler()),
    BOOLEAN(3, new XMLBooleanHandler()),
    DOUBLE(4, new XMLDoubleHandler());

    int id;
    ValueHandler valueHandler;
    public static int typeCnt = 4;
    public static Map<Integer, XMLDatatype> datatypeIdMap = new HashMap<>();

    private XMLDatatype(int id, ValueHandler valueHandler) {
        this.id = id;
        this.valueHandler = valueHandler;
    }

    static {
        for(XMLDatatype xmlDatatype : XMLDatatype.values()) {
            datatypeIdMap.put(xmlDatatype.id, xmlDatatype);
        }
    }

    public static XMLDatatype getRandomDataType() {
        int dataTypeId = GlobalRandom.getInstance().nextInt(XMLDatatype.typeCnt) + 1;
        return XMLDatatype.datatypeIdMap.get(dataTypeId);
    }

    public ValueHandler getValueHandler() {
        return valueHandler;
    }
}
