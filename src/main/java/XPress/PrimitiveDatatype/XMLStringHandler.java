package XPress.PrimitiveDatatype;

import XPress.GlobalRandom;

import java.util.*;

public class XMLStringHandler extends PooledValueHandler implements XMLComparable, XMLSimple, XMLAtomic {
    Set<Character> escapeSet = new HashSet<>(Arrays.asList('\'', '\"', '<', '>', '/', '&'));
    int valuePoolIdCnt = 0;
    Set<String> valuePool = new HashSet<>();
    Map<Integer, String> valuePoolLookUpMap = new HashMap<>();
    static int minLength = 0, maxLength = 20;
    boolean spaceFlipFlop = false;

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
                        value = value.substring(0, index) + getRandomCharacterWithSpace() + value.substring(index);
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
            if(i == 0 || i == valueLength - 1)
                stringBuilder.append(getRandomCharacter());
            else stringBuilder.append(getRandomCharacterWithSpace());
        }
        String generatedString = stringBuilder.toString();
        if(valueLength > 5 && GlobalRandom.getInstance().nextDouble() < 0.2) {
            int index = GlobalRandom.getInstance().nextInt(generatedString.length() - 1) + 1;
            generatedString = generatedString.substring(0, index) + ' ' + generatedString.substring(index);
        }
        return generatedString;
    }

    Character getRandomCharacterWithSpace() {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < (spaceFlipFlop ? 0.3: 0.1)) {
            spaceFlipFlop = !spaceFlipFlop;
            return ' ';
        }
        return getRandomCharacter();
    }

    Character getRandomCharacter() {
        boolean flag = false;
        int maxCharValue = 126;
        int minCharValue = 32;
        char c = 'a';
        while(!flag) {
            c = (char) (GlobalRandom.getInstance().nextInt(minCharValue, maxCharValue + 1));
            if(!escapeSet.contains(c))
                flag = true;
        }
        return c;
    }

    @Override
    public XMLComparedResult compare(String baseValue, String compareValue) {
        return compareT(baseValue, compareValue);
    }

    @Override
    public String getDefiniteGreater(String baseValue) {
        double prob = GlobalRandom.getInstance().nextDouble();
        String compareString = null;
        if(prob < 0.5) {
            Character c = getRandomCharacter();
            if(compareT(c + baseValue, baseValue) == XMLComparedResult.GREATER)
                compareString = c + baseValue;
        }
        if(compareString == null) {
            Character c = getRandomCharacter();
            compareString = baseValue + c;
        }
        return compareString;
    }

    @Override
    public String getDefiniteLess(String baseValue) {
        double prob = GlobalRandom.getInstance().nextDouble();
        String compareString = null;
        if(prob < 0.5 && baseValue.length() >= 1) {
            compareString = baseValue.substring(1);
            if(compareT(baseValue, compareString) != XMLComparedResult.LESS)
                compareString = null;
        }
        if(compareString == null && baseValue.length() >= 1) {
            compareString = baseValue.substring(0, baseValue.length());
        }
        if(compareString == null)
            compareString = "";
        return compareString;
    }
}
