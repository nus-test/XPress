package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class GreaterOrEqualOperationNode extends PredicateTreeLogicalOperationNode {
    GreaterOrEqualOperationNode() {
        this.XPathExpr = ">=";
    }
    @Override
    public GreaterOrEqualOperationNode newInstance() {
        return new GreaterOrEqualOperationNode();
    }
}
