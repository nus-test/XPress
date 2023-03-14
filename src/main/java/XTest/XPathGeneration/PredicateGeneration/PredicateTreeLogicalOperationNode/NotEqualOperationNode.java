package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class NotEqualOperationNode extends PredicateTreeLogicalOperationNode {
    NotEqualOperationNode() {
        this.XPathExpr = "!=";
    }

    @Override
    public NotEqualOperationNode newInstance() {
        return new NotEqualOperationNode();
    }
}
