package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.GlobalRandom;
import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLIntegerHandler;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeConstantNode;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class IntegerDivisionFunctionNode extends BinaryOperatorFunctionNode {
    public IntegerDivisionFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "idiv";
        priorityLevel = 3;
    }

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {
        double prob = GlobalRandom.getInstance().nextDouble();
        String value = null;
        if(prob < 0.7 && Integer.parseInt(childNode.context) >= 2)
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(1, Integer.parseInt(childNode.context));
        else {
            value = XMLDatatype.INTEGER.getValueHandler().getValue(false);
        }
        if(Integer.parseInt(value) == 0)
            value = "2";
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) {
        String value = XMLDatatype.INTEGER.getValueHandler().getValue(false);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return recorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerDivisionFunctionNode newInstance() {
        return new IntegerDivisionFunctionNode();
    }
}
