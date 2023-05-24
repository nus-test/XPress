package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.XPathGeneration.LogicTree.LogicTreeComparisonNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;

public abstract class BinaryOperatorFunctionNode extends InformationTreeFunctionNode {

    @Override
    public String getXPathExpression(boolean returnConstant, LogicTreeNode parentNode) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        returnString = childList.get(0).getXPathExpression(returnConstant, this) + " " + functionExpr + " " +
                childList.get(1).getXPathExpression(returnConstant, this);
        if(parentNode != null) {
            if(parentNode instanceof BinaryOperatorFunctionNode)
                returnString = "(" + returnString + ")";
        }
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }

    @Override
    public String getCurrentContextFunctionExpr() {
        String rightChild = childList.get(1).getXPathExpression(false, this);
        return ". " + functionExpr + " " + rightChild;
    }
}
