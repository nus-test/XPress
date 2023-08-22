package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.ValueHandler.XMLMixedHandler;

@Datatype
public class XML_Mixed extends XMLDatatype {
    static XML_Mixed instance;

    XML_Mixed() {
        valueHandler = new XMLMixedHandler();
    }

    static public XML_Mixed getInstance() {
        if(instance == null) {
            instance = new XML_Mixed();
        }
        return instance;
    }
}
