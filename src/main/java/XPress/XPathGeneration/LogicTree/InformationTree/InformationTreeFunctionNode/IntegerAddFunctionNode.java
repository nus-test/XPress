package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.DatatypeControl.ValueHandler.XMLIntegerHandler;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class IntegerAddFunctionNode extends BinaryNumericalOperatorFunctionNode {

    public IntegerAddFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        priorityLevel = 2;
        functionExpr = "+";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        if(fillContentParameterBySubRoot(XMLInteger.getInstance())) return;
        String value;
        int inputValue = Integer.parseInt(childNode.getContext().context);
        if(inputValue < 0) {
            value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE - inputValue, Integer.MAX_VALUE);
        }
        else {
            value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                    getRandomValueBounded(Integer.MAX_VALUE - inputValue);
        }
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String value;
        value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE, Integer.MAX_VALUE);
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLInteger;
    }

    @Override
    public IntegerAddFunctionNode newInstance() {
        return new IntegerAddFunctionNode();
    }
}
