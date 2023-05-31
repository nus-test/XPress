package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import static java.lang.Math.abs;

@FunctionV1
public class DoubleSubtractFunctionNode extends BinaryNumericalOperatorFunctionNode {
    public DoubleSubtractFunctionNode() {
        this.datatypeRecorder.xmlDatatype  = XMLDatatype.DOUBLE;
        functionExpr = "-";
        priorityLevel = 2;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        double currentValue;
        currentValue = Double.parseDouble(childNode.getContext().context);
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
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        // TODO: Control interval of randomly generated value to avoid overflow if necessary
        String value = XMLDatatype.DOUBLE.getValueHandler().getValue(false);
        childList.add(new InformationTreeConstantNode(XMLDatatype.DOUBLE, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.DOUBLE;
    }

    @Override
    public DoubleSubtractFunctionNode newInstance() {
        return new DoubleSubtractFunctionNode();
    }
}
