package XTest.PrimitiveDatatype;

import XTest.CommonUtils;
import XTest.DataCheckUtils;
import XTest.GlobalRandom;

public class XMLIntegerHandler extends PooledValueHandler implements XMLComparable, XMLNumeric, XMLSimple, XMLAtomic {

    XMLIntegerHandler() {
        officialTypeName = "xs:integer";
    }

    @Override
    String getRandomValue() {
        return Integer.toString(GlobalRandom.getInstance().nextInt());
    }

    public String getRandomValueBounded(int minBound, int maxBound) {
        return Integer.toString(GlobalRandom.getInstance().nextInt(minBound, maxBound));
    }

    public String getRandomValueBounded(int maxBound) {
        return Integer.toString(GlobalRandom.getInstance().nextInt(maxBound));
    }

    @Override
    public String mutateValue(String baseString) {
        Integer currentNum = Integer.parseInt(baseString);
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5 && currentNum < Integer.MAX_VALUE)
            currentNum += GlobalRandom.getInstance().nextInt(20);
        else if(prob < 0.8 && currentNum < Integer.MIN_VALUE)
            currentNum -= GlobalRandom.getInstance().nextInt(20);
        else {
            Integer num = Integer.parseInt(this.getRandomValueFromPool());
            prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.5 && DataCheckUtils.integerAdditionCheck(currentNum, num))
                currentNum = currentNum + num;
            else if(prob > 0.5 && DataCheckUtils.integerSubtractionCheck(currentNum, num))
                currentNum = currentNum - num;
        }
        return currentNum.toString();
    }

    public static Integer parseInt(String numString) throws NumberFormatException {
        try {
            return Integer.parseInt(numString);
        }
        catch (Exception exp) { }
        Integer sign = 1;
        Integer pre = 0, next = 0, e = 0, cnt = 0;
        Integer resultNum = 0;
        boolean flag = false;
        boolean eFlag = false;
        for(int i = 0; i < numString.length(); i ++) {
            if(i == 0 && numString.charAt(i) == '-')
                sign = -1;
            else if(!flag && numString.charAt(i) == '.') {
                flag = true;
            }
            else if(numString.charAt(i) == 'e' || numString.charAt(i) == 'E') {
                eFlag = true;

            }
            else {
                if(numString.charAt(i) > '9' || numString.charAt(i) < '0')
                    throw new NumberFormatException();
                int currentNum = numString.charAt(i) - '0';
                if(eFlag) {
                    e = e * 10 + currentNum;
                }
                else {
                    if (flag) {
                        next = next * 10 + currentNum;
                        cnt += 1;
                    }
                    else pre = pre * 10 + currentNum;
                }
            }
        }
        if(cnt > e) {
            throw new NumberFormatException();
        }
        e -= cnt;
        resultNum = pre * ((int) Math.pow(10, cnt)) + next;
        resultNum = resultNum * ((int) Math.pow(10, e));
        resultNum *= sign;
        return resultNum;
    }
}
