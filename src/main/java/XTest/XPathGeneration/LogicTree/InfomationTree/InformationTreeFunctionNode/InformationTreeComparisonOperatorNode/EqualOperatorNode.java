package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import org.apache.tools.ant.taskdefs.condition.Not;

public class EqualOperatorNode extends InformationTreeComparisonOperatorNode {
    EqualOperatorNode() {
        functionExpr = "=";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) {
        InformationTreeComparisonOperatorNode newRoot = new NotEqualOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public EqualOperatorNode newInstance() {
        return new EqualOperatorNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if (!childNode.checkCalculableContext()) {
            fillContentsRandom(childNode);
            return;
        }
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        childList.add(new InformationTreeConstantNode(childNode.dataTypeRecorder.xmlDatatype, childNode.context));
    }
}