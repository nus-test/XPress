package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.DatatypeControl.PrimitiveDatatype.XMLMixed;
import XPress.DatatypeControl.PrimitiveDatatype.XMLNode;
import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.GlobalSettings;
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
        if(childNode.datatypeRecorder.getActualDatatype() instanceof XMLDouble)
            return false;
        return !(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence) &&
                !(childNode.datatypeRecorder.xmlDatatype instanceof XMLNode) &&
                !(childNode.datatypeRecorder.xmlDatatype instanceof XMLMixed);
    }
}