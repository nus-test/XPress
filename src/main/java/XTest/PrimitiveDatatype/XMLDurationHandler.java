package XTest.PrimitiveDatatype;

import java.util.Arrays;
import java.util.List;

public class XMLDurationHandler extends PooledValueHandler {

    List<Character> yearMonthDurationPostfix = Arrays.asList('Y', 'M', 'D');
    List<Character> dayTimeDurationPostfix = Arrays.asList('H', 'M', 'S');

    @Override
    String getRandomValue() {
        return null;
    }

    @Override
    public String mutateValue(String baseString) {
        return null;
    }
}
