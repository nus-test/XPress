package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.GlobalSettings;
import XPress.DatatypeControl.XMLComparable;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV1
public class GreaterOrEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    public GreaterOrEqualOperatorNode() {
        functionExpr = ">=";
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        if(!GlobalSettings.starNodeSelection && GlobalSettings.rectifySelection) {
            return super.modifyToContainStarredNode(starredNodeId);
        }
        InformationTreeComparisonOperatorNode newRoot = new LessThanOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public GreaterOrEqualOperatorNode newInstance() {
        return new GreaterOrEqualOperatorNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String value = ((XMLComparable) childNode.datatypeRecorder.xmlDatatype.getValueHandler()).getLess(childNode.getContext().context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        boolean result = super.checkContextAcceptability(childNode);
        if(result) {
            return !(childNode.datatypeRecorder.getActualDatatype() instanceof XMLDouble);
        } else return false;
    }
}
