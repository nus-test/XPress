package XTest.XMLGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;

public class ElementNode {
    String tagName;
    ContextNode parentNode;
    XMLDatatype dataType;
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
