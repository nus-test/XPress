package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;
import org.basex.query.value.item.Int;

import java.util.Arrays;
import java.util.List;

public class XMLDurationHandler extends PooledValueHandler implements XMLSimple, XMLAtomic {

    XMLDurationHandler() {
        officialTypeName = "xs:duration";
    }

    List<Character> yearMonthDurationPostfix = Arrays.asList('Y', 'M', 'D');
    List<Character> dayTimeDurationPostfix = Arrays.asList('H', 'M', 'S');

    @Override
    String getRandomValue() {
        StringBuilder duration = new StringBuilder("P");
        for(Character yearMonthPostfix : yearMonthDurationPostfix) {
            duration.append(GlobalRandom.getInstance().nextInt(10000)).append(yearMonthPostfix);
        }
        duration.append("T");
        for(Character dayTimePostfix : dayTimeDurationPostfix) {
            duration.append(GlobalRandom.getInstance().nextInt(10000)).append(dayTimePostfix);
        }
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) duration.insert(0, "-");
        return duration.toString();
    }

    @Override
    public String mutateValue(String baseString) {
        return baseString;
    }
}
