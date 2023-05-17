package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;

import static XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager.wrapNumericalBinaryFunctionExpr;

public class IntegerModFunctionNode extends InformationTreeFunctionNode implements NumericalBinaryOperator {
    public IntegerModFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        fillContentsRandom(childNode);
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        String value = null;
        value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                getRandomValueBounded(Integer.MAX_VALUE / 2) + 1;
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) value = "-" + value;
        childList.add(childNode);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public IntegerModFunctionNode newInstance() {
        return new IntegerModFunctionNode();
    }

    @Override
    public String getCurrentContextFunctionExpr() {
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this, true);
        return ". mod " + rightChild;
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String leftChild = wrapNumericalBinaryFunctionExpr(childList.get(0), this, returnConstant);
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this, returnConstant);
        String returnString = leftChild + " mod " + rightChild;
        cacheXPathExpression(returnString, returnConstant);
        return returnString;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.INTEGER;
    }
}
