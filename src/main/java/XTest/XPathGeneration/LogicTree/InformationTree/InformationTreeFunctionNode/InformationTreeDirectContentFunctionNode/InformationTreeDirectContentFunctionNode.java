package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.TestException.UnexpectedExceptionThrownException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import net.sf.saxon.s9api.SaxonApiException;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.sql.SQLException;

public abstract class InformationTreeDirectContentFunctionNode extends InformationTreeFunctionNode {

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) throws SQLException, XMLDBException, UnexpectedExceptionThrownException, IOException, SaxonApiException, DebugErrorException {
        fillContentParametersRandom(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        Boolean checkResult = childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE;
        if(!checkResult) {
            if(childNode.datatypeRecorder.xmlDatatype == XMLDatatype.SEQUENCE && childNode.datatypeRecorder.subDatatype == XMLDatatype.NODE &&
            Integer.parseInt(childNode.getContext().context) != 0)
                checkResult = true;
        }
        return checkResult;
    }

    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        String expr = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(expr != null) return expr;
        String childExpr = childList.get(0).getXPathExpression(returnConstant, parentNode, calculateString);
        if(childExpr.equals(".")) {
            return functionExpr + "()";
        }
        return childExpr + "/" + functionExpr + "()";
    }
}
