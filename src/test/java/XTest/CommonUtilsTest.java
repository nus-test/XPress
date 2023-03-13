package XTest;

import org.junit.jupiter.api.Test;

public class CommonUtilsTest {
    @Test
    void getEnclosedIntegerTest() {
        String testString = "hello, id=\"13213\"";
        for(int i = 0; i < testString.length(); i ++) {
            System.out.println(i + " " + testString.charAt(i));
        }
        System.out.println(CommonUtils.getEnclosedInteger(testString, 11));
    }
}
