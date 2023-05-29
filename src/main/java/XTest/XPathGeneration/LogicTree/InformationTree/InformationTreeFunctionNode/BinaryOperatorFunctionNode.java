package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public abstract class BinaryOperatorFunctionNode extends InformationTreeFunctionNode {

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        String returnString = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(returnString != null) return binaryWrap(returnString, parentNode);
        returnString = childList.get(0).getXPathExpression(returnConstant, this, calculateString) + " " + functionExpr + " " +
                childList.get(1).getXPathExpression(returnConstant, this, calculateString);
        cacheXPathExpression(returnString, returnConstant, calculateString);
        return binaryWrap(returnString, parentNode);
    }

    public String binaryWrap(String resultString, LogicTreeNode parentNode) {
        if(parentNode != null) {
            if(parentNode instanceof BinaryOperatorFunctionNode)
                resultString = "(" + resultString + ")";
        }
        return resultString;
    }
}
