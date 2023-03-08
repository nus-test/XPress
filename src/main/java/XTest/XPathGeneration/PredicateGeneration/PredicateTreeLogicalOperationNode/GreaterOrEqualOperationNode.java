package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.PredicateTreeLogicalConnectionNode;

public class GreaterOrEqualOperationNode extends PredicateTreeLogicalOperationNode {
    static {
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new GreaterThanOperationNode());
    }

    @Override
    public String toString() {
        return childList.get(0) + ">=" + childList.get(1);
    }

    @Override
    public GreaterOrEqualOperationNode newInstance() {
        return new GreaterOrEqualOperationNode();
    }
}
