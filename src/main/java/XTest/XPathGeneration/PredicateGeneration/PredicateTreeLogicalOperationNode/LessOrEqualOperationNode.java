package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class LessOrEqualOperationNode extends PredicateTreeLogicalOperationNode {
    @Override
    public String toString() {
        return childList.get(0) + "<=" + childList.get(1);
    }

    @Override
    public LessOrEqualOperationNode newInstance() {
        return new LessOrEqualOperationNode();
    }
}
