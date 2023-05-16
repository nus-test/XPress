package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;

public class CountFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    CountFunctionNode() {
        functionExpr = "count";
    }

    @Override
    public CountFunctionNode newInstance() {
        return new CountFunctionNode();
    }
}
