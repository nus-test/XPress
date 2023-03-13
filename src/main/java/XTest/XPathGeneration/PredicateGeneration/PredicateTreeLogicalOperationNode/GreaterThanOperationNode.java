package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class GreaterThanOperationNode extends PredicateTreeLogicalOperationNode {
    @Override
    public String toString() {
        return childList.get(0) + ">" + childList.get(1);
    }

    @Override
    public GreaterThanOperationNode newInstance() {
        return new GreaterThanOperationNode();
    }
}
