package XTest.PrimitiveDatatype;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum XMLDatatype {
    INTEGER(1, new XMLIntegerHandler()),
    STRING(2, new XMLStringHandler()),
    BOOLEAN(3, new XMLBooleanHandler());

    int id;
    ValueHandler valueHandler;
    public static int typeCnt = 2;
    public static Map<Integer, XMLDatatype> datatypeIdMap = new HashMap<>();
    public static Random random = new Random();

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
        int dataTypeId = random.nextInt(XMLDatatype.typeCnt) + 1;
        return XMLDatatype.datatypeIdMap.get(dataTypeId);
    }

    public ValueHandler getValueHandler() {
        return valueHandler;
    }
}
