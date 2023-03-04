package XTest.PrimitiveDatatypes;

import XTest.PrimitiveDatatypes.XMLStringGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class XMLStringTest {
    @Test
    void GenerateRandomXMLStringTest() {
        XMLStringGenerator xmlStringGenerator = new XMLStringGenerator();
        String randomString = xmlStringGenerator.getRandomValue();
        System.out.println(randomString);
        for(int i = 0; i < randomString.length(); i ++) {
            System.out.println(i + " " + (int) randomString.charAt(i));
        }
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
