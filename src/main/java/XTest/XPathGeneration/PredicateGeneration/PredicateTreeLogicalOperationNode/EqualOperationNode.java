package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class EqualOperationNode extends PredicateTreeLogicalOperationNode {
    EqualOperationNode() {
        this.XPathExpr = "=";
    }

    @Override
    public EqualOperationNode newInstance() {
        return new EqualOperationNode();
    }
}
