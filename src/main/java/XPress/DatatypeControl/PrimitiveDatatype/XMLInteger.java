package XPress.DatatypeControl.PrimitiveDatatype;

import XPress.DatatypeControl.*;
import XPress.DatatypeControl.ValueHandler.XMLIntegerHandler;
import XPress.GlobalRandom;

@Datatype
public class XML_Integer extends XMLDatatype implements XMLComparable, XMLSimple, XMLAtomic {
    static XML_Integer instance;

    XML_Integer() {
        valueHandler = new XMLIntegerHandler();
        officialTypeName = "xs:integer";
    }

    static public XML_Integer getInstance() {
        if(instance == null) {
            instance = new XML_Integer();
        }
        return instance;
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

    @Override
    public String getDefiniteGreater(String baseValue) {
        Integer value = parseInt(baseValue);
        if(value == Integer.MAX_VALUE) return baseValue;
        Integer valueAdd;
        if(value < 0)
            valueAdd = GlobalRandom.getInstance().nextInt(Integer.MAX_VALUE);
        else
            valueAdd = GlobalRandom.getInstance().nextInt(Integer.MAX_VALUE - value);
        return Integer.toString(value + valueAdd);
    }

    @Override
    public String getDefiniteLess(String baseValue) {
        Integer value = parseInt(baseValue);
        if(value == Integer.MIN_VALUE) return baseValue;
        Integer valueMinus;
        if(value >= 0)
            valueMinus = GlobalRandom.getInstance().nextInt(-(Integer.MIN_VALUE + 10));
        else valueMinus = GlobalRandom.getInstance().nextInt(value - Integer.MIN_VALUE);
        return Integer.toString(value - valueMinus);
    }

    @Override
    public XMLComparedResult compare(String baseValue, String compareValue) {
        Integer value1 = parseInt(baseValue);
        Integer value2 = parseInt(compareValue);
        return compareT(value1, value2);
    }
}
