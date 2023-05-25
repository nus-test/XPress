package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class IntegerAddFunctionNode extends BinaryNumericalOperatorFunctionNode {

    public IntegerAddFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        priorityLevel = 2;
        functionExpr = "+";
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        String value;
        if(childNode.context.equals("")) {
            try {
                System.out.println("hihihaha" + childNode.getClass());
                System.out.println(childNode.getCalculationString());
                System.out.println(childNode.getXPathExpression());
            } catch (DebugErrorException e) {
                throw new RuntimeException(e);
            }
        }
        Integer inputValue = Integer.parseInt(childNode.context);
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
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerAddFunctionNode newInstance() {
        return new IntegerAddFunctionNode();
    }
}
