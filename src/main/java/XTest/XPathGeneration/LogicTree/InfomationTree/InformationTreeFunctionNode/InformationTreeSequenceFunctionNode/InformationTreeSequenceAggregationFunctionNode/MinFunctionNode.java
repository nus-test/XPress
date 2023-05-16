package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode.InformationTreeSequenceAggregationFunctionNode;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;

public class MinFunctionNode extends InformationTreeSequenceAggregationFunctionNode {
    MinFunctionNode() {
        functionExpr = "min";
    }

    @Override
    public MinFunctionNode newInstance() {
        return new MinFunctionNode();
    }
}
