package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

import java.util.*;

public class XMLStringHandler extends PooledValueHandler implements XMLComparable {
    Set<Character> escapeSet = new HashSet<>(Arrays.asList('\'', '\"', '<', '>', '/', '&'));
    int valuePoolIdCnt = 0;
    Set<String> valuePool = new HashSet<>();
    Map<Integer, String> valuePoolLookUpMap = new HashMap<>();
    static int minLength = 0, maxLength = 10;

    XMLStringHandler() {
    }

    public String mutateValue(String value) {
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
        int maxCharValue = 90;
        int minCharValue = 65;
        char c = 'a';
        while(!flag) {
            c = (char) (GlobalRandom.getInstance().nextInt(minCharValue, maxCharValue + 1));
            double prob = GlobalRandom.getInstance().nextDouble();
            if(prob < 0.5)
                c += 32;
            if(escapeSet.contains(c) == false)
                flag = true;
        }
        return c;
    }
}
