package XTest.PrimitiveDatatypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum XMLDataType {
    INTEGER(1, new XMLIntegerGenerator()),
    STRING(2, new XMLStringGenerator()),
    BOOLEAN(3, new XMLBooleanGenerator());

    int id;
    ValueGenerator valueGenerator;
    public static int typeCnt = 2;
    public static Map<Integer, XMLDataType> dataTypeIdMap = new HashMap<>();
    public static Random random = new Random();

    private XMLDataType(int id, ValueGenerator valueGenerator) {
        this.id = id;
        this.valueGenerator = valueGenerator;
    }

    public static XMLDataType getRandomDataType() {
        int dataTypeId = random.nextInt(XMLDataType.typeCnt) + 1;
        return XMLDataType.dataTypeIdMap.get(dataTypeId);
    }

    public ValueGenerator getValueGenerator() {
        return valueGenerator;
    }
}
