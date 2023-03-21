package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.PredicateGeneration.UnaryPredicateTreeNode;

public class NotConnectionNode extends PredicateTreeLogicalConnectionNode implements UnaryPredicateTreeNode {
    public NotConnectionNode() {
        this.XPathExpr = "not";
    }

    @Override
    public String toString() {
        return this.XPathExpr + "(" + childList.get(0) + ")";
    }

    @Override
    public NotConnectionNode newInstance() {
        return new NotConnectionNode();
    }
}
