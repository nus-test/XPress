package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class IntegerMultiplicationFunctionNode extends BinaryOperatorFunctionNode {
    public IntegerMultiplicationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "*";
        priorityLevel = 3;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String value;
        Integer inputValue = Math.abs(Integer.parseInt(childNode.context));
        if(inputValue == 0) {
            value = Integer.toString(GlobalRandom.getInstance().nextInt(1000));
        }
        else {
            Integer boundValue = Integer.MAX_VALUE / inputValue;
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(-boundValue, boundValue);
            if (value.equals("0"))
                value = Integer.toString(1);
        }
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }
    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String value = Integer.toString(GlobalRandom.getInstance().nextInt(1000));
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerMultiplicationFunctionNode newInstance() {
        return new IntegerMultiplicationFunctionNode();
    }

}
