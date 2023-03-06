package XTest.PrimitiveDatatype;

import XTest.GlobalRandom;

import java.util.*;

import static XTest.XMLGeneration.DataGenerator.mutateProb;
import static XTest.XMLGeneration.DataGenerator.newProb;

public abstract class PooledValueGenerator implements ValueGenerator {
    int valuePoolIdCnt = 0;
    Set<String> valuePool = new HashSet<>();
    Map<Integer, String> valuePoolLookUpMap = new HashMap<>();

    abstract String getRandomValue();
    abstract String mutateValue(String baseString);

    public String getValue() {
        boolean choice = GlobalRandom.getInstance().nextDouble() < newProb;
        String generatedString;
        if(choice) {
            // Generate new random value and add to pool;
            generatedString = getRandomValue();
            addValueToPool(getRandomValue());
        }
        else {
            // Get random value from original pool or from mutation
            boolean mutateChoice = GlobalRandom.getInstance().nextDouble() < mutateProb;
            String baseString = getRandomValueFromPool();
            if(mutateChoice) {
                generatedString = mutateValue(baseString);
                addValueToPool(generatedString);
            }
            else generatedString = baseString;
        }
        return generatedString;
    }

    String getRandomValueFromPool() {
        if(valuePoolIdCnt == 0) {
            return getRandomValue();
        }
        Random random = new Random();
        int id = random.nextInt(valuePoolIdCnt);
        return valuePoolLookUpMap.get(id);
    }

    void addValueToPool(String value) {
        if(!valuePool.contains(value)) {
            valuePool.add(value);
            valuePoolLookUpMap.put(valuePoolIdCnt, value);
            valuePoolIdCnt += 1;
        }
    }
}
