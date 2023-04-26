package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeContextNodeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XMLGeneration.AttributeNode;
import XTest.XMLGeneration.ContextNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class AttributeFunctionNode extends PredicateTreeContextNodeFunctionNode {
    AttributeNode attributeNode = null;

    public AttributeFunctionNode() {

    }

    public AttributeFunctionNode(ContextNode currentNode) {
        attributeNode = GlobalRandom.getInstance().getRandomFromList(currentNode.attributeList);
        setContext(attributeNode);
    }

    public AttributeFunctionNode(AttributeNode attributeNode) {
        setContext(attributeNode);
    }

    void setContext(AttributeNode attributeNode) {
        XPathExpr = "@" + attributeNode.tagName;
        datatype = attributeNode.dataType;
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        attributeNode = GlobalRandom.getInstance().getRandomFromList(inputNode.contextNode.attributeList);
        setContext(attributeNode);
    }

    @Override
    public PredicateTreeConstantNode generatePredicateTreeNodeFromContext(ContextNode currentNode) {
        if(attributeNode == null) {
            attributeNode = GlobalRandom.getInstance().getRandomFromList(currentNode.attributeList);
            setContext(attributeNode);
        }
        return new PredicateTreeConstantNode(attributeNode);
    }

    @Override
    public AttributeFunctionNode newInstance() {
        return new AttributeFunctionNode();
    }
}
