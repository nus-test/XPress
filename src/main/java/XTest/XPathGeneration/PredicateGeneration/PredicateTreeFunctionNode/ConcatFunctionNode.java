package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class ConcatFunctionNode extends PredicateTreeFunctionNode {
    static {
        ConcatFunctionNode concatFunctionNode = new ConcatFunctionNode();
        PredicateTreeFunctionNode.insertFunctionToMap(concatFunctionNode, XMLDatatype.STRING);
    }
    @Override
    public String toString() {
        return "concat(" + childList.get(0).toString() + ", " + childList.get(1).toString() + ")";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
        String randomString = XMLDatatype.STRING.getValueHandler().getValue(false);
        PredicateTreeConstantNode concatNode = new PredicateTreeConstantNode(XMLDatatype.STRING, randomString);
        childList.add(concatNode);
    }

    @Override
    public ConcatFunctionNode newInstance() {
        return new ConcatFunctionNode();
    }
}
