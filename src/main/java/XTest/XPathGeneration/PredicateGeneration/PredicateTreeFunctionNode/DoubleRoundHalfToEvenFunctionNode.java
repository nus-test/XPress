package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class DoubleRoundHalfToEvenFunctionNode extends PredicateTreeFunctionNode {
    DoubleRoundHalfToEvenFunctionNode() {
        datatype = XMLDatatype.DOUBLE;
        XPathExpr = "round-half-to-even";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public DoubleRoundHalfToEvenFunctionNode newInstance() {
        return new DoubleRoundHalfToEvenFunctionNode();
    }
}
