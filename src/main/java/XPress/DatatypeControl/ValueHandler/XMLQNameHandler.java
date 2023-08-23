package XPress.DatatypeControl.ValueHandler;

public class XMLQNameHandler extends ValueHandler {
    @Override
    public String getValue() {
        return "foo";
    }

    @Override
    public String mutateValue(String baseString) {
        return "foo";
    }
}
