package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;

public class SumFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    SumFunctionNode() {
        functionExpr = "sum";
    }

    @Override
    public SumFunctionNode newInstance() {
        return new SumFunctionNode();
    }
}
