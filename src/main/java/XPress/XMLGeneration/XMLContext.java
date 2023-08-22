package XPress.XMLGeneration;

import java.util.ArrayList;
import java.util.List;

public class XMLContext {
    ContextNode root;
    String xmlContent;
    List<String> namespaceList;

    XMLContext(ContextNode root, String xmlContent) {
        this.root = root;
        this.xmlContent = xmlContent;
    }

    XMLContext(ContextNode root, String xmlContent, List<String> namespaceList) {
        this.root = root;
        this.xmlContent = xmlContent;
        this.namespaceList = new ArrayList<>(namespaceList);
    }

    public ContextNode getRoot() {
        return root;
    }
    public String getXmlContent() {
        return xmlContent;
    }

    public List<String> getNamespaceList() { return namespaceList; }
}
