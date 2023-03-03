package XMLGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public String GenerateName() {
        Random random = new Random();
        int alphabetId = random.nextInt(26);
        char prefix = (char) (baseChar + alphabetId);
        String name = prefix + Integer.toString(alphabetCntList.get(alphabetId));
        return name;
    }
}
