package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class IntegerAbsFunctionNode extends PredicateTreeFunctionNode {
    IntegerAbsFunctionNode() {
        this.datatype = XMLDatatype.INTEGER;
        XPathExpr = "abs";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public PredicateTreeFunctionNode newInstance() {
        return new IntegerAbsFunctionNode();
    }
}
