package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.GlobalRandom;
import XTest.GlobalSettings;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.NotFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

@FunctionV1
public class EqualOperatorNode extends InformationTreeComparisonOperatorNode {
    public EqualOperatorNode() {
        functionExpr = "=";
    }

    @Override
    public InformationTreeNode modifyToContainStarredNode(int starredNodeId) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        NotFunctionNode newRoot = new NotFunctionNode();
        if(GlobalSettings.starNodeSelection)
            newRoot.fillContents(this);
        else newRoot.fillContentsRandom(this, false);
        return newRoot;
    }

    @Override
    public EqualOperatorNode newInstance() {
        return new EqualOperatorNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) {
            InformationTreeNode node = InformationTreeFunctionNodeManager.getInstance().getNodeWithSimpleValueOrSequence(childNode.datatypeRecorder.getActualDatatype());
            if(node != null) {
                childList.add(node);
                return;
            }
        }
        if(childNode.datatypeRecorder.getActualDatatype() == XMLDatatype.NODE || childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE) {
            childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, "()"));
        }
        String value = childNode.datatypeRecorder.xmlDatatype.getValueHandler().getEqual(childNode.getContext().context);
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.getActualDatatype() != XMLDatatype.DOUBLE;
    }
}