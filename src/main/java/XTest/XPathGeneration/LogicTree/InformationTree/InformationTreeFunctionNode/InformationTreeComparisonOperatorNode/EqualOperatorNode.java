package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class EqualOperatorNode extends InformationTreeComparisonOperatorNode {
    public EqualOperatorNode() {
        functionExpr = "=";
    }

    @Override
    public InformationTreeComparisonOperatorNode modifyToContainStarredNode(int starredNodeId) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        InformationTreeComparisonOperatorNode newRoot = new NotEqualOperatorNode();
        newRoot.transferInfo(this);
        return newRoot;
    }

    @Override
    public EqualOperatorNode newInstance() {
        return new EqualOperatorNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String value = childNode.datatypeRecorder.xmlDatatype.getValueHandler().getEqual(childNode.getContext().context);
        if(childNode.getContext().context.equals("q1=\"true\"")) {
            System.out.println("bububububbubububbbbub");
            try {
                System.out.println(childNode.getCalculationString());
            } catch (DebugErrorException e) {
                throw new RuntimeException(e);
            }
            System.out.println("bububububbubububbbbub");
        }
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        if(recorder.getActualDatatype() == XMLDatatype.DOUBLE)
            return false;
        return childNode.datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE &&
                childNode.datatypeRecorder.xmlDatatype != XMLDatatype.NODE &&
                childNode.datatypeRecorder.xmlDatatype != XMLDatatype.MIXED;
    }
}