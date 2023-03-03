package XMLGeneration;

public class XMLDocumentGenerator {
    ContextTreeGenerator contextTreeGenerator;

    XMLDocumentGenerator(ContextTreeGenerator contextTreeGenerator) {
        this.contextTreeGenerator = contextTreeGenerator;
    }

    ContextNode GenerateXMLDocument(int contextNodeSize) {
        ContextNode root = contextTreeGenerator.GenerateRandomTree(contextNodeSize);
        int templateSize = contextNodeSize / 2;

        return null;
    }
}
