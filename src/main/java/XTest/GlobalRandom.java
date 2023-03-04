package XTest;

import java.util.Random;

public class GlobalRandom {
    private static GlobalRandom globalRandom;
    Random random;
    private GlobalRandom() {
        random = new Random();
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
}
