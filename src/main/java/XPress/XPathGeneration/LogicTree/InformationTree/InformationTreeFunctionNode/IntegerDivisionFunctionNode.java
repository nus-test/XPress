package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.GlobalRandom;
import XPress.DatatypeControl.ValueHandler.XMLIntegerHandler;
import XPress.TestException.DebugErrorException;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class IntegerDivisionFunctionNode extends BinaryNumericalOperatorFunctionNode {
    public IntegerDivisionFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "idiv";
        priorityLevel = 3;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) throws DebugErrorException {
        double prob = GlobalRandom.getInstance().nextDouble();
        String value = null;
        if(prob < 0.7 && Integer.parseInt(childNode.getContext().context) >= 2)
            value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                    getRandomValueBounded(1, Integer.parseInt(childNode.getContext().context));
        else {
            value = XMLInteger.getInstance().getValueHandler().getValue(false);
        }
        if(Integer.parseInt(value) == 0)
            value = "2";
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String value = XMLInteger.getInstance().getValueHandler().getValue(false);
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLInteger;
    }

    @Override
    public IntegerDivisionFunctionNode newInstance() {
        return new IntegerDivisionFunctionNode();
    }
}
