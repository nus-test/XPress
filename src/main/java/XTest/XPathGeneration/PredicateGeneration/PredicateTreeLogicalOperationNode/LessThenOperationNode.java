package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalOperationNode;

import XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode.PredicateTreeLogicalConnectionNode;

public class LessThenOperationNode extends PredicateTreeLogicalOperationNode {
    static {
        PredicateTreeLogicalOperationNode.comparativeOperationNodeList.add(new LessThenOperationNode());
    }
    @Override
    public String toString() {
        return childList.get(0) + "<" + childList.get(1);
    }

    @Override
    public LessThenOperationNode newInstance() {
        return new LessThenOperationNode();
    }
}
