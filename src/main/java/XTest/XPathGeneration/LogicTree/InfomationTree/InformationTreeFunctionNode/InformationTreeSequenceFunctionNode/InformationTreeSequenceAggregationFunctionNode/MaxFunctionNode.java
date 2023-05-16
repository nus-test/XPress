package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;

public class MaxFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    MaxFunctionNode() {
        functionExpr = "max";
    }

    @Override
    public MaxFunctionNode newInstance() {
        return new MaxFunctionNode();
    }
}
