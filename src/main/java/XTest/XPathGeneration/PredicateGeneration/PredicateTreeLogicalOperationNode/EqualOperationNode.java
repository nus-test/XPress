package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

import XTest.PrimitiveDatatype.XMLDatatype;

public class EqualOperationNode extends PredicateTreeLogicalOperationNode {
    EqualOperationNode() {
        this.XPathExpr = "=";
    }

    @Override
    public EqualOperationNode newInstance() {
        return new EqualOperationNode();
    }
}
