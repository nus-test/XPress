package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class LessOrEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(MainExecutor mainExecutor, int starredNodeId) {
        InformationTreeComparisonOperatorNode newRoot = new GreaterThanOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public InformationTreeFunctionNode newInstance() {
        return new LessOrEqualOperatorNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {

    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {

    }
}
