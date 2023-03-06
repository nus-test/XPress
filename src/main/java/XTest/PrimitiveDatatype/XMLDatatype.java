package XTest.PrimitiveDatatype;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum XMLDatatype {
    INTEGER(1, new XMLIntegerGenerator()),
    STRING(2, new XMLStringGenerator()),
    BOOLEAN(3, new XMLBooleanGenerator());

    int id;
    ValueGenerator valueGenerator;
    public static int typeCnt = 2;
    public static Map<Integer, XMLDatatype> datatypeIdMap = new HashMap<>();
    public static Random random = new Random();

    private XMLDatatype(int id, ValueGenerator valueGenerator) {
        this.id = id;
        this.valueGenerator = valueGenerator;
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

    public ValueGenerator getValueGenerator() {
        return valueGenerator;
    }
}
