package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public abstract class InformationTreeDirectContentFunctionNode extends InformationTreeFunctionNode {

    @Override
    public void fillContentParameters(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        fillContentParametersRandom(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        Boolean checkResult = recorder.xmlDatatype == XMLDatatype.NODE;
        if(!checkResult) {
            if(recorder.xmlDatatype == XMLDatatype.SEQUENCE && recorder.subDatatype == XMLDatatype.NODE)
                checkResult = true;
        }
        return checkResult;
    }

    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) {
        String expr = getXPathExpressionCheck(returnConstant);
        if(expr != null) return expr;
        String childExpr = childList.get(0).getXPathExpression(returnConstant);
        if(childExpr.equals(".")) {
            return functionExpr;
        }
        return childExpr + "/" + functionExpr;
    }

    /**
     * 
     * @return XPath expression string for calculating the result of current information tree. If results in single value should evaluate
     * to single value, if is a sequence should give XPath expression of sequence.
     */
    public String getCurrentLevelCalculationString() {
        if(datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE)
            return getSequenceCalculationString();
        String calculationStr = getXPathExpression(false) + "[@id=\"" + childList.get(0).context + "\"]";
        calculationStr += "/" + functionExpr + "()";
        return calculationStr;
    }

    public String getSequenceCalculationString() {
        return getXPathExpression(true);
    }
}
