package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;
import XTest.GlobalSettings;

import java.util.*;

public enum XMLDatatype {
    INTEGER(1, new XMLIntegerHandler()),
    STRING(2, new XMLStringHandler()),
    BOOLEAN(3, new XMLBooleanHandler()),
    DOUBLE(4, new XMLDoubleHandler()),
    DURATION(5, new XMLDurationHandler()),
    NODE(6, null);

    int id;
    ValueHandler valueHandler;
    public static List<XMLDatatype> dataTypeList = new ArrayList<>();

    private XMLDatatype(int id, ValueHandler valueHandler) {
        this.id = id;
        this.valueHandler = valueHandler;
    }

    static {
        for(XMLDatatype xmlDatatype : XMLDatatype.values()) {
            if(xmlDatatype == DURATION && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
                continue;
            if(xmlDatatype != NODE)
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
