package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.TestException.DebugErrorException;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV1
public class IntegerMultiplicationFunctionNode extends BinaryNumericalOperatorFunctionNode {
    public IntegerMultiplicationFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "*";
        priorityLevel = 3;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) throws DebugErrorException {
        String value;
        if(childNode.getContext().context.equals("-2236074802")) {
            System.out.println("??? " + childNode.getClass() + " " + childNode.childList.get(0).getContext().context);
            System.out.println(childNode.getXPathExpression());
            System.out.println(childNode.childList.size());
            System.out.println(childNode.getCalculationString());
        }
        if(childNode.getContext().context.contains("id=")) {
            System.out.println("##################### Hi Hi Hi");
            System.out.println(childNode.getXPathExpression());
            System.out.println(childNode.getCalculationString());
            System.out.println("_____________________ Li Li Li");
        }
        Integer inputValue = Math.abs(Integer.parseInt(childNode.getContext().context));
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
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerMultiplicationFunctionNode newInstance() {
        return new IntegerMultiplicationFunctionNode();
    }

}
