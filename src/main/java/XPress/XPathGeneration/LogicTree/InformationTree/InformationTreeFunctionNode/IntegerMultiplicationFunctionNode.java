package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.GlobalRandom;
import XPress.DatatypeControl.ValueHandler.XMLIntegerHandler;
import XPress.TestException.DebugErrorException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class IntegerMultiplicationFunctionNode extends BinaryNumericalOperatorFunctionNode {
    public IntegerMultiplicationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "*";
        priorityLevel = 3;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) throws DebugErrorException {
        String value;
        Integer inputValue = Math.abs(Integer.parseInt(childNode.getContext().context));
        if(inputValue == 0) {
            value = Integer.toString(GlobalRandom.getInstance().nextInt(1000));
        }
        else {
            Integer boundValue = Integer.MAX_VALUE / inputValue;
            value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                    getRandomValueBounded(-boundValue, boundValue);
            if (value.equals("0"))
                value = Integer.toString(1);
        }
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }
    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String value = Integer.toString(GlobalRandom.getInstance().nextInt(1000));
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLInteger;
    }

    @Override
    public IntegerMultiplicationFunctionNode newInstance() {
        return new IntegerMultiplicationFunctionNode();
    }

}
