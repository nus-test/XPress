package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class LessOrEqualOperationNode extends PredicateTreeLogicalOperationNode {

    LessOrEqualOperationNode() {
        this.XPathExpr = "<=";
    }

    @Override
    public LessOrEqualOperationNode newInstance() {
        return new LessOrEqualOperationNode();
    }
}
