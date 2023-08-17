package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeDirectContentFunctionNode;

import XPress.GlobalSettings;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.TestException.DebugErrorException;
import XPress.TestException.UnexpectedExceptionThrownException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
import XPress.XPathGeneration.LogicTree.LogicTreeNode;

import java.io.IOException;
import java.sql.SQLException;

public abstract class InformationTreeDirectContentFunctionNode extends InformationTreeFunctionNode {

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) throws SQLException, UnexpectedExceptionThrownException, IOException, DebugErrorException {
        fillContentParametersRandom(childNode);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        Boolean checkResult = childNode.datatypeRecorder.xmlDatatype == XMLDatatype.NODE;
        if(!checkResult) {
            if(!GlobalSettings.starNodeSelection) return false;
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
