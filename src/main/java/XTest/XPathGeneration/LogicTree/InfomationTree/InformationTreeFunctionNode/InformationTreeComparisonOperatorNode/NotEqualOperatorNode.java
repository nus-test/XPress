package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class NotEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) {
        InformationTreeComparisonOperatorNode newRoot = new EqualOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public InformationTreeFunctionNode newInstance() {
        return new NotEqualOperatorNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {

    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {

    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return null;
    }

}