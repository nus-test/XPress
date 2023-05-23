package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

import static XTest.StringUtils.getListString;
import static java.lang.Math.abs;

public class DoubleAddFunctionNode extends BinaryOperatorFunctionNode {
    public DoubleAddFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
        priorityLevel = 2;
        functionExpr = "+";
    }

    @Override
    public DoubleAddFunctionNode newInstance() {
        return new DoubleAddFunctionNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        double currentValue = Double.parseDouble(childNode.context);
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        double addValue = Double.parseDouble(value);
        if(abs(currentValue + addValue) < 0.5) {
            if(addValue > 0) value = "-" + value;
            else value = value.substring(1);
        }
        else {
            if (currentValue > 10000 && Double.parseDouble(value) > 0) {
                value = "-" + value;
            } else if (currentValue < 10000 && Double.parseDouble(value) < 0) {
                value = value.substring(1);
            }
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        // TODO: Control interval of randomly generated value to avoid overflow if necessary
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.DOUBLE;
    }

    @Override
    public String getCurrentContextFunctionExpr() {
        return ". + " +
                childList.get(1).getXPathExpression(true, this);
    }
}
