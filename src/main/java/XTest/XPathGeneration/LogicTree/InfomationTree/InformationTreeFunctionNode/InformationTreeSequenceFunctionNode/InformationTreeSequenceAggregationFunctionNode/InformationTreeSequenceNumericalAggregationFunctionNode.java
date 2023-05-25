package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.PrimitiveDatatype.XMLDatatypeComplexRecorder;
import XTest.PrimitiveDatatype.XMLNumeric;
import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeNode;

public abstract class InformationTreeSequenceNumericalAggregationFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    @Override
    public Boolean checkContextAcceptability(InformationTreeNode childNode, XMLDatatypeComplexRecorder recorder) {
        if(!super.checkContextAcceptability(childNode, recorder))
            return false;
        return recorder.getActualDatatype().getValueHandler() instanceof XMLNumeric;
    }

    @Override
    protected void fillContentParametersRandom(InformationTreeNode childNode) {
        datatypeRecorder.xmlDatatype = childNode.datatypeRecorder.getActualDatatype();
    }
}
