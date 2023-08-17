package XPress.XMLGeneration;

public class XMLContext {
    ContextNode root;
    String xmlContent;

    XMLContext(ContextNode root, String xmlContent) {
        this.root = root;
        this.xmlContent = xmlContent;
    }

    public ContextNode getRoot() {
        return root;
    }
    public String getXmlContent() {
        return xmlContent;
    }
}
