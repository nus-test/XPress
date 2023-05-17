package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;

public class IntegerSubtractionFunctionNode extends InformationTreeFunctionNode implements NumericalBinaryOperator {

    public IntegerSubtractionFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
    }
    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childNode.context == null) {
            fillContentsRandom(childNode);
            return;
        }
        String value = null;
        Integer inputValue = Math.abs(Integer.parseInt(childNode.context));
        if(inputValue < 0)
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE - inputValue);
        else {
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(inputValue - Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        String value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                getRandomValueBounded(Integer.MIN_VALUE, Integer.MAX_VALUE);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public String getCurrentContextFunctionExpr() {
        return ". - " +
                childList.get(1).getXPathExpression(true);
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        returnString = childList.get(0).getXPathExpression(returnConstant) + " - " +
                childList.get(1).getXPathExpression(returnConstant);
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }

    @Override
    public IntegerSubtractionFunctionNode newInstance() {
        return new IntegerSubtractionFunctionNode();
    }
}
