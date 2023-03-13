package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class GreaterOrEqualOperationNode extends PredicateTreeLogicalOperationNode {

    @Override
    public String toString() {
        return childList.get(0) + ">=" + childList.get(1);
    }

    @Override
    public GreaterOrEqualOperationNode newInstance() {
        return new GreaterOrEqualOperationNode();
    }
}
