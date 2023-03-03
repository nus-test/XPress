package PrimitiveDatatypes;

import java.util.*;

import static XMLGeneration.DataGenerator.mutateProb;
import static XMLGeneration.DataGenerator.newProb;

public class XMLStringGenerator implements ValueGenerator {
    Set<Character> escapeSet = new HashSet<>(Arrays.asList('\'', '\"'));
    int valuePoolIdCnt = 0;
    Set<String> valuePool = new HashSet<>();
    Map<Integer, String> valuePoolLookUpMap = new HashMap<>();
    static int minLength = 0, maxLength = 60;

    public String getValue() {
        boolean choice = Math.random() < newProb;
        String generatedString;
        if(choice) {
            // Generate new random value and add to pool;
            generatedString = getRandomValue();
            addValueToPool(getRandomValue());
        }
        else {
            // Get random value from original pool or from mutation
            boolean mutateChoice = Math.random() < mutateProb;
            String baseString = getRandomValueFromPool();
            if(mutateChoice) {
                generatedString = mutateValue(baseString);
                addValueToPool(generatedString);
            }
            else generatedString = baseString;
        }
        return generatedString;
    }

    String mutateValue(String value) {
        return value;
    }

    String getRandomValueFromPool() {
        if(valuePoolIdCnt == 0) {
            return getRandomValue();
        }
        Random random = new Random();
        int id = random.nextInt(valuePoolIdCnt);
        return valuePoolLookUpMap.get(id);
    }

    String getRandomValue() {
        Random random = new Random();
        int valueLength = random.nextInt(maxLength);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < valueLength; i ++) {
            stringBuilder.append(getRandomCharacter());
        }
        String generatedString = stringBuilder.toString();
        return generatedString;
    }

    Character getRandomCharacter() {
        boolean flag = false;
        Random random = new Random();
        int maxCharValue = 126;
        int minCharValue = 32;
        int interval = maxCharValue - minCharValue;
        char c = 'a';
        while(!flag) {
            c = (char) (random.nextInt(interval + 1) + minCharValue);
            if(escapeSet.contains(c) == false)
                flag = true;
        }
        return c;
    }

    void addValueToPool(String value) {
        if(!valuePool.contains(value)) {
            valuePool.add(value);
            valuePoolLookUpMap.put(valuePoolIdCnt, value);
            valuePoolIdCnt += 1;
        }
    }
}
