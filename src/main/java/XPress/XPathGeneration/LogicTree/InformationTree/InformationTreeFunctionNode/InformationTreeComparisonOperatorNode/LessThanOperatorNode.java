package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.PrimitiveDatatype.XMLComparable;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import java.io.IOException;
import java.sql.SQLException;
@FunctionV1
public class LessThanOperatorNode extends InformationTreeComparisonOperatorNode {
    public LessThanOperatorNode() {
        functionExpr = "<";
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        if(!GlobalSettings.starNodeSelection && GlobalSettings.rectifySelection) {
            return super.modifyToContainStarredNode(starredNodeId);
        }
        InformationTreeComparisonOperatorNode newRoot = new GreaterOrEqualOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public LessThanOperatorNode newInstance() {
        return new LessThanOperatorNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.4) {
            InformationTreeNode node = InformationTreeFunctionNodeManager.getInstance().getNodeWithSimpleType(childNode.datatypeRecorder.getActualDatatype());
            if(node != null) {
                childList.add(node);
                return;
            }
        }
        String value = ((XMLComparable) childNode.datatypeRecorder.xmlDatatype.getValueHandler()).getGreaterOrEqual(childNode.getContext().context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }
}
