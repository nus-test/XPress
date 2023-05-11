package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;

public abstract class InformationTreeComparisonOperatorNode extends InformationTreeFunctionNode {
    @Override
    public abstract InformationTreeComparisonOperatorNode modifyToContainStarredNode(MainExecutor mainExecutor, int starredNodeId);

    void transferInfo(InformationTreeComparisonOperatorNode prevNode) {
        // TODO: Check if there is more information necessary to transfer
        this.childList = prevNode.childList;
        this.dataTypeRecorder = prevNode.dataTypeRecorder;
        this.containsContextConstant = prevNode.containsContextConstant;
        cacheXPathExpression();
    }
}
