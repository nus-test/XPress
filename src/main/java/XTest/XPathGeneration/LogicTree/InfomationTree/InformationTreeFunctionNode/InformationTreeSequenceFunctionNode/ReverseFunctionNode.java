package XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeSequenceFunctionNode;

import XTest.XPathGeneration.LogicTree.InfomationTree.InformationTreeFunctionNode.InformationTreeFunctionNode;

public class ReverseFunctionNode extends InformationTreeSequenceFunctionNode {
    public ReverseFunctionNode() {
        functionExpr = "reverse";
    }
    @Override
    public ReverseFunctionNode newInstance() {
        return new ReverseFunctionNode();
    }
}
