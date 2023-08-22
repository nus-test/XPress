package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.DatatypeControl.ValueHandler.XMLIntegerHandler;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class IntegerSubtractionFunctionNode extends BinaryNumericalOperatorFunctionNode {

    public IntegerSubtractionFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "-";
        priorityLevel = 2;
    }
    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        if(fillContentParameterBySubRoot(XMLInteger.getInstance())) return;
        String value = null;
        Integer inputValue = Integer.parseInt(childNode.getContext().context);
        if(inputValue < 0)
            value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE - inputValue);
        else {
            value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                    getRandomValueBounded(inputValue - Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                getRandomValueBounded(Integer.MIN_VALUE, Integer.MAX_VALUE);
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLInteger;
    }

    @Override
    public IntegerSubtractionFunctionNode newInstance() {
        return new IntegerSubtractionFunctionNode();
    }
}
