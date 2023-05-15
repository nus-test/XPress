package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;
import XTest.GlobalSettings;

import java.util.*;

public enum XMLDatatype {
    INTEGER(1, new XMLIntegerHandler(), true),
    STRING(2, new XMLStringHandler(), true),
    BOOLEAN(3, new XMLBooleanHandler(), true),
    DOUBLE(4, new XMLDoubleHandler(), true),
    DURATION(5, new XMLDurationHandler(), true),
    NODE(6, null, false),
    SEQUENCE(7, null, false),
    MIXED(8, null, false);

    int id;
    ValueHandler valueHandler;
    public static List<XMLDatatype> dataTypeList = new ArrayList<>();
    /**
     * Only value types of integrated would be generated directly: string, boolean, etc.
     * Non-integrated types could appear as castable types or be constructed through casting: QName, etc.
     * Not all data types are covered.
     * Also, only integrated types would be accepted as function node inputs (Non-integrated types are not implemented).
     */
    public boolean integrated;

    private XMLDatatype(int id, ValueHandler valueHandler, boolean integrated) {
        this.id = id;
        this.valueHandler = valueHandler;
        this.integrated = integrated;
    }

    static {
        for(XMLDatatype xmlDatatype : XMLDatatype.values()) {
            if(xmlDatatype == DURATION && GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
                continue;
            if(xmlDatatype != NODE && xmlDatatype != SEQUENCE && xmlDatatype != MIXED)
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

    public static String wrapExpression(String value, XMLDatatype xmlDatatype) {
        if(xmlDatatype == XMLDatatype.BOOLEAN)
            value += "()";
        else if(xmlDatatype == XMLDatatype.STRING)
            value = "\"" + value + "\"";
        else if(xmlDatatype == XMLDatatype.DURATION)
            value = "xs:duration('" + value + "')";
        return value;
    }

    /**
     *
     * @param from
     * @param to
     * @return True if data type "from" castable as "to"
     */
    public Boolean checkCastable(XMLDatatype from, XMLDatatype to) {
        return null;
    }

    /**
     *
     * @param datatype
     * @return Random integrated datatype which "datatype" is castable as, no requirements on "datatype":
     * could be non-integrated.
     */
    public XMLDatatype getRandomCastableIntegratedDatatype(XMLDatatype datatype) {
        return null;
    }
}
