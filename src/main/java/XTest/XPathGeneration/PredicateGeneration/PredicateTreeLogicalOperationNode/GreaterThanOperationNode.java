package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class GreaterThanOperationNode extends PredicateTreeLogicalOperationNode {
    GreaterThanOperationNode() {
        this.XPathExpr = ">";
    }
    @Override
    public GreaterThanOperationNode newInstance() {
        return new GreaterThanOperationNode();
    }

}
