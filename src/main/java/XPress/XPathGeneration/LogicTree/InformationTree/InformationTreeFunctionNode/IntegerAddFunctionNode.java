package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.PrimitiveDatatype.XMLIntegerHandler;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class IntegerAddFunctionNode extends BinaryNumericalOperatorFunctionNode {

    public IntegerAddFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        priorityLevel = 2;
        functionExpr = "+";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        if(fillContentParameterBySubRoot(XMLDatatype.INTEGER)) return;
        String value;
        int inputValue = Integer.parseInt(childNode.getContext().context);
        if(inputValue < 0) {
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE - inputValue, Integer.MAX_VALUE);
        }
        else {
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(Integer.MAX_VALUE - inputValue);
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String value;
        value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE, Integer.MAX_VALUE);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerAddFunctionNode newInstance() {
        return new IntegerAddFunctionNode();
    }
}