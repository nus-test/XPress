package XTest.PrimitiveDatatypes;

import XTest.GlobalRandom;

import java.util.*;

import static XTest.XMLGeneration.DataGenerator.mutateProb;
import static XTest.XMLGeneration.DataGenerator.newProb;

public class XMLStringGenerator extends PooledValueGenerator {
    Set<Character> escapeSet = new HashSet<>(Arrays.asList('\'', '\"'));
    int valuePoolIdCnt = 0;
    Set<String> valuePool = new HashSet<>();
    Map<Integer, String> valuePoolLookUpMap = new HashMap<>();
    static int minLength = 0, maxLength = 60;

    XMLStringGenerator() {
    }

    String mutateValue(String value) {
        return value;
    }

    String getRandomValue() {
        int valueLength = GlobalRandom.getInstance().nextInt(maxLength);
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
