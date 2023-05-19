package XTest.PrimitiveDatatype;

public abstract class ValueHandler {
    public String officialTypeName;
    public abstract String getValue();

    public String getValue(boolean pooling) {
        return getValue();
    }

    public abstract String mutateValue(String baseString);

    public void clear() {}

    /**
     *
     * @param baseString
     * @return The same as given parameter baseString.
     */
    public String getEqual(String baseString) {
        return baseString;
    }

    /**
     *
     * @param baseString
     * @return A likely different value than baseString. But no guarantees.
     */
    public String getNotEqual(String baseString) {
        String value = getValue(false);
        if(value.equals(baseString)) value = mutateValue(value);
        return value;
    }
}
