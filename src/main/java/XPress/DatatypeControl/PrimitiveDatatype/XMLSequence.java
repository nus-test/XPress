package XPress.DatatypeControl.PrimitiveDatatype;

@Datatype
public class XML_Sequence extends XMLDatatype {
    static XML_Sequence instance;

    static public XML_Sequence getInstance() {
        if(instance == null) {
            instance = new XML_Sequence();
        }
        return instance;
    }
}
