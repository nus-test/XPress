package XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode;

import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

public class ExistFunctionNode extends PredicateTreeFunctionNode {
    @Override
    public String toString() {
        return "exists(" + childList.toString() + ")";
    }

    @Override
    public void fillContents(PredicateTreeNode inputNode) {
        childList.add(inputNode);
    }

    @Override
    public ExistFunctionNode newInstance() {
        return new ExistFunctionNode();
    }
}
