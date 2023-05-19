package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

/**
 * A comparison interface for ValueHandlers.
 */
public interface XMLComparable {
    int POOL_ROUND = 3;

    /**
     *
     * @param baseValue
     * @param compareValue
     * @return Comparison result in XMLComparedResult type of baseValue and compareValue in String representation (
     * Actual value evaluated according to actual value handler).
     * baseValue and compareValue has to be the same type which implements compareTo.
     * e.g. If compareValue is greater than baseValue, returned is XMLComparedResult.GREATER.
     */
    XMLComparedResult compare(String baseValue, String compareValue);

    /**
     *
     * @param baseValue
     * @return A greater value than baseValue. There is no guarantees about the value except that it is
     * a greater value (if baseValue is already max value of a type the equal value might be returned). If
     * a null value is returned, there might be no easy means of finding a greater value.
     */
    String getDefiniteGreater(String baseValue);

    /**
     *
     * @param baseValue
     * @return A lesser value than baseValue. There is no guarantees about the value except that it is
     * a less value (if baseValue is already min value of a type the equal value might be returned). If
     * a null value is returned, there might be no easy means of finding a less value.
     */
    String getDefiniteLess(String baseValue);

    /**
     *
     * @param baseValue
     * @param compareValue
     * @return Comparison result of baseValue and compareValue in XMLComparedResult type.
     * baseValue and compareValue has to be the same type which implements compareTo.
     * e.g. If compareValue is greater than baseValue, returned is XMLComparedResult.GREATER.
     * @param <T>
     */
    default <T extends Comparable<T>> XMLComparedResult compareT(T baseValue, T compareValue) {
        Integer result = baseValue.compareTo(compareValue);
        if(result < 0) return XMLComparedResult.GREATER;
        if(result > 0) return XMLComparedResult.LESS;
        return XMLComparedResult.EQUAL;
    }

    /**
     * Fetch randomly from the pool(if value handler is instance of pooled value handler, else randomly)
     * for POOL_ROUND times. If any comparedValue when compared against baseValue through compareT(baseValue, compareValue)
     * produces the same result as given result parameter, that value is returned. If none, null is returned.
     * @param baseValue
     * @param result
     * @return compareValue fetched from the pool(if pooled, if not randomly) which compareT(baseValue, compareValue) == result.
     */
    default String getPooledWithResult(String baseValue, XMLComparedResult result) {
        for(int i = 0; i < POOL_ROUND; i ++) {
            String compareValue = ((ValueHandler) this).getValue(false);
            if(compareT(baseValue, compareValue) == result)
                return compareValue;
        }
        return null;
    }

    /**
     *
     * @param baseValue
     * @return A value which is likely to be greater than baseValue but no guarantees. Will not return null.
     */
    default String getGreater(String baseValue) {
        String result = getPooledWithResult(baseValue, XMLComparedResult.GREATER);
        if(result != null) return result;
        result = getDefiniteGreater(baseValue);
        if(result != null) return result;
        return ((ValueHandler) this).getValue(false);
    }

    /**
     *
     * @param baseValue
     * @return A value which is likely to be less than baseValue but no guarantees. Will not return null.
     */
    default String getLess(String baseValue) {
        String result = getPooledWithResult(baseValue, XMLComparedResult.LESS);
        if(result != null) return result;
        result = getDefiniteLess(baseValue);
        if(result != null) return result;
        return ((ValueHandler) this).getValue(false);
    }

    /**
     *
     * @param baseValue
     * @return A value which is likely to be greater than or equals to baseValue but no guarantees. Will not return null.
     */
    default String getGreaterOrEqual(String baseValue) {
        Double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.2) return baseValue;
        return getGreater(baseValue);
    }

    /**
     *
     * @param baseValue
     * @return A value which is likely to be less than or equals to baseValue but no guarantees. Will not return null.
     */
    default String getLessOrEqual(String baseValue) {
        Double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.2) return baseValue;
        return getLess(baseValue);
    }
}
