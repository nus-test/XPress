package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.PrimitiveDatatype.XMLComparable;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class LessOrEqualOperatorNode extends InformationTreeComparisonOperatorNode {
    LessOrEqualOperatorNode() {
        functionExpr = "<=";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        InformationTreeComparisonOperatorNode newRoot = new GreaterThanOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public LessOrEqualOperatorNode newInstance() {
        return new LessOrEqualOperatorNode();
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {
        String value = ((XMLComparable) childNode.dataTypeRecorder.xmlDatatype.getValueHandler()).getGreater(childNode.context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }

}
