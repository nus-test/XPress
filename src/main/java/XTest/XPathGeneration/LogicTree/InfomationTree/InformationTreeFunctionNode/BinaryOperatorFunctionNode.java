package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode.GreaterThanOperatorNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeComparisonOperatorNode.NotEqualOperatorNode;
import XTest.XPathGeneration.LogicTree.LogicTreeComparisonNode;
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
        if(this instanceof CastableFunctionNode) {
            System.out.println("^^^^^^^^^^^^^^^^^" + returnString);
            if(parentNode != null)
                System.out.println("what: " + parentNode.getClass());
        }
        cacheXPathExpression(returnString, returnConstant, calculateString);
        return returnString;
    }

    @Override
    public String getCurrentContextFunctionExpr() throws DebugErrorException {
        String rightChild = childList.get(1).getXPathExpression(false, this, false);
        return ". " + functionExpr + " " + rightChild;
    }
}
