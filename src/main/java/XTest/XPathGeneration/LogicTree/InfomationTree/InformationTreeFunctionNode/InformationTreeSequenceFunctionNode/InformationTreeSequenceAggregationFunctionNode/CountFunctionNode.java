package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;

public class CountFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    CountFunctionNode() {
        functionExpr = "count";
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
    }

    @Override
    public CountFunctionNode newInstance() {
        return new CountFunctionNode();
    }
}
