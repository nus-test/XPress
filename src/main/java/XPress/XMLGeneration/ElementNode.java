package XPress.XMLGeneration;

import XPress.PrimitiveDatatype.XMLDatatype;

public class ElementNode {
    public String tagName;
    public ContextNode parentNode;
    public XMLDatatype dataType;
    public String dataContext;

    ElementNode() {}
    ElementNode(ElementNode elementNode) {
        this.tagName = elementNode.tagName;
        this.dataType = elementNode.dataType;
        this.dataContext = elementNode.dataContext;
    }

    void assignRandomValue() {
        dataContext = dataType.getValueHandler().getValue();
    }
}
