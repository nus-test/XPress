package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.XMLAtomic;
import XPress.DatatypeControl.ValueHandler.XMLBooleanHandler;
import XPress.DatatypeControl.XMLSimple;

@Datatype
public class XML_Boolean extends XMLDatatype implements XMLSimple, XMLAtomic {
    static XML_Boolean instance;

    XML_Boolean() {
        valueHandler = new XMLBooleanHandler();
        officialTypeName = "xs:boolean";
    }

    static public XML_Boolean getInstance() {
        if(instance == null) {
            instance = new XML_Boolean();
        }
        return instance;
    }
}
