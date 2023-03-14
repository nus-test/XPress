package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class LessThenOperationNode extends PredicateTreeLogicalOperationNode {
    LessThenOperationNode() {
        this.XPathExpr = "<";
    }

    @Override
    public LessThenOperationNode newInstance() {
        return new LessThenOperationNode();
    }
}
