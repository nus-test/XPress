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

public class DoubleDivisionFunctionNode extends InformationTreeFunctionNode implements NumericalBinaryOperator {
    DoubleDivisionFunctionNode() {
        this.dataTypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childNode.context == null) {
            fillContentsRandom(childNode);
            return;
        }
        double currentValue = Double.parseDouble(childNode.context);
        if(abs(currentValue) > 10) {
            fillContentsRandom(childNode);
            return;
        }
        double divisionValue = GlobalRandom.getInstance().nextDouble() + 0.5;
        if(divisionValue > 1.2) divisionValue = 1;
        childList.add(childNode);
        String divisionValueStr = Double.toString(divisionValue);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, divisionValueStr));
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        Integer pre = GlobalRandom.getInstance().nextInt(10);
        Integer last = GlobalRandom.getInstance().nextInt(10);
        if(last == 0) last = 1;
        double divisionValue = Double.parseDouble(pre + "." + last);
        childList.add(childNode);
        String divisionValueStr = Double.toString(divisionValue);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, divisionValueStr));
    }

    @Override
    public DoubleDivisionFunctionNode newInstance() {
        return new DoubleDivisionFunctionNode();
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String leftChild = wrapNumericalBinaryFunctionExpr(childList.get(0), this, returnConstant);
        String rightChild = wrapNumericalBinaryFunctionExpr(childList.get(1), this, returnConstant);
        return leftChild + " div " + rightChild;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
}
