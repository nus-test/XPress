package XTest.PrimitiveDatatype;

public class XMLNodeHandler extends ValueHandler implements XMLSimple {
    XMLNodeHandler() {
        // Just to avoid null, maybe it is not an official name...
        officialTypeName = "xs:node";
    }

    @Override
    public String getValue() {
        return "<A>2</A>";
    }

    @Override
    public String mutateValue(String baseString) {
        return null;
    }
}
