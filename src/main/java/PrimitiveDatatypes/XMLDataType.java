package PrimitiveDatatypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum XMLDataType {
    INTEGER(1, null),
    STRING(2, new XMLStringGenerator());

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
}
