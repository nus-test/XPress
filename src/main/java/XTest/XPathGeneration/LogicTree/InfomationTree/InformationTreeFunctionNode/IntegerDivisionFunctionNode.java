package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager.wrapNumericalBinaryFunctionExpr;

public class IntegerDivisionFunctionNode extends InformationTreeFunctionNode implements NumericalBinaryOperator {
    IntegerDivisionFunctionNode() {
        dataTypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childNode.context == null) {
            fillContentsRandom(childNode);
            return;
        }
        double prob = GlobalRandom.getInstance().nextDouble();
        String value = null;
        if(prob < 0.7 && Integer.parseInt(childNode.context) >= 2)
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(1, Integer.parseInt(childNode.context));
        else {
            value = XMLDatatype.INTEGER.getValueHandler().getValue(false);
        }
        if(Integer.parseInt(value) == 0)
            value = "2";
        childList.add(childNode);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        String value = XMLDatatype.INTEGER.getValueHandler().getValue(false);
        childList.add(childNode);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public String getCurrentContextFunctionExpr() {
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this, true);
        return ". idiv " + rightChild;
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String leftChild = wrapNumericalBinaryFunctionExpr(childList.get(0), this, returnConstant);
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this, returnConstant);
        String returnString = leftChild + " idiv " + rightChild;
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerDivisionFunctionNode newInstance() {
        return new IntegerDivisionFunctionNode();
    }

}
