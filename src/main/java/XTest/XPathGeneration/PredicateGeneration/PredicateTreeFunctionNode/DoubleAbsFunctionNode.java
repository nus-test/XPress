package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class DoubleAbsFunctionNode extends PredicateTreeFunctionNode {
    DoubleAbsFunctionNode() {
        this.datatype = XMLDatatype.DOUBLE;
        XPathExpr = "abs";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public DoubleAbsFunctionNode newInstance() {
        return new DoubleAbsFunctionNode();
    }
}
