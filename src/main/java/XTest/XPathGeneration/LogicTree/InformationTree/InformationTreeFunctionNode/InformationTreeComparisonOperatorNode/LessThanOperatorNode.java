package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLComparable;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;
@FunctionV1
public class LessThanOperatorNode extends InformationTreeComparisonOperatorNode {
    public LessThanOperatorNode() {
        functionExpr = "<";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
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
