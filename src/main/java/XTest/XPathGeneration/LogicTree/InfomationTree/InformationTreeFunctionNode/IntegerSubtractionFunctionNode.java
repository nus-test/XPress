package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class IntegerSubtractionFunctionNode extends BinaryOperatorFunctionNode {

    public IntegerSubtractionFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "-";
        priorityLevel = 2;
    }
    @Override
    public void fillContents(InformationTreeNode childNode) {
        if(!childNode.checkCalculableContext()) {
            fillContentsRandom(childNode);
            return;
        }
        String value = null;
        Integer inputValue = Math.abs(Integer.parseInt(childNode.context));
        if(inputValue < 0)
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(Integer.MIN_VALUE - inputValue);
        else {
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(inputValue - Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public void fillContentsRandom(InformationTreeNode childNode) {
        childList.add(childNode);
        inheritContextChildInfo(childNode);
        String value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                getRandomValueBounded(Integer.MIN_VALUE, Integer.MAX_VALUE);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerSubtractionFunctionNode newInstance() {
        return new IntegerSubtractionFunctionNode();
    }
}
