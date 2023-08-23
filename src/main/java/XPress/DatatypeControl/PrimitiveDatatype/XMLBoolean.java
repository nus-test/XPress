package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.XMLAtomic;
import XPress.DatatypeControl.ValueHandler.XMLBooleanHandler;
import XPress.DatatypeControl.XMLSimple;

@Datatype
public class XMLBoolean extends XMLDatatype implements XMLSimple, XMLAtomic {
    static XMLBoolean instance;
    XMLBoolean() {
        valueHandler = new XMLBooleanHandler();
        officialTypeName = "xs:boolean";
    }

    static public XMLBoolean getInstance() {
        if(instance == null) {
            instance = new XMLBoolean();
        }
        return instance;
    }
}
