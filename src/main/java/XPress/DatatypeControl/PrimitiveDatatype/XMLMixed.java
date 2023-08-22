package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.ValueHandler.XMLMixedHandler;

@Datatype
public class XMLMixed extends XMLDatatype {
    static XMLMixed instance;

    XMLMixed() {
        valueHandler = new XMLMixedHandler();
    }

    static public XMLMixed getInstance() {
        if(instance == null) {
            instance = new XMLMixed();
        }
        return instance;
    }
}
