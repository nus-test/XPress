package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.ValueHandler.XMLNodeHandler;
import XPress.DatatypeControl.XMLSimple;

@Datatype
public class XMLNode extends XMLDatatype implements XMLSimple {
    static XMLNode instance;

    XMLNode() {
        valueHandler = new XMLNodeHandler();
        // Just to avoid null, maybe it is not an official name...
        officialTypeName = "xs:node";
    }

    static public XMLNode getInstance() {
        if(instance == null) {
            instance = new XMLNode();
        }
        return instance;
    }
}
