package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XPress.GlobalSettings;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV1
public class NotEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    public NotEqualOperatorNode() {
        functionExpr = "!=";
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        if(!GlobalSettings.starNodeSelection && GlobalSettings.rectifySelection) {
            return super.modifyToContainStarredNode(starredNodeId);
        }
        InformationTreeComparisonOperatorNode newRoot = new EqualOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public NotEqualOperatorNode newInstance() {
        return new NotEqualOperatorNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String value = childNode.datatypeRecorder.xmlDatatype.getValueHandler().getNotEqual(childNode.getContext().context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.getActualDatatype() == XMLDatatype.DOUBLE)
            return false;
        return childNode.datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE &&
                childNode.datatypeRecorder.xmlDatatype != XMLDatatype.NODE &&
                childNode.datatypeRecorder.xmlDatatype != XMLDatatype.MIXED;
    }
}