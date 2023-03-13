package XTest;

import java.util.*;
import java.util.stream.Collectors;

public class GlobalRandom {
    private static GlobalRandom globalRandom;
    Random random;
    private GlobalRandom() {
        random = new Random();
        random.setSeed(335);
    }

    public static GlobalRandom getInstance() {
        if(globalRandom == null)
            globalRandom = new GlobalRandom();
        return globalRandom;
    }

    public int nextInt() { return random.nextInt(); }
    public int nextInt(int minBound, int maxBound) {
        if (maxBound < 0) {
            return -(random.nextInt(-maxBound + minBound) - minBound);
        } else if(minBound >= 0)
            return random.nextInt(maxBound - minBound) + minBound;
        else {
            return (int) (random.nextLong((long) maxBound - (long) minBound) + minBound);
        }
    }

    public int nextInt(int maxBound) {
        if(maxBound > 0)
            return random.nextInt(maxBound);
        else {
            return -random.nextInt(-maxBound);
        }
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

    public <T> T getRandomFromList(List<T> list) {
        int id = nextInt(0, list.size());
        return list.get(id);
    }
}
