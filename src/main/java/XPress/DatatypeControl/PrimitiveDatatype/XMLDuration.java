package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.XMLAtomic;
import XPress.DatatypeControl.ValueHandler.XMLDurationHandler;
import XPress.DatatypeControl.XMLSimple;

@Datatype
public class XMLDuration extends XMLDatatype implements XMLSimple, XMLAtomic {
    static XMLDuration instance;

    XMLDuration() {
        valueHandler = new XMLDurationHandler();
        officialTypeName = "xs:duration";
    }

    static public XMLDuration getInstance() {
        if(instance == null) {
            instance = new XMLDuration();
        }
        return instance;
    }
}
