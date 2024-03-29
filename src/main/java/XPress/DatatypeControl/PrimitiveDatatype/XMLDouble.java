package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.*;
import XPress.DatatypeControl.ValueHandler.XMLDoubleHandler;

@Datatype
public class XMLDouble extends XMLNumeric implements XMLComparable, XMLSimple, XMLAtomic {
    static XMLDouble instance;

    XMLDouble() {
        valueHandler = new XMLDoubleHandler();
        officialTypeName = "xs:double";
    }

    static public XMLDouble getInstance() {
        if(instance == null) {
            instance = new XMLDouble();
        }
        return instance;
    }

    @Override
    public XMLComparedResult compare(String baseValue, String compareValue) {
        Double value1 = Double.parseDouble(baseValue);
        Double value2 = Double.parseDouble(compareValue);
        return compareT(value1, value2);
    }

    @Override
    public String getDefiniteGreater(String baseValue) {
        Double value = Double.parseDouble(baseValue);
        Double valueAdd = Double.parseDouble(valueHandler.getValue(false));
        return Double.toString(value + valueAdd);
    }

    @Override
    public String getDefiniteLess(String baseValue) {
        Double value = Double.parseDouble(baseValue);
        Double valueMinus = Double.parseDouble(valueHandler.getValue(false));
        return Double.toString(value - valueMinus);
    }
}
