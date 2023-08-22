package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

@FunctionV3
public class TailFunctionNode extends InformationTreeSequenceFunctionNode {
    public TailFunctionNode() {
        functionExpr = "tail";
    }

    @Override
    public TailFunctionNode newInstance() {
        return new TailFunctionNode();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        // TODO: Note that this check is for filling contents to not result in empty sequence, in fact is still legal;
        if(!(childNode.datatypeRecorder.xmlDatatype instanceof XMLSequence))
            return false;
        if(childNode.getContext().context == null) return true;
        return Integer.parseInt(childNode.getContext().context) > 1;
    }
}
