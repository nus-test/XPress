package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;

public abstract class InformationTreeComparisonOperatorNode extends InformationTreeFunctionNode {
    @Override
    public abstract InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId);

    void transferInfo(InformationTreeComparisonOperatorNode prevNode) {
        // TODO: Check if there is more information necessary to transfer
        this.childList = prevNode.childList;
        this.datatypeRecorder = prevNode.datatypeRecorder;
        this.containsContextConstant = prevNode.containsContextConstant;
        cacheXPathExpression();
    }
}
