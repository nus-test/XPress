package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public abstract class BinaryOperatorFunctionNode extends InformationTreeFunctionNode {

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        String returnString = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(returnString != null) return returnString;
        returnString = childList.get(0).getXPathExpression(returnConstant, this) + " " + functionExpr + " " +
                childList.get(1).getXPathExpression(returnConstant, this);
        if(parentNode != null) {
            if(parentNode instanceof BinaryOperatorFunctionNode)
                returnString = "(" + returnString + ")";
        }
        cacheXPathExpression(returnString, returnConstant, calculateString);
        return returnString;
    }

    public String binaryWrap(String resultString, LogicTreeNode parentNode) {
        if(parentNode != null) {
            if(parentNode instanceof BinaryOperatorFunctionNode)
                resultString = "(" + resultString + ")";
        }
        return resultString;
    }
}
