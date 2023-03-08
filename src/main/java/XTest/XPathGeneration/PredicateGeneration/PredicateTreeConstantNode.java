package XTest.XPathGeneration.PredicateGeneration;

import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import XTest.XMLGeneration.ElementNode;

public class PredicateTreeConstantNode extends PredicateTreeNode {
    PredicateTreeConstantNode() {}
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
