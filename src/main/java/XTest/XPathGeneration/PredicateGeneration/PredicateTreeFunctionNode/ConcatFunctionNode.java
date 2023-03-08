package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;

public class ConcatFunctionNode extends PredicateTreeFunctionNode {
    static {
        ConcatFunctionNode concatFunctionNode = new ConcatFunctionNode();
        PredicateTreeFunctionNode.insertFunctionToMap(concatFunctionNode, XMLDatatype.STRING);
    }
    @Override
    public String toString() {
        return "concat(" + childList.get(0).toString() + ", " + childList.get(1).toString() + ")";
    }
}
