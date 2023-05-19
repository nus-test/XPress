package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLComparable;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class GreaterOrEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    GreaterOrEqualOperatorNode() {
        functionExpr = ">=";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) {
        InformationTreeComparisonOperatorNode newRoot = new LessThanOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public GreaterOrEqualOperatorNode newInstance() {
        return new GreaterOrEqualOperatorNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if (!childNode.checkCalculableContext()) {
            fillContentsRandom(childNode);
            return;
        }
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        String value = ((XMLComparable) childNode.dataTypeRecorder.xmlDatatype.getValueHandler()).getLess(childNode.context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }
}
