package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class EmptyFunctionNode extends PredicateTreeFunctionNode {
    EmptyFunctionNode() {
        this.datatype = XMLDatatype.BOOLEAN;
        XPathExpr = "empty";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public EmptyFunctionNode newInstance() {
        return new EmptyFunctionNode();
    }
}
