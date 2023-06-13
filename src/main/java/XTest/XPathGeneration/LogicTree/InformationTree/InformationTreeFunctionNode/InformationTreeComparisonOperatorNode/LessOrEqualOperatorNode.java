package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.PrimitiveDatatype.XMLComparable;
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
public class LessOrEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    public LessOrEqualOperatorNode() {
        functionExpr = "<=";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        InformationTreeComparisonOperatorNode newRoot = new GreaterThanOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public LessOrEqualOperatorNode newInstance() {
        return new LessOrEqualOperatorNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String value = ((XMLComparable) childNode.datatypeRecorder.xmlDatatype.getValueHandler()).getGreater(childNode.getContext().context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        boolean result = super.checkContextAcceptability(childNode);
        if(result) {
            return childNode.datatypeRecorder.getActualDatatype() != XMLDatatype.DOUBLE;
        } else return false;
    }
}
