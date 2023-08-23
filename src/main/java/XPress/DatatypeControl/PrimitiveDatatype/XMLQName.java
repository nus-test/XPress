package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.ValueHandler.XMLStringHandler;
import XPress.DatatypeControl.XMLComparable;
import XPress.DatatypeControl.XMLSimple;

@Datatype
public class XMLQName extends XMLDatatype implements XMLSimple {
    static XMLQName instance;

    public XMLQName() {
        valueHandler = new XMLStringHandler();
        officialTypeName = "xs:QName";
    }

    static public XMLQName getInstance() {
        if(instance == null) {
            instance = new XMLQName();
        }
        return instance;
    }
}
