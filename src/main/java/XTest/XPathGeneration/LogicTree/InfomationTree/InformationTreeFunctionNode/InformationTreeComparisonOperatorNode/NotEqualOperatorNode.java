package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.PrimitiveDatatype.XMLComparable;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class NotEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    NotEqualOperatorNode() {
        functionExpr = "!=";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) {
        InformationTreeComparisonOperatorNode newRoot = new EqualOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public NotEqualOperatorNode newInstance() {
        return new NotEqualOperatorNode();
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if (!childNode.checkCalculableContext()) {
            fillContentsRandom(childNode);
            return;
        }
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        String value = childNode.dataTypeRecorder.xmlDatatype.getValueHandler().getNotEqual(childNode.context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }
    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return childNode.datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE &&
                childNode.datatypeRecorder.xmlDatatype != XMLDatatype.NODE &&
                childNode.datatypeRecorder.xmlDatatype != XMLDatatype.MIXED;
    }
}