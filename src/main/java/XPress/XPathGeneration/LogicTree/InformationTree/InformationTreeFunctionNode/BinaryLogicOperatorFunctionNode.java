package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeConstantNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public abstract class BinaryLogicOperatorFunctionNode extends BinaryOperatorFunctionNode {
    public BinaryLogicOperatorFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.BOOLEAN;
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.xmlDatatype == XMLDatatype.BOOLEAN;
    }

    protected void fillContentParameters(InformationTreeNode childNode) {
        fillContentParametersRandom(childNode);
    }
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        childList.add(new InformationTreeConstantNode(childNode.datatypeRecorder.xmlDatatype,
                XMLDatatype.BOOLEAN.getValueHandler().getValue()));
    }
}
