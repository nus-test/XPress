package XTest.XMLGeneration;

import XTest.PrimitiveDatatypes.XMLDataType;

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

    void assignRandomValue() {
        dataContext = dataType.getValueGenerator().getValue();
    }
}
