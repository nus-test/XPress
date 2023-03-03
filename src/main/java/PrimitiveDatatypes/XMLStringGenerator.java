package PrimitiveDatatypes;

import java.nio.charset.Charset;
import java.util.*;

import static PrimitiveDatatypes.RandomDataGenerator.mutateProb;
import static PrimitiveDatatypes.RandomDataGenerator.newProb;

public class XMLStringGenerator {
    Set<Character> escapeSet = new HashSet<>(Arrays.asList('\'', '\"'));
    int valuePoolIdCnt = 0;
    Set<String> valuePool = new HashSet<>();
    Map<Integer, String> valuePoolLookUpMap = new HashMap<>();
    static int minLength = 0, maxLength = 60;

    String getValue() {
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
        byte[] array = new byte[maxLength]; // length is bounded by 7
        Random random = new Random();
        new Random().nextBytes(array);
        int valueLength = random.nextInt(maxLength);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        System.out.println("Maxlength: " + maxLength + " valueLength: " + valueLength + " " + array.length);
        StringBuilder stringBuilder = new StringBuilder(generatedString.substring(0, valueLength));
        for(int i = 0; i < stringBuilder.length(); i ++) {
            if(escapeSet.contains(stringBuilder.charAt(i))) {
                stringBuilder.setCharAt(i, getRandomCharacter());
            }
        }
        generatedString = stringBuilder.toString();
        System.out.println(generatedString);
        return generatedString;
    }

    Character getRandomCharacter() {
        byte[] array = new byte[1];
        Character c = 'a';
        boolean flag = false;
        while(!flag) {
            c = (new String(array, Charset.forName("UTF-8"))).charAt(0);
            if(!escapeSet.contains(c))
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
