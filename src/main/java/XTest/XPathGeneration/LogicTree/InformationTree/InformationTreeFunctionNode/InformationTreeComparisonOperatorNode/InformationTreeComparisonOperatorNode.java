package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLComparable;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.BinaryOperatorFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public abstract class InformationTreeComparisonOperatorNode extends BinaryOperatorFunctionNode {
    InformationTreeComparisonOperatorNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
    }

    @Override
    public abstract InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException;

    /**
     * To transfer all information of the previous node to current node (change only the comparison operator
     * node type.) Also, new result is calculated.
     * @param prevNode
     */
    void transferInfo(InformationTreeComparisonOperatorNode prevNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        //TODO: Check if there is more information necessary to transfer
        childList = prevNode.childList;
        datatypeRecorder = prevNode.datatypeRecorder;
        setContextInfo(prevNode.getContextInfo());
        calculateInfo();
        setCalculableContextFlag();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype,
                childNode.datatypeRecorder.xmlDatatype.getValueHandler().getValue()));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        if(GlobalSettings.xPathVersion == GlobalSettings.XPathVersion.VERSION_1 && childNode.datatypeRecorder.xmlDatatype == XMLDatatype.STRING) {
            return false;
        }
        if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE ||
            childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE ||
            childNode.datatypeRecorder.xmlDatatype == XMLDatatype.MIXED)
            return false;
        return childNode.datatypeRecorder.xmlDatatype.getValueHandler() instanceof XMLComparable;
    }
}
