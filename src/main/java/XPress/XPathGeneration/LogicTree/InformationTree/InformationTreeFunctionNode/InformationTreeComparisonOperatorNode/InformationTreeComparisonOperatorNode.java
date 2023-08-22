package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XPress.DatatypeControl.PrimitiveDatatype.*;
import XPress.GlobalSettings;
import XPress.DatatypeControl.XMLComparable;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.BinaryOperatorFunctionNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.NotFunctionNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import java.io.IOException;
import java.sql.SQLException;

public abstract class InformationTreeComparisonOperatorNode extends BinaryOperatorFunctionNode {
    InformationTreeComparisonOperatorNode() {
        datatypeRecorder.xmlDatatype = XMLBoolean.getInstance();
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        NotFunctionNode newRoot = new NotFunctionNode();
        if(GlobalSettings.starNodeSelection)
            newRoot.fillContents(this);
        else newRoot.fillContentsRandom(this, false);
        return newRoot;
    }

    /**
     * To transfer all information of the previous node to current node (change only the comparison operator
     * node type.) Also, new result is calculated.
     * @param prevNode
     */
    void transferInfo(InformationTreeComparisonOperatorNode prevNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        //TODO: Check if there is more information necessary to transfer
        childList = prevNode.childList;
        datatypeRecorder = prevNode.datatypeRecorder;
        setContextInfo(prevNode.getContextInfo());
        if(GlobalSettings.starNodeSelection) {
            calculateInfo();
            setCalculableContextFlag();
        }
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype,
                childNode.datatypeRecorder.xmlDatatype.getValueHandler().getValue()));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1 && childNode.datatypeRecorder.xmlDatatype instanceof XMLString) {
            return false;
        }
        if(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence ||
            childNode.datatypeRecorder.xmlDatatype instanceof XMLNode ||
            childNode.datatypeRecorder.xmlDatatype instanceof XMLMixed)
            return false;
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLComparable;
    }
}
