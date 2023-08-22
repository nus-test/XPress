package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.*;
import XPress.DatatypeControl.ValueHandler.XMLStringHandler;
import XPress.GlobalRandom;

@Datatype
public class XMLString extends XMLDatatype implements XMLComparable, XMLSimple, XMLAtomic {
    static XMLString instance;

    public XMLString() {
        valueHandler = new XMLStringHandler();
        officialTypeName = "xs:string";
    }

    static public XMLString getInstance() {
        if(instance == null) {
            instance = new XMLString();
        }
        return instance;
    }

    @Override
    public XMLComparedResult compare(String baseValue, String compareValue) {
        return compareT(baseValue, compareValue);
    }

    @Override
    public String getDefiniteGreater(String baseValue) {
        double prob = GlobalRandom.getInstance().nextDouble();
        String compareString = null;
        if(prob < 0.5) {
            Character c = ((XMLStringHandler) valueHandler).getRandomCharacter();
            if(compareT(c + baseValue, baseValue) == XMLComparedResult.GREATER)
                compareString = c + baseValue;
        }
        if(compareString == null) {
            Character c = ((XMLStringHandler) valueHandler).getRandomCharacter();
            compareString = baseValue + c;
        }
        return compareString;
    }

    @Override
    public String getDefiniteLess(String baseValue) {
        double prob = GlobalRandom.getInstance().nextDouble();
        String compareString = null;
        if(prob < 0.5 && baseValue.length() >= 1) {
            compareString = baseValue.substring(1);
            if(compareT(baseValue, compareString) != XMLComparedResult.LESS)
                compareString = null;
        }
        if(compareString == null && baseValue.length() >= 1) {
            compareString = baseValue.substring(0, baseValue.length());
        }
        if(compareString == null)
            compareString = "";
        return compareString;
    }
}
