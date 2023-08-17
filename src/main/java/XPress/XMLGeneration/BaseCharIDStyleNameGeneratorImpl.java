package XPress.XMLGeneration;

import XPress.GlobalRandom;

import java.util.ArrayList;
import java.util.List;

public class BaseCharIDStyleNameGeneratorImpl implements NameGenerator {

    char baseChar;
    List<Integer> alphabetCntList = new ArrayList<>();

    BaseCharIDStyleNameGeneratorImpl(char baseChar) {
        this.baseChar = baseChar;
        for(int i = 0; i < 26; i ++) {
            alphabetCntList.add(1);
        }
    }
    @Override
    public String generateName() {
        int alphabetId = GlobalRandom.getInstance().nextInt(26);
        char prefix = (char) (baseChar + alphabetId);
        String name = prefix + Integer.toString(alphabetCntList.get(alphabetId));
        alphabetCntList.set(alphabetId, alphabetCntList.get(alphabetId) + 1);
        return name;
    }
}
