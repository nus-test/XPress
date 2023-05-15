package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeConstantNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.PredicateTreeFunctionNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeNode;

import static XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNodeManager.wrapNumericalBinaryFunctionExpr;
import static java.lang.Math.abs;

public class DoubleMultiplicationFunctionNode extends InformationTreeFunctionNode implements NumericalBinaryOperator {

    DoubleMultiplicationFunctionNode() {
        dataTypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childNode.context == null) {
            fillContentsRandom(childNode);
            return;
        }
        childList.add(childNode);
        double currentValue = Double.parseDouble(childNode.context);
        double multiplicationValue;
        if(abs(currentValue) > 10000) {
            multiplicationValue = GlobalRandom.getInstance().nextDouble() + 0.1;
            if(multiplicationValue > 1) multiplicationValue = 1;
        }
        else {
            Integer pre = GlobalRandom.getInstance().nextInt(10) + 1;
            double last = GlobalRandom.getInstance().nextDouble();
            if(last < 0.0001) last = 0.0001;
            multiplicationValue = pre + last;
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, Double.toString(multiplicationValue)));
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        double prob = GlobalRandom.getInstance().nextDouble();
        double multiplicationValue;
        if(prob < 0.6) {
            multiplicationValue = GlobalRandom.getInstance().nextDouble() + 0.1;
            if(multiplicationValue > 1) multiplicationValue = 1;
        }
        else {
            Integer pre = GlobalRandom.getInstance().nextInt(10) + 1;
            double last = GlobalRandom.getInstance().nextDouble();
            if(last < 0.0001) last = 0.0001;
            multiplicationValue = pre + last;
        }
        String multiplicationValueStr = Double.toString(multiplicationValue);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, multiplicationValueStr));
    }

    @Override
    public DoubleMultiplicationFunctionNode newInstance() {
        return new DoubleMultiplicationFunctionNode();
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String leftChild = wrapNumericalBinaryFunctionExpr(childList.get(0), this, returnConstant);
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this, returnConstant);
        return leftChild + " mul " + rightChild;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
}
