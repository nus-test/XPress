package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XPress.DatatypeControl.PrimitiveDatatype.XMLSequence;
import XPress.DatatypeControl.XMLAtomic;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

// TODO: Should only accept atomic types (could be with implicit cast) and would also result in list of atomic types.
//@FunctionV3
public class DistinctValuesFunctionNode extends InformationTreeSequenceFunctionNode {
    public DistinctValuesFunctionNode() {
        functionExpr = "distinct-values";
    }

    @Override
    public DistinctValuesFunctionNode newInstance() {
        return new DistinctValuesFunctionNode();
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        datatypeRecorder.xmlDatatype = XMLSequence.getInstance();
        datatypeRecorder.subDatatype = childNode.datatypeRecorder.getActualDatatype();
    }

    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.getActualDatatype() instanceof XMLAtomic;
    }
}
