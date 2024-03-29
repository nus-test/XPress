package XPress.DatatypeControl.ValueHandler;

import XPress.GlobalRandom;

import java.util.*;

import static XPress.XMLGeneration.DataGenerator.mutateProb;
import static XPress.XMLGeneration.DataGenerator.newProb;

public abstract class PooledValueHandler extends ValueHandler {
    int valuePoolIdCnt = 0;
    Set<String> valuePool = new HashSet<>();
    Map<Integer, String> valuePoolLookUpMap = new HashMap<>();

    abstract String getRandomValue();

    public String getValue() {
        return getValue(true);
    }

    public String getValue(boolean pooling) {
        boolean choice = GlobalRandom.getInstance().nextDouble() < newProb;
        String generatedString;
        if(choice) {
            // Generate new random value and add to pool;
            generatedString = getRandomValue();
            if(pooling) addValueToPool(getRandomValue());
        }
        else {
            // Get random value from original pool or from mutation
            boolean mutateChoice = GlobalRandom.getInstance().nextDouble() < mutateProb;
            String baseString = getRandomValueFromPool();
            if(mutateChoice) {
                generatedString = mutateValue(baseString);
                if(pooling) addValueToPool(generatedString);
            }
            else generatedString = baseString;
        }
        return generatedString;
    }

    public String getRandomValueFromPool() {
        if(valuePoolIdCnt == 0) {
            return getRandomValue();
        }
        int id = GlobalRandom.getInstance().nextInt(valuePoolIdCnt);
        return valuePoolLookUpMap.get(id);
    }

    void addValueToPool(String value) {
        if(!valuePool.contains(value)) {
            valuePool.add(value);
            valuePoolLookUpMap.put(valuePoolIdCnt, value);
            valuePoolIdCnt += 1;
        }
    }

    @Override
    public void clear() {
        valuePoolIdCnt = 0;
        valuePool = new HashSet<>();
        valuePoolLookUpMap = new HashMap<>();
    }
}
