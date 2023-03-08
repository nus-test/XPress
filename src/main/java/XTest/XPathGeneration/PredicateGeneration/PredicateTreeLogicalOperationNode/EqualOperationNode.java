package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

public class EqualOperationNode extends PredicateTreeLogicalOperationNode {
    @Override
    public String toString() {
        return childList.get(0) + "=" + childList.get(1);
    }

    @Override
    public EqualOperationNode newInstance() {
        return new EqualOperationNode();
    }
}
