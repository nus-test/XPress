package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.LogicTree.LogicTreeNode;
import static java.lang.Math.abs;

public class DoubleDivisionFunctionNode extends BinaryOperatorFunctionNode {
    public DoubleDivisionFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        priorityLevel = 3;
        functionExpr = "div";
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(!childNode.checkCalculableContext()) {
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
        inheritContextChildInfo(childNode);
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
        inheritContextChildInfo(childNode);
        String divisionValueStr = Double.toString(divisionValue);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, divisionValueStr));
    }

    @Override
    public DoubleDivisionFunctionNode newInstance() {
        return new DoubleDivisionFunctionNode();
    }

    @Override
    public String getCurrentContextFunctionExpr() {
        String rightChild = childList.get(1).getXPathExpression(false, this);
        return ". div " + rightChild;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
}
