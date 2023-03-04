package XTest.XMLGeneration;

import org.junit.jupiter.api.Test;

public class XMLDocumentGeneratorTest {
    @Test
    void XMLDocumentGenerationTest() {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        String xml = xmlDocumentGenerator.getXMLDocument(5);
        System.out.println(xml);
    }
}
