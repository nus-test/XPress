package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.ValueHandler.XMLNodeHandler;
import XPress.DatatypeControl.XMLSimple;

@Datatype
public class XML_Node extends XMLDatatype implements XMLSimple {
    static XML_Node instance;

    XML_Node() {
        valueHandler = new XMLNodeHandler();
        // Just to avoid null, maybe it is not an official name...
        officialTypeName = "xs:node";
    }

    static public XML_Node getInstance() {
        if(instance == null) {
            instance = new XML_Node();
        }
        return instance;
    }
}
