package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;
import XTest.GlobalSettings;

import java.util.*;

public enum XMLDatatype {
    INTEGER(1, new XMLIntegerHandler()),
    STRING(2, new XMLStringHandler()),
    BOOLEAN(3, new XMLBooleanHandler()),
    DOUBLE(4, new XMLDoubleHandler());

    int id;
    ValueHandler valueHandler;
    public static int typeCnt = 4;
    public static Map<Integer, XMLDatatype> datatypeIdMap = new HashMap<>();
    public static List<XMLDatatype> dataTypeList = new ArrayList<>();

    private XMLDatatype(int id, ValueHandler valueHandler) {
        this.id = id;
        this.valueHandler = valueHandler;
    }

    static {
        for(XMLDatatype xmlDatatype : XMLDatatype.values()) {
            dataTypeList.add(xmlDatatype);
        }
    }

    public static XMLDatatype getRandomDataType() {
        XMLDatatype xmlDatatype = GlobalRandom.getInstance().getRandomFromList(dataTypeList);
        if(xmlDatatype == BOOLEAN && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
            xmlDatatype = INTEGER;
        return xmlDatatype;
    }

    public ValueHandler getValueHandler() {
        return valueHandler;
    }
}
