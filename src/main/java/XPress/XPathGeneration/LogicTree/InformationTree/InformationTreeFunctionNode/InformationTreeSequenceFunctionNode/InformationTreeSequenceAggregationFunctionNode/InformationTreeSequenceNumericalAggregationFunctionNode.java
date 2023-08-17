package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XPress.PrimitiveDatatype.XMLNumeric;
import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeNode;

public abstract class InformationTreeSequenceNumericalAggregationFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode) {
        return childNode.datatypeRecorder.getActualDatatype().getValueHandler() instanceof XMLNumeric;
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.getActualDatatype();
    }
}
