package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import XPress.GlobalRandom;
import XPress.DatatypeControl.ValueHandler.XMLIntegerHandler;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;
@FunctionV3
public class IntegerModFunctionNode extends BinaryNumericalOperatorFunctionNode {
    public IntegerModFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLInteger.getInstance();
        functionExpr = "mod";
        priorityLevel = 3;
    }

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        fillContentParametersRandom(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        String value = null;
        value = ((XMLIntegerHandler) XMLInteger.getInstance().getValueHandler()).
                getRandomValueBounded(Integer.MAX_VALUE / 2) + 1;
        double prob = GlobalRandom.getInstance().nextDouble();
        if(prob < 0.5) value = "-" + value;
        childList.add(new InformationTreeConstantNode(XMLInteger.getInstance(), value));
    }

    @Override
    public IntegerModFunctionNode newInstance() {
        return new IntegerModFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype instanceof XMLInteger;
    }
}
