package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class DoubleRoundFunctionNode extends PredicateTreeFunctionNode {
    DoubleRoundFunctionNode() {
        datatype = XMLDatatype.DOUBLE;
        XPathExpr = "round";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public DoubleRoundFunctionNode newInstance() {
        return new DoubleRoundFunctionNode();
    }
}
