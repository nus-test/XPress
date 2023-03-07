package XTest.XMLGeneration;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class XMLDocumentGeneratorTest {

    @Test
    void XMLContextTreeGenerationTest() {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        ContextNode root = xmlDocumentGenerator.generateXMLDocument(5);
    }

    @Test
    void XMLDocumentGenerationTest() {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        String xml = xmlDocumentGenerator.getXMLDocument(5);
        System.out.println(xml);
    }

    @Test
    void saveXMLDocumentToFileTest() throws IOException {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        xmlDocumentGenerator.generateXMLDocumentSave2Resource(5, "/xmldocs/test2.xml");
    }

    @Test
    void XMLDocumentGenerationWithStructureTest() {
        XMLDocumentGenerator xmlDocumentGenerator = new XMLDocumentGenerator();
        String xml = xmlDocumentGenerator.getXMLDocumentWithStructure(5);
        System.out.println(xml);
    }
}
