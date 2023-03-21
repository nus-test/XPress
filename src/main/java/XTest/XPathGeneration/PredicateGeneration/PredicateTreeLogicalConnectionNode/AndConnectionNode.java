package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode;

import XTest.PrimitiveDatatype.XMLDatatype;

public class AndConnectionNode extends PredicateTreeLogicalConnectionNode {

    AndConnectionNode() {
        this.XPathExpr = "and";
    }

    @Override
    public AndConnectionNode newInstance() {
        return new AndConnectionNode();
    }
}
