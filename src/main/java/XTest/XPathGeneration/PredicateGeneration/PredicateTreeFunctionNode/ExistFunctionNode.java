package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;
import XTest.XPathGeneration.PredicateGeneration.UnaryPredicateTreeNode;

public class ExistFunctionNode extends PredicateTreeFunctionNode implements UnaryPredicateTreeNode {
    ExistFunctionNode() {
        this.datatype = XMLDatatype.BOOLEAN;
        XPathExpr = "exists";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public ExistFunctionNode newInstance() {
        return new ExistFunctionNode();
    }

}
