package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XPress.DatatypeControl.PrimitiveDatatype.*;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV1
public class EqualOperatorNode extends InformationTreeComparisonOperatorNode {
    public EqualOperatorNode() {
        functionExpr = "=";
    }

    @Override
    public EqualOperatorNode newInstance() {
        return new EqualOperatorNode();
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
    protected void fillContentParameters(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) {
            InformationTreeNode node = InformationTreeFunctionNodeManager.getInstance().getNodeWithSimpleType(childNode.datatypeRecorder.getActualDatatype());
            if(node != null) {
                childList.add(node);
                return;
            }
        }
        if(childNode.datatypeRecorder.getActualDatatype() instanceof XMLNode || childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence) {
            childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), "()"));
        }
        String value = childNode.datatypeRecorder.xmlDatatype.getValueHandler().getEqual(childNode.getContext().context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(childNode.datatypeRecorder.getActualDatatype() instanceof XMLQName)
            return false;
        if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1)
            return childNode.datatypeRecorder.xmlDatatype instanceof XMLNumeric &&
                    !(childNode.datatypeRecorder.xmlDatatype instanceof XMLDouble);
        return !(childNode.datatypeRecorder.getActualDatatype() instanceof XMLDouble)
                && !(childNode.datatypeRecorder.getActualDatatype() instanceof XMLMixed);
    }
}