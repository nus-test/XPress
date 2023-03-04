package XTest;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class GlobalRandomTest {
    @Test
    void nextIntListNoRepRandomGenerationTest() {
        for(int i = 0; i < 5; i ++) {
            int length = GlobalRandom.getInstance().nextInt(10);
            int maxBound = GlobalRandom.getInstance().nextInt(15);
            if (maxBound < length) {
                int t = maxBound;
                maxBound = length;
                length = t;
            }
            System.out.println("-------------" + length + " " + maxBound);
            List<Integer> list = GlobalRandom.getInstance().nextIntListNoRep(length, maxBound);
            Set<Integer> set = new HashSet<>();
            assertTrue(list.size() == length);
            for (Integer intVar : list) {
                assertFalse(set.contains(intVar));
                assertTrue(intVar < maxBound);
            }
            for (Integer intVar : list) {
                System.out.printf(intVar + " ");
            }
            System.out.println("");
        }
    }
}
