package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import static java.lang.Math.abs;

@FunctionV1
public class DoubleDivisionFunctionNode extends BinaryNumericalOperatorFunctionNode {
    public DoubleDivisionFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        priorityLevel = 3;
        functionExpr = "div";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        double currentValue = Double.parseDouble(childNode.getContext().context);
        if(abs(currentValue) > 10) {
            fillContentParametersRandom(childNode);
            return;
        }
        double divisionValue = GlobalRandom.getInstance().nextDouble() + 0.5;
        if(divisionValue > 1.2) divisionValue = 1;
        String divisionValueStr = Double.toString(divisionValue);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, divisionValueStr));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        Integer pre = GlobalRandom.getInstance().nextInt(10);
        Integer last = GlobalRandom.getInstance().nextInt(10);
        if(last == 0) last = 1;
        double divisionValue = Double.parseDouble(pre + "." + last);
        String divisionValueStr = Double.toString(divisionValue);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, divisionValueStr));
    }

    @Override
    public DoubleDivisionFunctionNode newInstance() {
        return new DoubleDivisionFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DOUBLE;
    }
}
