package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class DoubleFloorFunctionNode extends PredicateTreeFunctionNode {

    DoubleFloorFunctionNode() {
        datatype = XMLDatatype.DOUBLE;
        XPathExpr = "floor";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public DoubleFloorFunctionNode newInstance() {
        return new DoubleFloorFunctionNode();
    }
}
