package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;

public class AverageFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    public AverageFunctionNode() {
        functionExpr = "avg";
        datatypeRecorder.xmlDatatype = XMLDatatype.DOUBLE;
    }

    @Override
    public AverageFunctionNode newInstance() {
        return new AverageFunctionNode();
    }
}
