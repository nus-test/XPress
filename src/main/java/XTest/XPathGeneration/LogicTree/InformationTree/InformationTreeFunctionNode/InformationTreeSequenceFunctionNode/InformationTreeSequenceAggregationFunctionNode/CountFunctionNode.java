package XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.PrimitiveDatatype.XMLDatatype;
import XTest.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV1;

@FunctionV1
public class CountFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    public CountFunctionNode() {
        functionExpr = "count";
        datatypeRecorder.xmlDatatype = XMLDatatype.INTEGER;
    }


    @Override
    public CountFunctionNode newInstance() {
        return new CountFunctionNode();
    }
}
