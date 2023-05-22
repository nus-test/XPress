package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

import static java.lang.Math.abs;

public class DoubleSubtractFunctionNode extends BinaryOperatorFunctionNode {
    public DoubleSubtractFunctionNode() {
        this.datatypeRecorder.xmlDatatype  = XMLDatatype.DOUBLE;
        functionExpr = "-";
        priorityLevel = 2;
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        double currentValue;
        currentValue = Double.parseDouble(childNode.context);
        double subtractValue = Double.parseDouble(value);
        if(abs(currentValue - subtractValue) < 0.5) {
            if(subtractValue < 0) value = value.substring(1);
            else value = "-" + value;
        }
        else {
            if (currentValue > 10000 && subtractValue < 0) {
                value = value.substring(1);
            } else if (currentValue < 10000 && subtractValue > 0) {
                value = "-" + value;
            }
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) {
        // TODO: Control interval of randomly generated value to avoid overflow if necessary
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }

    @Override
    public String getXPathExpression(boolean returnConstant) {
        String returnString = getXPathExpressionCheck(returnConstant);
        if(returnString != null) return returnString;
        return childList.get(0).getXPathExpression(returnConstant) + " - " +
                childList.get(1).getXPathExpression(returnConstant);
    }

    @Override
    public DoubleSubtractFunctionNode newInstance() {
        return new DoubleSubtractFunctionNode();
    }
}
