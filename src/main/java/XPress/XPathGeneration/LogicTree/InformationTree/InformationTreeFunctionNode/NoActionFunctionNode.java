package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public class NoActionFunctionNode extends InformationTreeFunctionNode {

    @Override
    protected void fillContentParameters(InformationTreeNode childNode) {
        fillContentParametersRandom(childNode);
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        datatypeRecorder = new XMLDatatypeComplexRecorder(childNode.datatypeRecorder);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return true;
    }

    @Override
    public NoActionFunctionNode newInstance() {
        return new NoActionFunctionNode();
    }
}
