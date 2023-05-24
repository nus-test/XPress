package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.LogicTreeComparisonNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public abstract class BinaryNumericalOperatorFunctionNode extends BinaryOperatorFunctionNode {
    /**
     * Integer which represents the priority level of the binary operator. Higher value has higher priority.
     * If parent operator node has higher priority level than child node, child node XPath expression has to be
     * wrapped by parentheses.
     */
    int priorityLevel;
    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode, boolean calculateString) throws DebugErrorException {
        String returnString = getXPathExpressionCheck(returnConstant, parentNode, calculateString);
        if(returnString != null) return returnString;
        returnString = childList.get(0).getXPathExpression(returnConstant, this, calculateString) + " " + functionExpr + " " +
                childList.get(1).getXPathExpression(returnConstant, this, calculateString);
        if(parentNode != null) {
            if(!(parentNode instanceof BinaryNumericalOperatorFunctionNode &&
                    ((BinaryNumericalOperatorFunctionNode) parentNode).priorityLevel > priorityLevel))
                returnString = "(" + returnString + ")";
        }
        cacheXPathExpression(returnString, returnConstant, calculateString);
        return returnString;
    }
}
