package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.XMLAtomic;
import XPress.DatatypeControl.ValueHandler.XMLDurationHandler;
import XPress.DatatypeControl.XMLSimple;

@Datatype
public class XML_Duration extends XMLDatatype implements XMLSimple, XMLAtomic {
    static XML_Duration instance;

    XML_Duration() {
        valueHandler = new XMLDurationHandler();
        officialTypeName = "xs:duration";
    }

    static public XML_Duration getInstance() {
        if(instance == null) {
            instance = new XML_Duration();
        }
        return instance;
    }
}
