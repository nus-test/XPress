package XTest.XPathGeneration.PredicateGeneration;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import XTest.XMLGeneration.ElementNode;

public class PredicateTreeConstantNode extends PredicateTreeNode {
    PredicateTreeConstantNode() {}

    public PredicateTreeConstantNode(XMLDatatype xmlDatatype, String context) {
        datatype = xmlDatatype;
        dataContent = context;
        XPathExpr = context;
        if(this.datatype == XMLDatatype.STRING)
            XPathExpr = "\"" + context + "\"";
    }

    PredicateTreeConstantNode(ElementNode elementNode) {
        dataContent = elementNode.dataContext;
        datatype = elementNode.dataType;
        if(elementNode instanceof AttributeNode)
            XPathExpr = "@" + elementNode.tagName;
        else
            XPathExpr = "text()";
    }

    @Override
    public String toString() {
        return XPathExpr;
    }
}
