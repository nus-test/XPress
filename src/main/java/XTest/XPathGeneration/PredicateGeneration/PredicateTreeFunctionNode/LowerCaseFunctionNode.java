package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class LowerCaseFunctionNode extends PredicateTreeFunctionNode {
    static {
        LowerCaseFunctionNode lowerCaseFunctionNode = new LowerCaseFunctionNode();
        PredicateTreeFunctionNode.insertFunctionToMap(lowerCaseFunctionNode, XMLDatatype.STRING);
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public String toString() {
        return "lower-case(" + childList.get(0) + ")";
    }

    @Override
    public LowerCaseFunctionNode newInstance() {
        return new LowerCaseFunctionNode();
    }
}
