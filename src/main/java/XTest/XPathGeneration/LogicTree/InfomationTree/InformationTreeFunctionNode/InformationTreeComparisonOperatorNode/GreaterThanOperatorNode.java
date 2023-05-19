package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class GreaterThanOperatorNode extends InformationTreeComparisonOperatorNode {
    GreaterThanOperatorNode() {
        functionExpr = ">=";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) {
        InformationTreeComparisonOperatorNode newRoot = new LessOrEqualOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public InformationTreeFunctionNode newInstance() {
        return new GreaterThanOperatorNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if (!childNode.checkCalculableContext()) {
            fillContentsRandom(childNode);
            return;
        }

    }

}
