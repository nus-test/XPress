package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public class TailFunctionNode extends InformationTreeSequenceFunctionNode {
    public TailFunctionNode() {
        functionExpr = "tail";
    }

    @Override
    public TailFunctionNode newInstance() {
        return new TailFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        // TODO: Note that this check is for filling contents to not result in empty sequence, in fact is still legal;
        if(childNode.datatypeRecorder.xmlDatatype != XMLDatatype.SEQUENCE)
            return false;
        return Integer.parseInt(childNode.context) > 1;
    }
}
