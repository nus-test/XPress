package PrimitiveDatatypes;

import org.junit.jupiter.api.Test;

public class XMLStringTest {
    @Test
    void GenerateRandomXMLStringTest() {
        XMLStringGenerator xmlStringGenerator = new XMLStringGenerator();
        String randomString = xmlStringGenerator.getRandomValue();
    }
}
