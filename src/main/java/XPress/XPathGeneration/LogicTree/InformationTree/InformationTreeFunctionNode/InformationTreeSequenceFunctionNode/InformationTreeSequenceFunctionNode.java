package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.PrimitiveDatatype.XMLDatatype;
import XPress.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public abstract class InformationTreeSequenceFunctionNode extends InformationTreeFunctionNode {
    public InformationTreeSequenceFunctionNode() {
        datatypeRecorder.xmlDatatype = XMLDatatype.SEQUENCE;
    }

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
}
