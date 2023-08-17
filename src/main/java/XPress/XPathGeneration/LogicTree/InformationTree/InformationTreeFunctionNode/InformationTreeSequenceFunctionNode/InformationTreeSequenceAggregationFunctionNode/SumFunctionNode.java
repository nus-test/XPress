package XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XPress.XPathGeneration.LogicTree.InformationTree.InformationTreeFunctionNode.FunctionV3;

@FunctionV3
public class SumFunctionNode extends InformationTreeSequenceNumericalAggregationFunctionNode {
    public SumFunctionNode() {
        functionExpr = "sum";
    }

    @Override
    public SumFunctionNode newInstance() {
        return new SumFunctionNode();
    }
}
