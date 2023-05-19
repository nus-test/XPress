package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.PrimitiveDatatype.XMLComparable;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public abstract class InformationTreeComparisonOperatorNode extends InformationTreeFunctionNode {
    InformationTreeComparisonOperatorNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
    }

    @Override
    public abstract InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId);

    void transferInfo(InformationTreeComparisonOperatorNode prevNode) {
        // TODO: Check if there is more information necessary to transfer
        this.childList = prevNode.childList;
        this.datatypeRecorder = prevNode.datatypeRecorder;
        this.containsContextConstant = prevNode.containsContextConstant;
        cacheXPathExpression();
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        childList.add(new InformationTreeConstantNode(childNode.dataTypeRecorder.xmlDatatype,
                childNode.datatypeRecorder.xmlDatatype.getValueHandler().getValue()));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE ||
            childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE ||
            childNode.datatypeRecorder.xmlDatatype == XMLDatatype.MIXED)
            return false;
        return childNode.datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLComparable;
    }
}
