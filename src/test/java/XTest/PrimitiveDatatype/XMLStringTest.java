package XTest.PrimitiveDatatype;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class XMLStringTest {
    @Test
    void GenerateRandomXMLStringTest() {
        XMLStringGenerator xmlStringGenerator = new XMLStringGenerator();
        String randomString = xmlStringGenerator.getRandomValue();
        System.out.println(randomString);
    }

    @Test
    void EscapeSetContainsSingleDash() {
        XMLStringGenerator xmlStringGenerator = new XMLStringGenerator();
        assertTrue(xmlStringGenerator.escapeSet.contains('\''));
    }

    @Test
    void GeneratedStringDoesNotContainEscapedCharacters() {
        XMLStringGenerator xmlStringGenerator = new XMLStringGenerator();
        for(int i = 1; i <= 10; i ++) {
            String randomString = xmlStringGenerator.getRandomValue();
            for (int j = 0; j < randomString.length(); j ++) {
                assertFalse(xmlStringGenerator.escapeSet.contains(randomString.charAt(j)));
            }
        }
    }

    @Test
    void ScratchTest() {
        String a = "a";
        a += "v";
        System.out.println(a);
    }
}
