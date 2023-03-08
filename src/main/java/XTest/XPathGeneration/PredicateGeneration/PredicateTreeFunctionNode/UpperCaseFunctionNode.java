package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class UpperCaseFunctionNode extends PredicateTreeFunctionNode {
    static {
        UpperCaseFunctionNode upperCaseFunctionNode = new UpperCaseFunctionNode();
        PredicateTreeFunctionNode.insertFunctionToMap(upperCaseFunctionNode, XMLDatatype.STRING);
    }
    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public String toString() {
        return "upper-case(" + childList.get(0).toString() + ")";
    }

    @Override
    public UpperCaseFunctionNode newInstance() {
        return new UpperCaseFunctionNode();
    }
}
