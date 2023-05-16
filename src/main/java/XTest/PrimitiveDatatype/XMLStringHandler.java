package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

import java.util.*;

public class XMLStringHandler extends PooledValueHandler implements XMLComparable, XMLSimple, XMLAtomic {
    Set<Character> escapeSet = new HashSet<>(Arrays.asList('\'', '\"', '<', '>', '/', '&'));
    int valuePoolIdCnt = 0;
    Set<String> valuePool = new HashSet<>();
    Map<Integer, String> valuePoolLookUpMap = new HashMap<>();
    static int minLength = 0, maxLength = 10;

    XMLStringHandler() {
        officialTypeName = "xs:string";
    }

    public String mutateValue(String value) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.3) {
            int randomStringLength = GlobalRandom.getInstance().nextInt(3) + 1;
            String randomString = getRandomValue(randomStringLength);
            prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.5) value += randomString;
            else value = randomString + value;
        }
        else {
            prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.5) {
                String randomString = getRandomValueFromPool();
                prob = GlobalRandom.getInstance().nextDouble();
                if(prob < 0.5) value += randomString;
                else value = randomString + value;
            }
            else {
                if(value.length() != 0) {
                    int index = GlobalRandom.getInstance().nextInt(value.length());
                    if (index == 0)
                        value = getRandomCharacter() + value.substring(index);
                    else if (index == value.length() - 1)
                        value = value.substring(0, index) + getRandomCharacter();
                    else
                        value = value.substring(0, index) + getRandomCharacter() + value.substring(index);
                }
                else value = getRandomCharacter().toString();
            }
        }
        return value;
    }

    String getRandomValue() {
        int valueLength = GlobalRandom.getInstance().nextInt(maxLength) + 1;
        String generatedString = getRandomValue(valueLength);
        return generatedString;
    }

    public String getRandomValue(int valueLength) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < valueLength; i ++) {
            stringBuilder.append(getRandomCharacter());
        }
        String generatedString = stringBuilder.toString();
        return generatedString;
    }

    Character getRandomCharacter() {
        boolean flag = false;
        int maxCharValue = 126;
        int minCharValue = 32;
        char c = 'a';
        while(!flag) {
            c = (char) (GlobalRandom.getInstance().nextInt(minCharValue, maxCharValue + 1));
            if(escapeSet.contains(c) == false)
                flag = true;
        }
        return c;
    }
}
