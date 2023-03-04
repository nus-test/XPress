package XTest;

import java.util.*;
import java.util.stream.Collectors;

public class GlobalRandom {
    private static GlobalRandom globalRandom;
    Random random;
    private GlobalRandom() {
        random = new Random();
        random.setSeed(998244353);
    }

    public static GlobalRandom getInstance() {
        if(globalRandom == null)
            globalRandom = new GlobalRandom();
        return globalRandom;
    }

    public int nextInt() { return random.nextInt(); }
    public int nextInt(int minBound, int maxBound) {
        return random.nextInt(maxBound - minBound) + minBound;
    }

    public int nextInt(int maxBound) {
        return random.nextInt(maxBound);
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    public List<Integer> nextIntListNoRep(int length, int maxBound) {
        List<Integer> intList = new ArrayList<>();
        for(int i = 0; i < maxBound; i ++)
            intList.add(i);
        Collections.shuffle(intList, random);
        return intList.stream().limit(length).collect(Collectors.toList());
    }
}
