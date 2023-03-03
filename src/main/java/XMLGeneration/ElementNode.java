package XMLGeneration;

import PrimitiveDatatypes.XMLDataType;

import javax.swing.text.Element;

public class ElementNode {
    String tagName;
    ContextNode parentNode;
    XMLDataType dataType;
    String dataContext;

    ElementNode() {}
    ElementNode(ElementNode elementNode) {
        this.tagName = elementNode.tagName;
        this.dataType = elementNode.dataType;
        this.dataContext = elementNode.dataContext;
    }
}
