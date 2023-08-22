package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.ValueHandler.XMLMixedHandler;
import XPress.DatatypeControl.ValueHandler.XMLSequenceHandler;

@Datatype
public class XMLSequence extends XMLDatatype {
    static XMLSequence instance;

    XMLSequence() {
        valueHandler = new XMLSequenceHandler();
    }

    static public XMLSequence getInstance() {
        if(instance == null) {
            instance = new XMLSequence();
        }
        return instance;
    }
}
