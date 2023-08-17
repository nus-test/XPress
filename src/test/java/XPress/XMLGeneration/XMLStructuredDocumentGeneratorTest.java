package XPress.XMLGeneration;

import org.junit.jupiter.api.Test;

public class XMLStructuredDocumentGeneratorTest {
    @Test
    void XMLDocumentGenerationWithStructureTest() {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLStructuredDocumentGenerator();
        String xml = xmlDocumentGenerator.getXMLDocumentWithStructure(20);
        System.out.println(xml);
    }
}
