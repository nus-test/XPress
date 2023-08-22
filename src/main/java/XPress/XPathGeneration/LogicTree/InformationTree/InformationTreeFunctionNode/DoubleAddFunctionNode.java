package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLDouble;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

import static XPress.StringUtils.getListString;
import static java.lang.Math.abs;

@FunctionV1
public class DoubleAddFunctionNode extends BinaryNumericalOperatorFunctionNode {
    public DoubleAddFunctionNode() {
        this.datatypeRecorder.xmlDatatype = XMLDouble.getInstance();
        priorityLevel = 2;
        functionExpr = "+";
    }

    @Override
    public DoubleAddFunctionNode newInstance() {
        return new DoubleAddFunctionNode();
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        double currentValue = Double.parseDouble(childNode.getContext().context);
        String value = XMLDouble.getInstance().getValueHandler().getValue(false);
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
        childList.add(new InformationTreeConstantNode(XMLDouble.getInstance(), value));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        // TODO: Control interval of randomly generated value to avoid overflow if necessary
        String value = XMLDouble.getInstance().getValueHandler().getValue(false);
        childList.add(new InformationTreeConstantNode(XMLDouble.getInstance(), value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLDouble;
    }
}
