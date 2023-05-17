package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;
import XTest.XPathGeneration.PredicateGeneration.PredicateTreeFunctionNode.NumericalBinaryOperator;

import static java.lang.Math.abs;

public class DoubleSubtractFunctionNode extends InformationTreeFunctionNode implements NumericalBinaryOperator {
    DoubleSubtractFunctionNode() {
        this.datatypeRecorder.xmlDatatype  = XMLDatatype.DOUBLE;
    }

    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(childNode.context == null) {
            fillContentsRandom(childNode);
            return;
        }
        childList.add(childNode);
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
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
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