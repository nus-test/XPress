package XPress.DatatypeControl.ValueHandler;

import XPress.DatatypeControl.ValueHandler.XMLStringHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class XMLStringHandlerTest {
    @Test
    void GenerateRandomXMLStringTest() {
        XMLStringHandler xmlStringHandler = new XMLStringHandler();
        String randomString = xmlStringHandler.getRandomValue();
        System.out.println(randomString);
    }

    @Test
    void EscapeSetContainsSingleDash() {
        XMLStringHandler xmlStringHandler = new XMLStringHandler();
        assertTrue(xmlStringHandler.escapeSet.contains('\''));
    }

    @Test
    void GeneratedStringDoesNotContainEscapedCharacters() {
        XMLStringHandler xmlStringHandler = new XMLStringHandler();
        for(int i = 1; i <= 10; i ++) {
            String randomString = xmlStringHandler.getRandomValue();
            for (int j = 0; j < randomString.length(); j ++) {
                assertFalse(xmlStringHandler.escapeSet.contains(randomString.charAt(j)));
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
