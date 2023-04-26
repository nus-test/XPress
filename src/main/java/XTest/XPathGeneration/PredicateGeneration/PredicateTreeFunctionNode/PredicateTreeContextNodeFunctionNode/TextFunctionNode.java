package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;

public class TextFunctionNode extends PredicateTreeContextNodeFunctionNode {
    {
        XPathExpr = "text";
    }
    public TextFunctionNode(){};

    public TextFunctionNode(ContextNode contextNode) {
        setContext(contextNode);
    }

    void setContext(ContextNode contextNode) {
        datatype = contextNode.dataType;
        dataContent = contextNode.dataContext;
    }

    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        PredicateTreeConstantNode node = new PredicateTreeConstantNode(currentNode, XPathExpr + "()");
        return node;
    }

    @Override
    public TextFunctionNode newInstance() {
        return new TextFunctionNode();
    }
}
