package XTest.PrimitiveDatatype;

public abstract class ValueHandler {
    String officialTypeName;
    public abstract String getValue();

    public String getValue(boolean pooling) {
        return getValue();
    }

    public abstract String mutateValue(String baseString);

    public void clear() {}
}
