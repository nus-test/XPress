package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class NoActionFunctionNode extends InformationTreeFunctionNode {

    @Override
    public void fillContentParameters(InformationTreeNode childNode) {
        fillContentParametersRandom(childNode);
    }

    @Override
    public void fillContentParametersRandom(InformationTreeNode childNode) {
        datatypeRecorder = new XMLDatatypeComplexRecorder(childNode.datatypeRecorder);
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        return true;
    }

    @Override
    public NoActionFunctionNode newInstance() {
        return new NoActionFunctionNode();
    }
}
