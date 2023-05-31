package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
@FunctionV1
public class NotEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    public NotEqualOperatorNode() {
        functionExpr = "!=";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
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