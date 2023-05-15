package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import org.apache.tools.ant.taskdefs.condition.Not;

public class EqualOperatorNode extends InformationTreeComparisonOperatorNode {
    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(MainExecutor mainExecutor, int starredNodeId) {
        InformationTreeComparisonOperatorNode newRoot = new NotEqualOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public InformationTreeFunctionNode newInstance() {
        return new EqualOperatorNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {

    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {

    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return null;
    }
}