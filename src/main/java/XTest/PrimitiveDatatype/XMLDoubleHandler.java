package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

public class XMLDoubleHandler extends PooledValueHandler implements XMLComparable, XMLNumeric, XMLSimple, XMLAtomic {
    XMLDoubleHandler() {
        officialTypeName = "xs:double";
    }

    @Override
    String getRandomValue() {
        Integer pre = GlobalRandom.getInstance().nextInt(100000);
        Integer last = GlobalRandom.getInstance().nextInt(10000);
        if(pre == 0) pre = 1;
        if(last == 0) last = 1;
        String result = pre + "." + last;
        Double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5)
            result = "-" + result;
        return result;
    }

    @Override
    public String mutateValue(String baseString) {
        double prob = 0.3;
        double prob2 = GlobalRandom.getInstance().nextDouble();
        String result = "";
        if(prob < 0.3) {
            Integer pre = GlobalRandom.getInstance().nextInt(100);
            Integer last = GlobalRandom.getInstance().nextInt(100);
            if(pre == 0) pre = 1;
            if(last == 0) last = 1;
            String delta = pre + "." + last;
            if(prob2 < 0.5)
                delta = "-" + delta;
            result = Double.toString(Double.parseDouble(baseString) + Double.parseDouble(delta));
        }
        else {
            Double value = Double.parseDouble(getRandomValueFromPool());
            if(prob2 < 0.5)
                value = -value;
            result = Double.toString(Double.parseDouble(baseString) + value);
        }
        return result;
    }
}
