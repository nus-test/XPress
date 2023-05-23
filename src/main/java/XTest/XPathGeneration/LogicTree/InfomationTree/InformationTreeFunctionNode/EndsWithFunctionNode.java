package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public class EndsWithFunctionNode extends InformationTreeFunctionNode {
    String internalStr = null;

    public EndsWithFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
        functionExpr = "ends-with";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        internalStr = childNode.context;
        fillContentParametersWithGivenContext(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        if(internalStr == null) {
            internalStr = XMLDatatype.STRING.getValueHandler().getValue(false);
        }
        fillContentParametersWithGivenContext(childNode);
    }

    public void fillContentsWithGivenContext(InformationTreeNode childNode, String str) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        internalStr = str;
        fillContentsRandom(childNode);
    }

    private void fillContentParametersWithGivenContext(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        int startIndex = GlobalRandom.getInstance().nextInt(internalStr.length());
        String endStr = internalStr.substring(startIndex);
        if(prob < 0.2) {
            endStr = XMLDatatype.STRING.getValueHandler().mutateValue(endStr);
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.STRING, endStr));
        internalStr = null;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.STRING;
    }

    @Override
    public EndsWithFunctionNode newInstance() {
        return new EndsWithFunctionNode();
    }


}
