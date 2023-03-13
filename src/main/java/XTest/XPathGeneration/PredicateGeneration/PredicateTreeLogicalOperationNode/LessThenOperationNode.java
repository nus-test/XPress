package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class LessThenOperationNode extends PredicateTreeLogicalOperationNode {
    @Override
    public String toString() {
        return childList.get(0) + "<" + childList.get(1);
    }

    @Override
    public LessThenOperationNode newInstance() {
        return new LessThenOperationNode();
    }
}
