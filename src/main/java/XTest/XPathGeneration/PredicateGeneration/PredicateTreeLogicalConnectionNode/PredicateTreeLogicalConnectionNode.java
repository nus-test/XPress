package XTest.XPathGeneration.PredicateGeneration.PredicateTreeLogicalConnectionNode;

import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public abstract class PredicateTreeLogicalConnectionNode extends PredicateTreeNode {

    public static PredicateTreeLogicalConnectionNode getRandomLogicalConnectionNode() {
        return null;
    }

    public void join(PredicateTreeNode leftChild, PredicateTreeNode rightChild) {
        childList.add(leftChild);
        childList.add(rightChild);
    }

    abstract public PredicateTreeLogicalConnectionNode newInstance();
}
