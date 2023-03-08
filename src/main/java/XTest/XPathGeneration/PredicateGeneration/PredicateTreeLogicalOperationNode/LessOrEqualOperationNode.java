package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.PredicateTreeLogicalConnectionNode;

public class LessOrEqualOperationNode extends PredicateTreeLogicalOperationNode {
    static {
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new LessOrEqualOperationNode());
    }
    @Override
    public String toString() {
        return childList.get(0) + "<=" + childList.get(1);
    }

    @Override
    public LessOrEqualOperationNode newInstance() {
        return new LessOrEqualOperationNode();
    }
}
