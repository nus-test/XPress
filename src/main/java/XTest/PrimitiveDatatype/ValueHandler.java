package XTest.PrimitiveDatatype;

public abstract class ValueHandler {
    public abstract String getValue();

    public String getValue(boolean pooling) {
        return getValue();
    }

    public abstract String mutateValue(String baseString);
}
