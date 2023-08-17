package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.GlobalRandom;
import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.PrimitiveDatatype.XMLIntegerHandler;
import XPress.TestException.DebugErrorException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class IntegerDivisionFunctionNode extends BinaryNumericalOperatorFunctionNode {
    public IntegerDivisionFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
        functionExpr = "idiv";
        priorityLevel = 3;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) throws DebugErrorException {
        double prob = GlobalRandom.getInstance().nextDouble();
        String value = null;
        if(prob < 0.7 && Integer.parseInt(childNode.getContext().context) >= 2)
            value = ((XMLIntegerHandler) XMLDatatype.INTEGER.getValueHandler()).
                    getRandomValueBounded(1, Integer.parseInt(childNode.getContext().context));
        else {
            value = XMLDatatype.INTEGER.getValueHandler().getValue(false);
        }
        if(Integer.parseInt(value) == 0)
            value = "2";
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String value = XMLDatatype.INTEGER.getValueHandler().getValue(false);
        childList.add(new InformationTreeConstantNode(XMLDatatype.INTEGER, value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.INTEGER;
    }

    @Override
    public IntegerDivisionFunctionNode newInstance() {
        return new IntegerDivisionFunctionNode();
    }
}
