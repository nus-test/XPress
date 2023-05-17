package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;

import static XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager.wrapNumericalBinaryFunctionExpr;

public class IntegerMultiplicationFunctionNode extends InformationTreeFunctionNode implements NumericalBinaryOperator {
    public IntegerMultiplicationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childNode.context == null) {
            fillContentsRandom(childNode);
            return;
        }
        String value;
        Integer inputValue = Math.abs(Integer.parseInt(childNode.context));
        if(inputValue == 0) {
            value = Integer.toString(GlobalRandom.getInstance().nextInt(1000));
        }
        else {
            Integer boundValue = Integer.MAX_VALUE / inputValue;
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(-boundValue, boundValue);
            if (value.equals("0"))
                value = Integer.toString(1);
        }
        childList.add(childNode);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }
    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        String value = Integer.toString(GlobalRandom.getInstance().nextInt(1000));
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public String getCurrentContextFunctionExpr() {
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this, true);
        return ". mul " + rightChild;
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String leftChild = wrapNumericalBinaryFunctionExpr(childList.get(0), this, returnConstant);
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this, returnConstant);
        String returnString = leftChild + " mul " + rightChild;
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerMultiplicationFunctionNode newInstance() {
        return new IntegerMultiplicationFunctionNode();
    }
}
